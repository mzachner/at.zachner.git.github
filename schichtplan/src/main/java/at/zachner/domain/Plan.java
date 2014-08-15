package at.zachner.domain;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class Plan implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<ListenEintrag> listeVorher = new ArrayList<>();
	private ListenEintrag eintragHeute;
	private List<ListenEintrag> listeNachher = new ArrayList<>();
	private String schichtModelName;
	
	private Locale homeLocale = new Locale("de", "AT");
	
	public Plan() {

		ExternalContext eC = 
				FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sMap = eC.getSessionMap();
		CurrentUser cU = (CurrentUser) sMap.get("currentUser");
		
		if (cU != null) {
			this.schichtModelName = cU.getUser().getSchichtModel();
			initPlan(cU.getUser().getTageVorher(), cU.getUser().getTageNachher());
		}
		
	}

	private void initPlan(Integer tageVorher, Integer tageNachher) {

		SchichtModels schichtModels = new SchichtModels();
		List<String> schichtmodel = schichtModels.getSchichtmodel(this.schichtModelName);
		Date startingDate = schichtModels.getStartingDate(this.schichtModelName);
		startingDate.setTime(startingDate.getTime()+(1000*60*60*12));
		
		
		Calendar tempCalendar = Calendar.getInstance();
		Date heute = new Date();
		try {
			SimpleDateFormat sDF = new SimpleDateFormat("d.M.y", homeLocale);
			heute = sDF.parse(sDF.format(heute));
			heute.setTime(heute.getTime()+(1000*60*60*12));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String datumInfo = "";
		int dayDifference = 0;
		tempCalendar.setTime(heute);
		tempCalendar.add(Calendar.DAY_OF_MONTH, -tageVorher -1);
		for (int i = 0; i < tageVorher; i++) {
			tempCalendar.add(Calendar.DAY_OF_MONTH, 1);
			dayDifference = getDayDifference(schichtmodel, startingDate,
					tempCalendar.getTime());
			
			listeVorher.add(new ListenEintrag(tempCalendar.getTime(),
				schichtmodel.get(dayDifference)));
		}
		
		dayDifference = getDayDifference(schichtmodel, startingDate,
				heute);
		eintragHeute = new ListenEintrag(heute, schichtmodel.get(dayDifference));
		
		tempCalendar.setTime(heute);
		for (int i = 0; i < tageNachher; i++) {
			tempCalendar.add(Calendar.DAY_OF_MONTH, 1);
			
			dayDifference = getDayDifference(schichtmodel, startingDate,
					tempCalendar.getTime());
			listeNachher.add(new ListenEintrag(tempCalendar.getTime(),
					schichtmodel.get(dayDifference)));
		}
		
	}

	private int getDayDifference(List<String> schichtmodel, Date startingDate,
			Date actualDate) {
		int actualTage = (int) (actualDate.getTime()/(1000*60*60*24));
		int startingTage = (int) (startingDate.getTime()/(1000*60*60*24));
		
		int dayDifference = (int) ((actualDate.getTime()-startingDate.getTime())/(1000*60*60*24));
		
		if (dayDifference < 0) {
			dayDifference = dayDifference * -1;
			dayDifference = dayDifference % (schichtmodel.size());
			dayDifference = dayDifference * -1;
			dayDifference = dayDifference + schichtmodel.size();
			// 0  -->  0
			//-1  --> 34
			//-2  --> 33
			//-3  --> 32
			//-33 -->  2
			//-34 -->  1 
			//-35 -->  0 (35)
			//-36 --> 34 
			
		}
		dayDifference = dayDifference % (schichtmodel.size());
		
		
		return dayDifference;
	}

	public List<ListenEintrag> getListeVorher() {
		return listeVorher;
	}

	public ListenEintrag getEintragHeute() {
		return eintragHeute;
	}

	public List<ListenEintrag> getListeNachher() {
		return listeNachher;
	}

	public Locale getHomeLocale() {
		return homeLocale;
	}
	
	
	
}

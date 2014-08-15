package at.zachner.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class SchichtModels {
	
	private Map<String,List<String>> schichtModelMap = new HashMap<String, List<String>>();
	private Map<String,Date> startingDateMap = new HashMap<>();
	private String spaetText;
	private String mittelText;
	private String fruehText;
	private String freiText;
	private int days;
	
	private SimpleDateFormat dF = new SimpleDateFormat("d.M.y");
	
	public SchichtModels() {
		ResourceBundle schichtModelProp = ResourceBundle.getBundle("SchichtModels");
		
		this.spaetText = schichtModelProp.getString("spaetText");
		this.mittelText = schichtModelProp.getString("mittelText");
		this.fruehText = schichtModelProp.getString("fruehText");
		this.freiText = schichtModelProp.getString("freiText");
		
		String daysString = schichtModelProp.getString("days");
		this.days = Integer.valueOf(daysString);
		
		String models = schichtModelProp.getString("models");
		String[] modelArray = models.split(", ");
		for (String schichtModelName: modelArray) {
			String startingDateString = schichtModelProp.getString(schichtModelName.toLowerCase() + "StartingDate");
			Date startingDate = null;
			try {
				startingDate = dF.parse(startingDateString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			startingDateMap.put(schichtModelName, startingDate);
			
			List<String> schichtModel = new ArrayList<String>();
			for (int i = 1 ; i <= this.days ; i++) {
				String dayString = schichtModelProp.getString(schichtModelName.toLowerCase() + "Day" + i);
				switch (dayString) {
					case "spaetText":
						schichtModel.add(this.spaetText);
						break;
					case "mittelText":
						schichtModel.add(this.mittelText);
						break;
					case "fruehText":
						schichtModel.add(this.fruehText);
						break;
					case "freiText":
						schichtModel.add(this.freiText);
						break;
					default:
						schichtModel.add("Fehler");
						break;
				}
			}
			schichtModelMap.put(schichtModelName, schichtModel);
			
			
		}
	}

	public List<String> getSchichtmodel(String model) {
		return this.schichtModelMap.get(model);
	}
	
	public Date getStartingDate(String model) {
		return this.startingDateMap.get(model);
	}

}

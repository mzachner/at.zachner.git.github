package at.zachner.domain;

import java.util.Date;

public class ListenEintrag {
	
	private Date datum;
	private String datumInfo;
	
	
	
	public ListenEintrag(Date datum, String datumInfo) {
		super();
		this.datum = datum;
		this.datumInfo = datumInfo;
	}
	
	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getDatumInfo() {
		return datumInfo;
	}
	
	public void setDatumInfo(String datumInfo) {
		this.datumInfo = datumInfo;
	}

}

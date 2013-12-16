package com.ctbri.srcapi.tool;

public class Review {

	public String username="";
	public String comment="";
	public String time="";
	public String source="";
	@Override
	public String toString() {
		return username +" : "+comment+" : "+time+" : "+source;
	}
}

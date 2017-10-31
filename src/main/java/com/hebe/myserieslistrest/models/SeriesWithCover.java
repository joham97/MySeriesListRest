package com.hebe.myserieslistrest.models;

import com.hebe.thetvdbapi.models.Series;

public class SeriesWithCover extends Series {

	private String filename;
	
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return this.filename;
	}
		
}

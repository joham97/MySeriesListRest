package com.hebe.myserieslistrest.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hebe.thetvdbapi.BadTokenException;
import com.hebe.thetvdbapi.TheTVDBApi;
import com.hebe.thetvdbapi.models.Series;

@RestController
public class MySeriesListController {

    private void refreshToken() {
		try {
			TheTVDBApi.useToken(TheTVDBApi.requestNewToken("B2FABF16E0600F2C"));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /*
     * Search
     */
	
    @RequestMapping("/search")
    public List<Series> getSearch(@RequestParam(value="keywords", defaultValue="") String keywords) {
    	try {
			return search(keywords);
		} catch (BadTokenException e) {
			try {
				refreshToken();
				return search(keywords);
			} catch (BadTokenException e1) {
				e1.printStackTrace();
			}
		}
    	
    	return new ArrayList<Series>();
    }
    
    private List<Series> search(String keywords) throws BadTokenException {
    	try {
			return TheTVDBApi.searchForSeries(keywords);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return new ArrayList<Series>();
    }

    /*
     * Search Series By Id
     */

    @RequestMapping("/series")
    public Series getSeriesById(@RequestParam(value="id", defaultValue="") String id) {
    	try {
			return seriesById(id);
		} catch (BadTokenException e) {
			try {
				refreshToken();
				return seriesById(id);
			} catch (BadTokenException e1) {
				e1.printStackTrace();
			}
		}
    	
    	return null;
    }
    
    private Series seriesById(String id) throws BadTokenException {
    	try {
			return TheTVDBApi.searchForSeriesById(id);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }

}

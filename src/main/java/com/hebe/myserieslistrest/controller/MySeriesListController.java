package com.hebe.myserieslistrest.controller;

import java.util.Comparator;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omertron.thetvdbapi.TheTVDBApi;
import com.omertron.thetvdbapi.TvDbException;
import com.omertron.thetvdbapi.model.Episode;
import com.omertron.thetvdbapi.model.Series;

@RestController
public class MySeriesListController {
    
	private static TheTVDBApi api = new TheTVDBApi("B2FABF16E0600F2C");
	
	private static final String language = "en";
	
    /*
     * Search
     */
	
    @RequestMapping("/search")
    public List<Series> getSearch(@RequestParam(value="keywords", defaultValue="") String keywords) {
    	try {
    		List<Series> series = api.searchSeries(keywords, language);
	    	if(series.size() < 10){
	    		for(int i = 0; i < series.size(); i++){
	    			series.set(i, api.getSeries(series.get(i).getId(), series.get(i).getLanguage()));
	    		}
	    		series.sort(Comparator.comparing(Series::getRating).reversed());
    		}else{
	    		series.sort(new Comparator<Series>() {
					@Override
					public int compare(Series o1, Series o2) {
						if(o1.getBanner() == null && o2.getBanner() != null){
							return 1;
						}else if(o1.getBanner() != null && o2.getBanner() == null){
							return -1;
						}
						return 0;
					}
				});
    		}
			return series;
		} catch (TvDbException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    /*
     * Series by Id
     */

    @RequestMapping("/series")
    public Series getSeriesById(@RequestParam(value="id", defaultValue="") String id) {
    	try {
			return api.getSeries(id, language);
		} catch (TvDbException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /*
     * Episodes of Series
     */

    @RequestMapping("/series/episodes")
    public List<Episode> getEpisodesOfSeries(@RequestParam(value="id", defaultValue="") String id) {
    	try {
			return api.getAllEpisodes(id, language);
		} catch (TvDbException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
}

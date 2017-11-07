package com.hebe.myserieslistrest.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
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
    		List<Episode> episodes = api.getAllEpisodes(id, language);
    		
    		try {
        		ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("episodes.data"));
        		Episode[] episodesArray = new Episode[episodes.size()];
        		for(int i = 0; i < episodes.size(); i++){
        			episodesArray[i] = episodes.get(i);
        		}
        		objectOutputStream.writeObject(episodesArray);
				objectOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		
			return episodes;
		} catch (TvDbException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /*
     * Offline Series
     */

    @RequestMapping("/offline/series")
    public Series getOfflineSeries() {
    	Series series = null;
		try {
    		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("series.data"));
    		series = (Series) objectInputStream.readObject();
    		objectInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return series;
    }
    
    /*
     * Offline Episodes
     */

    @RequestMapping("/offline/episodes")
    public List<Episode> getOfflineEpisodes() {
    	List<Episode> episodes = new ArrayList<Episode>();
		try {
    		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("episodes.data"));
    		for(Episode episode : (Episode[]) objectInputStream.readObject()){
    			episodes.add(episode);
    		}
    		
    		objectInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return episodes;
    }
    
    
}

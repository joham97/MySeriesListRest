package com.hebe.myserieslistrest.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hebe.myserieslistrest.models.SeriesWithCover;
import com.hebe.thetvdbapi.BadTokenException;
import com.hebe.thetvdbapi.TheTVDBApi;
import com.hebe.thetvdbapi.models.Season;
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
     * Search
     */
	
    @RequestMapping("/searchC")
    public List<SeriesWithCover> getSearchWithCover(@RequestParam(value="keywords", defaultValue="") String keywords, @RequestParam(value="thumbnail", defaultValue="false") boolean thumbnail) {
    	try {
			return searchWithCover(keywords, thumbnail);
		} catch (BadTokenException e) {
			try {
				refreshToken();
				return searchWithCover(keywords, thumbnail);
			} catch (BadTokenException e1) {
				e1.printStackTrace();
			}
		}
    	
    	return new ArrayList<SeriesWithCover>();
    }
    
    private List<SeriesWithCover> searchWithCover(String keywords, boolean thumbnail) throws BadTokenException {
    	try {
    		List<Series> series = TheTVDBApi.searchForSeries(keywords);
    		List<SeriesWithCover> seriesWithCover = new ArrayList<>();
    		
    		for(Series singleSeries : series){
    			SeriesWithCover singleSeriesWithCover = new SeriesWithCover();
    			singleSeriesWithCover.setId(singleSeries.getId());
    			singleSeriesWithCover.setFirstAired(singleSeries.getFirstAired());
    			singleSeriesWithCover.setNetwork(singleSeries.getNetwork());
    			singleSeriesWithCover.setOverview(singleSeries.getOverview());
    			singleSeriesWithCover.setSeriesName(singleSeries.getSeriesName());
    			singleSeriesWithCover.setStatus(singleSeries.getStatus());
    			if(thumbnail){
        			singleSeriesWithCover.setFilename(TheTVDBApi.getThumbnailPathById(""+singleSeries.getId()));
    			}else{
        			singleSeriesWithCover.setFilename(TheTVDBApi.getCoverPathById(""+singleSeries.getId()));    				
    			}
    			seriesWithCover.add(singleSeriesWithCover);
    		}
    		
    		return seriesWithCover;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return new ArrayList<SeriesWithCover>();
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
    
    /*
     * Cover Of Series
     */

    @RequestMapping("/series/cover")
    public String getCover(@RequestParam(value="id", defaultValue="") String id, @RequestParam(value="thumbnail", defaultValue="false") boolean thumbnail) {
    	try {
			return cover(id, thumbnail);
		} catch (BadTokenException e) {
			try {
				refreshToken();
				return cover(id, thumbnail);
			} catch (BadTokenException e1) {
				e1.printStackTrace();
			}
		}
    	
    	return null;
    }
    
    private String cover(String id, boolean thumbnail) throws BadTokenException {
    	try {
    		if(thumbnail){
    			return TheTVDBApi.getThumbnailPathById(id);
    		}else{
    			return TheTVDBApi.getCoverPathById(id);
    		}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
        
    /*
     * Get Seasons
     */

    @RequestMapping("/series/seasons")
    public List<Season> getSeasons(@RequestParam(value="id", defaultValue="") String id) {
    	try {
			return seasons(id);
		} catch (BadTokenException e) {
			try {
				refreshToken();
				return seasons(id);
			} catch (BadTokenException e1) {
				e1.printStackTrace();
			}
		}
    	
    	return null;
    }
    
    private List<Season> seasons(String id) throws BadTokenException {
    	try {
    		return TheTVDBApi.getSeasons(id);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    /*
     * Cover Of Episode
     */

    @RequestMapping("/episode/cover")
    public String getCoverOfEpisode(@RequestParam(value="id", defaultValue="") String id, @RequestParam(value="thumbnail", defaultValue="false") boolean thumbnail) {
    	try {
			return coverOfEpisode(id, thumbnail);
		} catch (BadTokenException e) {
			try {
				refreshToken();
				return coverOfEpisode(id, thumbnail);
			} catch (BadTokenException e1) {
				e1.printStackTrace();
			}
		}
    	
    	return null;
    }
    
    private String coverOfEpisode(String id, boolean thumbnail) throws BadTokenException {
    	try {
    		if(thumbnail){
    			return TheTVDBApi.getEpisodeThumbnailPathById(id);
    		}else{
    			return TheTVDBApi.getEpisodeCoverPathById(id);
    		}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }

}

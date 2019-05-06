package com.project.watchdatabase;

/*
 * "Interface" for the Scrapers
 * 
 * @author Bassil Alcheikh
 * 
 */
public interface Scraper {
	
	public String getSearchURL();
	
	public String[][] getScrapedResults();
	
}

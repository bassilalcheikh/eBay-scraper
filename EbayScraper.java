package com.project.watchdatabase;

import java.sql.SQLException;
/*
 * @author Bassil Alcheikh
 * 
 * This class will scrape the html we need from the eBay page.
 * The basic inputs include the search words and (optionally) the price range
 */
import java.util.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

public class EbayScraper implements Scraper{
	
	private String searchBrand;
	private String searchModel;
	private double lower_price_bound;
	private double upper_price_bound;
	private ChromeDriver driver; 
	private String year;
	private ArrayList<String> dataTypes;
	private ChromeOptions chromeOptions;
	
	// CONSTRUCTORS 
	/*
	 * The default constructor below will be used in future projects; for now, use the constructor on the bottom
	public EbayScraper() {
		searchBrand = "";
		searchModel = "";
		lower_price_bound = 0;
		upper_price_bound = 1000000;
		System.setProperty("webdriver.chrome.driver", "/Users/bassilalcheikh/eclipse-workspace/ChronoPriceWatch/chromedriver");
		dataTypes = new ArrayList<String>();
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("headless"); // comment this out if you want to see the browser in action
		driver = new ChromeDriver(chromeOptions);
	}
	*/
	
	public EbayScraper(String given_search_brand, String given_search_model, String directory_path) {
		searchBrand = given_search_brand;
		searchModel = given_search_model;
		lower_price_bound = 0;
		upper_price_bound = 1000000;
		System.setProperty("webdriver.chrome.driver", directory_path+"/chromedriver");
		dataTypes = new ArrayList<String>();
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("headless"); // comment this out if you want to see the browser in action
		driver = new ChromeDriver(chromeOptions);
	}
	
	// GETTERS & SETTERS
	public double getLower_price_bound() {
		return lower_price_bound;
	}

	public void setLower_price_bound(double lower_price_bound) {
		this.lower_price_bound = lower_price_bound;
	}

	public double getUpper_price_bound() {
		return upper_price_bound;
	}

	public void setUpper_price_bound(double upper_price_bound) {
		this.upper_price_bound = upper_price_bound;
	}

	public String getSearchBrand() {
		return searchBrand;
	}

	public void setSearchBrand(String inputBrand) {
		this.searchBrand = inputBrand;
	}
	
	public String getSearchModel() {
		return searchModel;
	}

	public void setSearchModel(String inputModel) {
		this.searchModel = inputModel;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public ArrayList<String> getDataTypes() {
		return dataTypes;
	}

	public void setDataTypes(ArrayList<String> dataTypes) {
		this.dataTypes = dataTypes;
	}

	// INSTANCE METHODS	
	public String getSearchURL() {
		// returns the URL with the arguments appended; string gets used in the Chromedriver instance
		return "https://www.ebay.com/sch/i.html?_nkw="+searchBrand+" "+searchModel+"&_in_kw=1&_ex_kw=&_sacat=0&LH_Sold=1&_mPrRngCbx=1&_udlo="+lower_price_bound+"&_udhi="+upper_price_bound+"&_samilow=&_samihi=&_sadis=15&_stpos=60611&_sargn=-1%26saslc%3D1&_salic=1&_sop=12&_dmd=1&_ipg=200&LH_Complete=1&_fosrp=1";
	}

	public String[][] getScrapedResults() {
		String[][] scraped_results = null;
		String baseUrl = getSearchURL();
	    driver.get(baseUrl);
	    
		try {
			// this method scrapes data from eBay; without this, the class is completely useless.
			
		    // we give a specific "type" setting here, because the following is specific to the html tag extractions below
		    String[] arrayTypes = {"String", "String", "String", "String", "int", "String", "String", "double", "double", "String", "String"};
		    dataTypes = new ArrayList<String>(Arrays.asList(arrayTypes));
		    
		    // IDs 
		    List<WebElement> wd_id_list = driver.findElements(By.cssSelector("div[class='lvpic pic img left']"));
		    // titles 
		    List<WebElement> wd_titles_list = driver.findElements(By.cssSelector("h3[class='lvtitle']"));
		    // links 
		    List<WebElement> wd_links_list = driver.findElements(By.cssSelector("a[class='vip']"));
		    // auction times 
		    List<WebElement> wd_times_list = driver.findElements(By.cssSelector("span[class='tme']"));	    
		    // bid counts 
		    List<WebElement> wd_bids_list = driver.findElements(By.cssSelector("li[class='lvformat']"));
		    // conditions 
		    List<WebElement> wd_conditions_list = driver.findElements(By.cssSelector("div[class='lvsubtitle']"));
		    // prices 
		    List<WebElement> wd_prices_list = driver.findElements(By.cssSelector("span[class='bold bidsold']"));
		    // shipping cost
		    List<WebElement> wd_shipping_list = driver.findElements(By.cssSelector("li[class='lvshipping']"));
		    
		    int scraped_listings_count = wd_id_list.size();
		    
		    ArrayList<String> wd_format_list = new ArrayList<String>();
		    // the following looks at the bid count; if 0, then the listing was a "buy it now" rather than an auction
		    for(int k = 0; k < scraped_listings_count; k++) {
		    	String bid_value = wd_bids_list.get(k).getText();
		    	if(bid_value.contentEquals("Best offer accepted") || bid_value.contentEquals("Buy It Now")) {
		    		wd_format_list.add("Sale");
		    	}
		    	else {
		    		wd_format_list.add("Auction");
		    	}
		    }
		    // ================================================================================================
		    scraped_results = new String[scraped_listings_count][11];
		    
		    for(int i = 0; i < scraped_listings_count; i++) {
		    	scraped_results[i] = new String[] {
		    		wd_id_list.get(i).getAttribute("iid"), // listing ID
		    		DataCleanup.removeApostrophe(wd_titles_list.get(i).getText()), // listing title
		    		DataCleanup.removeApostrophe(wd_links_list.get(i).getAttribute("href")), // link
		    		DataCleanup.formatDateTime(wd_times_list.get(i).getText(), year), // date & time
		    		Integer.toString(DataCleanup.extractInteger(wd_bids_list.get(i).getText())), // bids
		    		wd_format_list.get(i), // listing format
		    		wd_conditions_list.get(i).getText(), // place condition here
		    		Double.toString(DataCleanup.extractDouble(DataCleanup.extractFirstAmount(wd_prices_list.get(i).getText()))),
		    		Double.toString(DataCleanup.extractDouble(wd_shipping_list.get(i).getText())),
		    		this.searchBrand,
		    		this.searchModel
		    	};
		    }	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		driver.close();
	    driver.quit();
		return scraped_results;
		
	}
	@Override
	public String toString() {
		return "EbayScraper [searchBrand=" + searchBrand + ", searchModel=" + searchModel + "]";
	}
		
}

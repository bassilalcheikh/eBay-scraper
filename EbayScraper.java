/*
 * This class will scrape the html we need from the eBay page
 */
import java.util.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

public class EbayScraper {
	
	//private String url;
	private String searchinput;
	private double lower_price_bound;
	private double upper_price_bound;
	private ChromeDriver driver; 
	
	// CONSTRUCTORS
	public EbayScraper() {
		searchinput = "";
		lower_price_bound = 0;
		upper_price_bound = 1000000;
		System.setProperty("webdriver.chrome.driver", "/Users/bassilalcheikh/eclipse-workspace/ChronoPriceWatch/chromedriver");
		driver = new ChromeDriver();
	}
	
	public EbayScraper(String given_search_keywords) {
		searchinput = given_search_keywords;
		lower_price_bound = 0;
		upper_price_bound = 1000000;
		System.setProperty("webdriver.chrome.driver", "/Users/bassilalcheikh/eclipse-workspace/ChronoPriceWatch/chromedriver");
		driver = new ChromeDriver();
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

	public String getSearchinput() {
		return searchinput;
	}

	public void setSearchinput(String searchinput) {
		this.searchinput = searchinput;
	}
	
	// INSTANCE METHODS	
	public String getSearchURL() {
		return "https://www.ebay.com/sch/i.html?_nkw="+searchinput+"&_in_kw=1&_ex_kw=&_sacat=0&LH_Sold=1&_mPrRngCbx=1&_udlo="+lower_price_bound+"&_udhi="+upper_price_bound+"&_samilow=&_samihi=&_sadis=15&_stpos=60611&_sargn=-1%26saslc%3D1&_salic=1&_sop=12&_dmd=1&_ipg=200&LH_Complete=1&_fosrp=1";
	}
	

	public String[][] getScrapedResults() {
		
		String baseUrl = getSearchURL();
	    driver.get(baseUrl);
	    
	    // IDs - OK; need to use .getAttribute("iid");
	    List<WebElement> wd_id_list = driver.findElements(By.cssSelector("div[class='lvpic pic img left']"));
	    // titles - good
	    List<WebElement> wd_titles_list = driver.findElements(By.cssSelector("h3[class='lvtitle']"));
	    // links - good
	    List<WebElement> wd_links_list = driver.findElements(By.cssSelector("a[class='vip']"));
	    // auction times - good
	    List<WebElement> wd_times_list = driver.findElements(By.cssSelector("span[class='tme']"));
	    // bid counts - good
	    List<WebElement> wd_bids_list = driver.findElements(By.cssSelector("li[class='lvformat']"));
	    // prices - good
	    List<WebElement> wd_prices_list = driver.findElements(By.cssSelector("span[class='bold bidsold']"));
	    // shipping - good
	    List<WebElement> wd_shipping_list = driver.findElements(By.cssSelector("span[class='ship']"));
	    
	    int scraped_listings_count = wd_id_list.size();
	    
	    String[][] scraped_results = new String[scraped_listings_count][7];
	    
	    for(int i = 0; i < scraped_listings_count; i++) {
	    	scraped_results[i][0] = wd_id_list.get(i).getAttribute("iid");
	    	scraped_results[i][1] =	wd_titles_list.get(i).getText();
	    	scraped_results[i][2] = wd_links_list.get(i).getAttribute("href");
	    	scraped_results[i][3] =	wd_times_list.get(i).getText();
	    	scraped_results[i][4] =	wd_bids_list.get(i).getText();
	    	scraped_results[i][5] =	wd_prices_list.get(i).getText();
	    	scraped_results[i][6] = wd_shipping_list.get(i).getText();
	    }
	    
	    //close Chrome.
	    driver.close();
	    return scraped_results;
		
	}
	
}

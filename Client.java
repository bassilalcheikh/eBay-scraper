package com.project.watchdatabase;

/*
 * Main Client 
 * Bassil Alcheikh, 2019
 */
import java.io.*;
import java.nio.file.Paths; // we will use this once, just for getting user's directory path
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
// I will eventually replace the chart with a better/more flexible graphics library
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public class Client {

	public static void main(String[] args) throws SQLException, InterruptedException {
		
		final String projectDirectoryPath = Paths.get("").toAbsolutePath().toString();
		final String dataBase = "jdbc:sqlite:watchrecords.db";
		
		Scanner scan = new Scanner(System.in);
		String choice = "";
		
		while(!(choice.toLowerCase()).contentEquals("q")) {
			System.out.println("Main menu: enter 'i' to initiate ETL, 's' to search current results, 'v' to view line chart, 'c' to setup tables, or 'q' to quit.");
			choice = scan.nextLine();
			
			if(choice.toLowerCase().contentEquals("i")) {
//================================================================================================================================================
				// PART 1: launches ETL script
				System.out.println("Initializing ETL process now.");
				
				ArrayList<String> columns = null;
				ResultSet rs_column_results = null;
				SQLiteExecutor se_i = null;
				try {
					se_i = new SQLiteExecutor(dataBase, "searchlist");
					columns = se_i.getTableColumns();
					
					String[] desiredColumns =  new String[] {columns.get(1), columns.get(2), columns.get(3), columns.get(4)};
					
					rs_column_results = se_i.selectData(desiredColumns);
					// displays data in "column_results" that will be used to feed arguments into ebay scraper
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// PART 2: launch webdriver code
				se_i.setTableName("rawebaysearchresults");
				
				try {	
					while(rs_column_results.next()) {
						EbayScraper es = new EbayScraper(rs_column_results.getString(1), rs_column_results.getString(2), projectDirectoryPath );
						es.setLower_price_bound(rs_column_results.getDouble(3));
						es.setYear("2019");
						
						System.out.println("Process starting for: "+es.toString());
						String[][] watches = es.getScrapedResults(); 
						ArrayList<String> data_types = es.getDataTypes(); // have to run this AFTER getScrapedResults
						// the above gets the scraped values
						
						se_i.insertData(data_types, watches);
						System.out.println("ETL process finished for "+es.toString());
						// the above inserts stuff into the database
					}
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
				se_i.close();
			}
//================================================================================================================================================
		
			else if (choice.toLowerCase().contentEquals("s")){
				SQLiteExecutor sq_s = new SQLiteExecutor(dataBase, null);
				
				System.out.println("Enter '1' for searchlist and '2' for rawebaysearchresults:");
				
				String section_choice = scan.nextLine();
				if(section_choice.toLowerCase().equals("1")) {
					sq_s.viewTableData("searchlist");
				}
				else if(section_choice.toLowerCase().equals("2")) {
					sq_s.viewTableData("rawebaysearchresults");
					
				}
				else {
					System.out.println("Bad entry- try again.");
				}
			}
//================================================================================================================================================
			else if (choice.toLowerCase().contentEquals("v")) {				
				SQLiteExecutor sq_v = new SQLiteExecutor(dataBase, "searchlist");
				
				String[] menuOptionsColumns = new String[] {"search_id", "search_brand", "search_model"};
				ArrayList<String[]> menu_options = sq_v.transformResultSet(sq_v.selectData(menuOptionsColumns));
				
				for(int i = 0; i < menu_options.size(); i++) {
					for(int j = 0; j < 3; j++) {
						System.out.print(menu_options.get(i)[j]+" ");
					}
					System.out.println("\n");
				}
				System.out.println("To view an item's trend, select its respective number from the menu above: ");
				
				String section_choice = scan.nextLine();
				int optionIndex = Integer.valueOf(section_choice);
				
				if(optionIndex <= menu_options.size() && optionIndex > 0) {
					
					String chosen_brand = menu_options.get(optionIndex-1)[1];
					String chosen_model = menu_options.get(optionIndex-1)[2];
					String chart_title = "Prices for "+chosen_brand+" "+chosen_model;
					String key_details = "Realized Prices from eBay";
					
					// note: the below code should be (practically) immune against SQL injections, because the chosen brand  
					// and chosen model values come directly from admin-input values
					String query_code = "SELECT julianday(sale_date_time), price FROM rawebaysearchresults WHERE brand = '"+chosen_brand+"' AND model = '"+chosen_model+"' ORDER BY sale_date_time ASC;";
					ArrayList<String[]> query_code_results = sq_v.plainSelect(query_code); 
					
					int data_points = query_code_results.size();
					double[] datetimes = new double[data_points];
					double[] prices = new double[data_points];
					
					for(int k = 0; k < data_points; k++ ) {
						datetimes[k] = Double.valueOf(query_code_results.get(k)[0]);
						prices[k] = Double.valueOf(query_code_results.get(k)[1]);
					}
										
					XYChart chart = QuickChart.getChart(chart_title, "Time (in Julian days)", "Dollars", key_details, datetimes, prices);

				    // Shows it
				    SwingWrapper<XYChart> sw = new SwingWrapper<XYChart>(chart);
				    sw.displayChart();
				    
				    sq_v.close();
				}
				else {
					System.out.println("Sorry, option doesn't exist.");
				}
				
			}
//================================================================================================================================================			
			else if (choice.toLowerCase().contentEquals("c")){
				
				System.out.println("Setting up tables.");
				
				/* NOTE: down the line, there will be more of these SQL scripts, 
				 * 		so I will create an ArrayList of File objects. There are 
				 * 		just 2 File objects now, but in the future, there will be
				 * 		enough to make the ArrayList worthwhile.
				 */
				File createEbayResults = new File(projectDirectoryPath+"/src/createEbaySearchResults.sql");
				File createSearchList = new File(projectDirectoryPath+"/src/createSearchListTable.sql");
				
				ArrayList<File> sqlScripts = new ArrayList<File>();
				sqlScripts.add(createSearchList);
				sqlScripts.add(createEbayResults);
				 
				Scanner fileScanner;
				SQLiteExecutor se_c = new SQLiteExecutor(dataBase, null);
								
				for(int f = 0; f < sqlScripts.size(); f++) {
					
					String sqlcode = "";
					File myFile = sqlScripts.get(f);
					System.out.println("Running file: "+myFile.toString());

					try{ 
				    	fileScanner = new Scanner(myFile);
				    	while (fileScanner.hasNextLine()) {
				    		sqlcode += fileScanner.nextLine();
				    	}
				    }
				    catch (IOException e){
				        e.printStackTrace();
				    }		
					se_c.plainExecute(sqlcode);
				}   
			}
//================================================================================================================================================			
			else if(choice.toLowerCase().contentEquals("q")){
				break;
			}
			else
				System.out.println("Invalid entry; try again.");
		}
		System.out.println("Process finished.");
		scan.close();
	}
}
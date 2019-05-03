/*
 * Search Routine
 * Bassil Alcheikh, April 2019
 * 
 * "Search Routine" queries the database for all the items to scrape
 * 
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchRoutine {

	
	//private ArrayList<String[]> QueryResults;
	
	public SearchRoutine(){
		
	}
	
	public ArrayList<String[]> executeDBQuery() {
		// executes search from the database and stores results in array, to be searched
		
		ArrayList<String[]> QueryResults = new ArrayList<String[]>();
		
		Connection c = null;
	      
		String uploadDataScript = "select search_brand, search_model, price_min, price_max from searchlist;";
		
		try {
			//Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:watchrecords.db");
			Statement stmt = null;
			
			System.out.println("Opened database successfully.");
			System.out.println("Querying now...");
			
			stmt = c.createStatement();
	        			
			ResultSet rs = stmt.executeQuery(uploadDataScript);
			
			while(rs.next()) {
				String[] tempString = new String[4];
				tempString[0] = rs.getString("search_brand");
				tempString[1] = rs.getString("search_model");
				tempString[2] = Double.toString(rs.getDouble("price_min"));
				tempString[3] = Double.toString(rs.getDouble("price_max"));
				/*
				String[] tempString = new String[]{
					rs.getString("search_brand"),
					rs.getString("search_model"),
					Double.toString(rs.getDouble("price_min")),
					Double.toString(rs.getDouble("price_max"))
				};
				*/
				
				QueryResults.add(tempString);
				
			}
	       
	        stmt.close();
	        c.close();
			
		} 
		catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		return QueryResults;
		
	}

}

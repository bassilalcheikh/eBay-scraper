import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SQLiteJDBConnect {
	/*
	private String databasepath;
	
	public SQLiteJDBConnect(String inputDataBasePath) {
		databasepath = inputDataBasePath;
	}
	*/

	public static void main( String args[] ) {
		
		
		// PART I: creating SQL statement
		
		String table_name = "rawebaysearchresults";
		GenSQLStatements SQLStatement = new GenSQLStatements(table_name);		
		
		EbayScraper es = new EbayScraper("Rolex", "16610");
		es.setLower_price_bound(2001);
		es.setYear("2019");
		
		String[][] watches = es.getScrapedResults();
		ArrayList<String> data_types = es.getDataTypes();
		
		System.out.println(SQLStatement.getInsertStatement(watches, data_types));
		//String uploadDataScript = SQLStatement.getInsertStatement(watches, data_types);
		
	
		// PART II: executing code
		/*
		Connection c = null;
      
		//String uploadDataScript = "select search_brand, search_model, price_min, price_max from searchlist;";
		
		try {
			//Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:watchrecords.db");
			Statement stmt = null;
			
			System.out.println("Opened database successfully.");
			
			stmt = c.createStatement();
	        
	        //stmt.executeUpdate(uploadDataScript);
			/*
			ResultSet rs = stmt.executeQuery(uploadDataScript);
			while(rs.next()) {
				
				String db_search_brand 	= rs.getString("search_brand");
				String db_search_model 	= rs.getString("search_model");
				double db_price_min 	= rs.getDouble("price_min");
				double db_price_max 	= rs.getDouble("price_max");
				
				System.out.println( "BRAND = " 		+ db_search_brand );
				System.out.println( "MODEL = "		+ db_search_model);
				System.out.println( "PRICE MIN = " 	+ db_price_min);
				System.out.println( "PRICE MAX = " 	+ db_price_max);
				
				/*
				String db_listing 	= rs.getString("listing_number");
				String db_title 	= rs.getString("listing_title");
				String db_link 		= rs.getString("listing_link");
				String db_datetime 	= rs.getString("sale_date_time");
				int db_bids 		= rs.getInt("bid_count");
				
				String db_format 	= rs.getString("listing_format");
				String db_condition = rs.getString("condition");
				double db_price 	= rs.getDouble("price");
				
				double db_shipping 	= rs.getDouble("price_shipping");
				String db_brand		= rs.getString("brand");
				String db_model 	= rs.getString("model");
				
				System.out.println( "ID = " + db_listing );
				System.out.println( "TITLE = " + db_title );
				System.out.println( "LINK = " + db_link );
				System.out.println( "DATETIME = " + db_datetime );
				System.out.println( "BIDS = " + db_bids );
				System.out.println( "FORMAT = "+db_format);
				
				System.out.println( "DATETIME = " + db_datetime );
				System.out.println( "BIDS = " + db_condition );
				System.out.println( "PRICE = "+db_price);
				
				System.out.println( "SHIPPING = " + db_shipping );
				System.out.println( "BRAND = " + db_brand );
				System.out.println( "MODEL = "+db_model);
				
			}
			
			System.out.println(rs);
			
	        stmt.close();
	        c.close();
			
		} 
		catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		*/
	}
}

//https://stackoverflow.com/questions/33332639/problems-connecting-to-sqlite-database-in-java
// https://www.tutorialspoint.com/sqlite/sqlite_java.htm
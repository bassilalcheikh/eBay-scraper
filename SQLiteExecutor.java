package com.project.watchdatabase;

/* SQL Execution
 * 
 * @author Bassil Alcheikh
 *
 * Takes SQL script from external SQL files and then turns them into executable Strings
 */
import java.sql.*;
import java.util.ArrayList;

public class SQLiteExecutor implements SQLExecutor {
	// class operates on ONE CONNECTION at a time!
	
	// instance variables
	private String tableName;
	private String databaseName;
	private Connection c;
	
	// constructor
	public SQLiteExecutor(String givenDatabaseName, String givenTableName) throws SQLException {
		databaseName = givenDatabaseName;
		tableName = givenTableName;
		c = DriverManager.getConnection(databaseName);
		System.out.println("Opened database successfully.");
	}
	
	// getters-setters
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	// class methods
	
	// gets table columns 
	public ArrayList<String> getTableColumns() throws SQLException {
		// WORKS- fetches column names (Strings) for a table
		ArrayList<String> tableColumns = new ArrayList<String>();
		
		PreparedStatement pstmt = c.prepareStatement("PRAGMA table_info("+tableName+");");  // pstmt is for selecting
		ResultSet prs = pstmt.executeQuery(); 
		
		while(prs.next()) {
			tableColumns.add(prs.getString(2));
		}
		return tableColumns;
	}

	// transforms resultset into ArrayList<String[]>
	public ArrayList<String[]> transformResultSet(ResultSet prs, int column_count) throws SQLException {
		// converts the ResultSet into an iterable list structure
		int dim = column_count;
		
		ArrayList<String[]> tableresults = new ArrayList<String[]>();
		while( prs.next() ){
		    String[] row = new String[dim];
		    // for some reason, index is 1. This is probably because the data is inherently stored in 
		    // something besides an array
		    for( int col = 1; col <= dim; col++ ){
		    	// the code below doesn't make sense to me at the moment- but it works.		
		    	Object obj = prs.getObject( col );
		    	row[col-1] = (obj == null) ?null:obj.toString();     
		    }
		    tableresults.add( row );
		} 
		return tableresults; 
	}
	
	// insert a SQL string and executes command
	// will eventually work to remove possibilities for SQL injection
	public void plainExecute(String code) throws SQLException {
		PreparedStatement prepstmt = c.prepareStatement(code); 
		prepstmt.executeUpdate(); //ResultSet object	
	}
	/*
	// like the above method, but returns data, stores it into 
	public void plainExecute(String code) throws SQLException {
		PreparedStatement prepstmt = c.prepareStatement(code); 
		prepstmt.executeUpdate(); //ResultSet object	
	}
	*/
	// select data from the specified columns
	public ResultSet selectData(String[] given_columns) throws SQLException {
		// input: column names (String array); output: results from db table (ResultSet Object)
		int dim = given_columns.length;
		
		String search_string = "SELECT ";
		for(int w = 0; w < dim-1; w++) {
			search_string += given_columns[w]+", ";
		}
		search_string += given_columns[dim-1]+" FROM "+tableName+";";
				
		PreparedStatement pstmt = c.prepareStatement(search_string); 
		return pstmt.executeQuery(); //ResultSet object	
	}
	
	// view entire contents of a table
	public void viewTableData(String tableName) throws SQLException {
		// input: column names (String array); output: results from db table (ResultSet Object)
		
		String search_string = "SELECT * FROM "+tableName+";";
		
		PreparedStatement pstmt = c.prepareStatement(search_string); 
		ResultSet results = pstmt.executeQuery(); 
		
		ArrayList<String[]> listedResults = transformResultSet(results, results.getMetaData().getColumnCount());
		
		for(int i = 0; i < listedResults.size(); i++) {
			for(int j = 0; j < listedResults.get(0).length; j++) {
				System.out.print(listedResults.get(i)[j]+"    ");
			}
			System.out.println("\n");
		}
		
	}
	
	// inserts data into table
	public void insertData(ArrayList<String> data_types, String[][] data_values) {
		
		try{	
			c.setAutoCommit(false);	
			// create insert statement
			String insertDataScript = "INSERT INTO "+tableName+" VALUES (";
			for(int q = 0; q < data_types.size()-1; q++) {
				insertDataScript += "?, ";
			}
			insertDataScript += "?)";
			
			PreparedStatement stmt = c.prepareStatement(insertDataScript);
			for (int i = 0; i < data_values.length; i++) {
				
				for(int j = 1; j < data_types.size()+1; j++) {
					
					if(data_types.get(j-1).toLowerCase().equals("double")) {
						//System.out.print("made it to DOUBLE and value is "+data_values[i][j-1]+"\n");
						stmt.setDouble(j, Double.valueOf(data_values[i][j-1]));
					}
					else if(data_types.get(j-1).toLowerCase().equals("int")) {
						//System.out.print("made it to INT and value is "+data_values[i][j-1]+"\n");
						stmt.setInt(j, Integer.valueOf(data_values[i][j-1]));
					}
					else if(data_types.get(j-1).toLowerCase().equals("string")) {
						//System.out.print("made it to STRING and value is "+data_values[i][j-1]+"\n");
						stmt.setString(j, data_values[i][j-1]);
					}
					else {
						System.out.println("Error");
					}
				}
				stmt.addBatch();
		    }
			stmt.executeBatch();
			c.commit();
			c.setAutoCommit(true);
	        stmt.close();
		} 
		catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
	
	public void close() throws SQLException {
		c.close();
	}
	
}
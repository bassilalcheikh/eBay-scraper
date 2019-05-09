package com.project.watchdatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface SQLExecutor {

	public ArrayList<String> getTableColumns() throws SQLException ;
	
	public ArrayList<String[]> transformResultSet(ResultSet prs) throws SQLException;
	
	public void plainExecute(String code) throws SQLException;
	
	public ResultSet selectData(String[] given_columns) throws SQLException;
	
	public void viewTableData(String tableName) throws SQLException;
	
	public void insertData(ArrayList<String> data_types, String[][] data_values);
	
	public void close() throws SQLException;

}

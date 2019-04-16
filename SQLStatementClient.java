import java.util.*;

public class SQLStatementClient {

	public static void main(String[] args) {
		String table_name = "ebaysearchresults";
		GenSQLStatements SQLStatement = new GenSQLStatements(table_name);	 
		
		String[] types = new String[] {"String", "String", "String", "String", "String", "String", "double", "double", "String", "int", "String"};
		String[] values = new String[] {"233190249270", "https://www.ebay.com/itm/Rolex-GMT-Master-16750-Vintage-Pepsi-Mens-Automatic-Watch-1983-1984/233190249270?hash=item364b38d336:g:4PoAAOSwj4dbmsNb", "Rolex GMT-Master 16750 Vintage Pepsi Mens Automatic Watch 1983-1984", "Rolex", "16750", "Apr-12 16:44", "8250.00", "0.00", "auction", "1", "melvin106"};
		
		ArrayList<String> data_values = new ArrayList<String>(Arrays.asList(values));
		ArrayList<String> data_types = new ArrayList<String>(Arrays.asList(types));			
		
		System.out.println(SQLStatement.getInsertStatement(data_values, data_types));
		
	}

}

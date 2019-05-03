import java.util.*;

public class TestDBSearchQuery {

	public static void main(String[] args) {

		SearchRoutine sR = new SearchRoutine();
		
		ArrayList<String[]> results = sR.executeDBQuery();
		
		for(int row = 0; row < results.size(); row++) {
			// loops through each row
			for(int col = 0; col < results.get(col).length ; col++) {
				// loops through every column
				System.out.print(results.get(row)[col]+" ");
			}
			System.out.println("\n");
		}
		
		

	}

}

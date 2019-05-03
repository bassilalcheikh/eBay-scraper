
public class TestClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EbayScraper es = new EbayScraper("Rolex", "16760");
		//es.setLower_price_bound(1001);
		
		String[][] Rolexes = es.getScrapedResults();
		
		for(int i = 0; i < Rolexes.length; i++) {
			
			for(int j = 0; j < Rolexes[0].length; j++) {
				System.out.println(Rolexes[i][j]);
			}
			
		}
		
	}

}

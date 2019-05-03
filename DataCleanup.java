import java.io.UnsupportedEncodingException;

/*
 * This class holds methods that will clean up the data.
 */
public class DataCleanup {
	
	public DataCleanup() {
	}
	
	public static String extractFirstAmount(String raw_price_string) {
	    int first_amount_index = raw_price_string.indexOf('.')+2;
	    return  raw_price_string.substring(0, first_amount_index+1);
	}
	
	public static double extractDouble(String stringAmount) {
		// this method will parse a string for digits that will eventually provide a double; 
		// in our case, this is used for prices /dollar amounts
		String newNumberString = "";
		byte[] bytes;
		
		try {
			bytes = stringAmount.getBytes("US-ASCII");
			for(int b = 0; b < bytes.length; b++) {
				if(bytes[b]==46 || (bytes[b] <= 57 && bytes[b] >= 48)){
					newNumberString += Character.toString((char) bytes[b]);
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// the following code assumes the double value is "0.00" if no numbers are presented
		if(newNumberString.length() > 0) {
			return Double.valueOf(newNumberString); // PROBLEM HERE
		}
		else {
			return 0.00; // i.e., "free shipping" gets reported as 0.00 
		}
	}
	
	public static int extractInteger(String stringAmount) {
		// this method will parse a string for digits that will eventually provide an integer; 
		// in our case, this is used for bids
		String newNumberString = "";
		byte[] bytes;
		
		try {
			bytes = stringAmount.getBytes("US-ASCII");
			for(int b = 0; b < bytes.length; b++) {
				if(bytes[b] <= 57 && bytes[b] >= 48){
					newNumberString += Character.toString((char) bytes[b]);
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// the following code assumes the double value is "0" if no numbers are presented
		if(newNumberString.length() > 0) {
			return Integer.valueOf(newNumberString); 
		}
		else {
			return 0; // i.e., "bids" gets reported as 0
		} 
	}
	
	public static String formatDateTime(String date_input, String given_year) {
		/* 	TEXT as ISO8601 strings ("YYYY-MM-DD HH:MM:SS.SSS").
		 	Example input: 
		 	"Jan-25 18:21" 
		 	should yield: "2019-01-25 18:21:00.000"
		*/
		String yearString;
		String monthString; 
		String dayString = date_input.substring(4,6);
		String hourMinute = date_input.substring(7,12);
		
		if(given_year.length() != 4){
			System.out.println("Year entry error; default value added");
			yearString = "2010"; // revisit this issue
		}
		else {
			yearString = given_year;
		}
		
		String input_month = date_input.substring(0,3);
        switch (input_month) { 
        case "Jan": 
            monthString = "01"; 
            break; 
        case "Feb": 
            monthString = "02"; 
            break; 
        case "Mar": 
            monthString = "03"; 
            break; 
        case "Apr": 
            monthString = "04"; 
            break; 
        case "May": 
            monthString = "05"; 
            break; 
        case "Jun": 
            monthString = "06"; 
            break; 
        case "Jul": 
            monthString = "07"; 
            break;
        case "Aug": 
            monthString = "08"; 
            break; 
        case "Sep": 
            monthString = "09"; 
            break; 
        case "Oct": 
            monthString = "10"; 
            break; 
        case "Nov": 
            monthString = "11"; 
            break;
        case "Dec": 
            monthString = "12"; 
            break; 
        default: 
            monthString = "Invalid month"; 
            break; 
        } 
		
		return yearString+"-"+monthString+"-"+dayString+" "+hourMinute+":00.000";
	}
	
	public static String removeApostrophe(String text) {
		// remove apostrophe issue
		String newString = "";
		byte[] bytes;
		try {
			bytes = text.getBytes("US-ASCII");
			for(int b = 0; b < bytes.length; b++) {				
				newString += Character.toString((char) bytes[b]);
				if(bytes[b]==39){
					newString += "'";
				}
			}
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		}
		return newString;
	}
	
}

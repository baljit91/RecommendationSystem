package RecommendationSystem;
/**
 * A class that implements Filter interface and returns all movies that released after a given year

 */

public class YearAfterFilter implements Filter {
	private int myYear;
	
	public YearAfterFilter(int year) {
		myYear = year;
	}
	
	
	public boolean satisfies(String id) {
		return MovieDatabase.getYear(id) >= myYear;
	}

}

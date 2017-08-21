package RecommendationSystem;
/**
 * A class that implements Filter interface and returns all movies that lie within the specified duration
 */
public class MinutesFilter implements Filter {
	int minTime,maxTime;
	
	public MinutesFilter(int minTime, int maxTime){
		this.minTime = minTime;
		this.maxTime = maxTime;
	}
	
	public boolean satisfies(String id) {
		// TODO Auto-generated method stub
		return MovieDatabase.getMinutes(id) >= minTime && MovieDatabase.getMinutes(id) <= maxTime;
	}

}

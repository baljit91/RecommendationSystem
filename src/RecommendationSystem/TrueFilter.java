package RecommendationSystem;

/**
 * A class that implements Filter interface and returns all movies in the database

 */
public class TrueFilter implements Filter {
	public boolean satisfies(String id) {
		return true;
	}

}

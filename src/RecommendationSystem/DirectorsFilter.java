package RecommendationSystem;
/**
 * A class that implements Filter interface and returns all movies that were directed by
 *  any one of the directors specified in the list
* */
public class DirectorsFilter implements Filter {
	private String[] directors;
	
	public DirectorsFilter(String directors){
		this.directors = directors.split(",");
	}
	
	public boolean satisfies(String id) {
		for(String director : directors){
			if(MovieDatabase.getDirector(id).contains(director)){
				return true;
			}
		}
		return false;
	}

}

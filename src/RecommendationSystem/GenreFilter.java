package RecommendationSystem;
/**
 * A class that implements Filter interface and returns all movies of the genres mentioned specified in the list

 */
public class GenreFilter implements Filter{
	private String genre;
	
	public GenreFilter(String genre){
		this.genre = genre;
	}
	
	public boolean satisfies(String id) {
		// TODO Auto-generated method stub

		return MovieDatabase.getGenres(id).contains(genre);
	}

}

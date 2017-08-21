package RecommendationSystem;


//Used in initial design(Not in use now)

import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    private FirstRatings fr;
    
    public SecondRatings() {
        // default constructor
        //this("ratedmoviesfull.csv", "ratings.csv");
    }
    public SecondRatings(String moviefile,String ratingsfile) {
    	fr = new FirstRatings();
    	myMovies = fr.loadMovies(moviefile);
    	myRaters = fr.loadRaters(ratingsfile);
    }
    
    public int getMovieSize(){
    	return myMovies.size();
    }
    
    public int getRaterSize(){
    	return myRaters.size();
    }
    
    //Returns average rating of the specified movie  if number of raters of
    // movie not less than minimalRaters
    public double getAverageByID(String id, int minimalRaters){
    	double average = 0.0;
    	double sum = 0;
    	int countRaters = 0;
    	for(Rater r: myRaters) {
    		if(r.hasRating(id)) {
    			countRaters++;  
    			sum += r.getRating(id);
    		}
    	}
    	if(countRaters >= minimalRaters)
    		average = sum / countRaters;
    	return average;
	}
    
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters){
    	ArrayList<Rating> result = new ArrayList<Rating>();
    	
    	for(Movie movie : myMovies){
    		double movieAvgRating = getAverageByID(movie.getID(),minimalRaters);
    		if(movieAvgRating > 0.0){
    			result.add(new Rating(movie.getID(),movieAvgRating));
    		}
    	}
    	
    	return result;
    }
    
  //Returns Movie Title for the specified movie id
    public String getTitle(String id){
    	for(Movie movie : myMovies){
    		String currMovieId = movie.getID();
    		if(currMovieId.equals(id)){
    			return movie.getTitle();
    		}
    	}
    	return "Movie with the specified id does not exists!";
    }
    
  //Returns movie id for the specified movie title
    public String getID(String title){
    	for(Movie movie : myMovies){
    		String currMovieTitle = movie.getTitle();
    		if(currMovieTitle.equals(title)){
    			return movie.getID();
    		}
    	}
    	return "NO SUCH TITLE!";
    }
    
    
    
    
}
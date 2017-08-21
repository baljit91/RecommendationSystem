package RecommendationSystem;
//Used in initial design(Not in use now)

import java.util.ArrayList;


public class ThirdRatings {
    private ArrayList<Rater> myRaters;
    private FirstRatings fr;
    
    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }
    public ThirdRatings(String ratingsfile) {
    	fr = new FirstRatings();
    	myRaters = fr.loadRaters(ratingsfile);
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
    	ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
    	
    	for(String movie : movies){
    		double movieAvgRating = getAverageByID(movie,minimalRaters);
    		if(movieAvgRating > 0.0){
    			result.add(new Rating(movie,movieAvgRating));
    		}
    	}
    	
    	return result;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters,Filter filterCriteria){
		ArrayList<Rating> result = new ArrayList<Rating>();
		MovieDatabase.initialize("ratedmovies_short.csv");
		ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter()); 
		
		for(String movie : movies){
			if(filterCriteria.satisfies(movie) && getAverageByID(movie, minimalRaters) > 0.0){
				result.add(new Rating(movie,getAverageByID(movie, minimalRaters)));
			}	
		}
		return result;
	}
    
    
}

package RecommendationSystem;
/**
 * Business logic to provide appropriate recommendations to a given user
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


public class FourthRatings {
	    private FirstRatings fr;
	    
	    public FourthRatings() {
	        // default constructor
	        this("ratings.csv");
	    }
	    public FourthRatings(String ratingsfile) {
	    	fr = new FirstRatings();
	    }

	    
	    public int getRaterSize(){
	    	return RaterDatabase.size();
	    }
	    
	    //Returns average rating of the specified movie  if number of raters of
	    // movie not less than minimalRaters
	    public double getAverageByID(String id, int minimalRaters){
	    	double average = 0.0;
	    	double sum = 0;
	    	int countRaters = 0;
	    	for(Rater r: RaterDatabase.getRaters()) {
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
	    
	  /**
	   * @param me
	   * @param r
	   * @return a similarity factor between two specified users/raters
	   */
	    private double dotProduct(Rater me, Rater r){
	    	double dotProduct = 0;
	    	ArrayList<String> myMovies = me.getItemsRated();
	    	for (String id: myMovies)
			{
				if (r.hasRating(id))
				{
					double meRating = me.getRating(id);
					double rRating = r.getRating(id);
					meRating -= 5;
					rRating -= 5;
					dotProduct += meRating * rRating;
					
				}
			}
			return dotProduct;
	    }
	    /**Returns a list consisting of all other raters and their degree of similarity in movie tastes with the specified user
	    	*List is returned in decreasing order of similarity. Users more similar to the concerned user will end up on top of the returned list
    		*raterIdToBeMatchedWith : User/rater in consideration
	    **/
	    //this method computes a similarity rating for each rater in the RaterDatabase
	    public ArrayList<Rating> getSimilarities(String id){
	    	ArrayList<Rating> list = new ArrayList<Rating>();
	    	Rater me = RaterDatabase.getRater(id);
	    	
	    	for(Rater r : RaterDatabase.getRaters()){
	    		
	    		if(!r.equals(me)){
	    			double similarityIndex = dotProduct(me,r);
	    			if(similarityIndex > 0)
	    				list.add(new Rating(r.getID(),similarityIndex));
	    		}
	    	}
	    	
	    	Collections.sort(list, Collections.reverseOrder());
	    	return list;
	    }
	    
	    
	    /**Returns a list of all recommendations for a specified user
	     * @param
	     *raterId : user for whom recommendations have to be provided
	     *numSimilarRaters : This is the minimum number of raters that have to be considered who have the same taste in movies as the specified user
	     *minimalRaters : only movies that have atleast minimalRaters number of raters should be considered for recommendations
	    **/
	    public ArrayList<Rating> getSimilarRatings(String raterId, int numSimilarRaters, int minimalRaters){
	    	ArrayList<Rating> result = new ArrayList<Rating>();
	    	ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
	    	ArrayList<Rating> similarRatings = getSimilarities(raterId);
	    	
	    	for(String movieId : movies){
	    		double weightedAverage = 0;
	        	double sum = 0;
	        	int countRaters = 0;
	        	
	        	for (int i = 0; i < numSimilarRaters; i++) {
	        		Rating r = similarRatings.get(i);
	        		String currRaterId = r.getItem();
	        		double weight = r.getValue();
	        		
	        		//get all the movie rated by the rater
	        		Rater myRater = RaterDatabase.getRater(currRaterId);
		    		if(myRater.hasRating(movieId)) {
		    			countRaters++;
		    			sum += weight * myRater.getRating(movieId);
		    		}
	        	}
	        	
	        	if(countRaters >= minimalRaters){
	        		weightedAverage = sum / countRaters;
	        		result.add(new Rating(movieId,weightedAverage));
	        	}
	    	}
	    	
	    	Collections.sort(result, Collections.reverseOrder());
	    	return result;
	    	
	    }   
	    
	    
	    /**Returns a list of all recommendations for a specified user based on the selected filter
	     * @param
	     *raterId : user for whom recommendations have to be provided
	     *numSimilarRaters : This is the minimum number of raters that have to be considered who have the same taste in movies as the specified user
	     *minimalRaters : only movies that have atleast minimalRaters number of raters should be considered for recommendations
	     *criteriaFilter : Filters out movies based on the criteria specified. Reduces the sample space to be considered
	    **/
	    public ArrayList<Rating> getSimilarRatingsByFilter(String raterId, int numSimilarRaters, int minimalRaters, Filter filterCriteria){
	    	ArrayList<Rating> result = new ArrayList<Rating>();
	    	ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
	    	ArrayList<Rating> similarRatings = getSimilarities(raterId);
	    	
	    	for(String movieId : movies){
	    		double weightedAverage = 0;
	        	double sum = 0;
	        	int countRaters = 0;
	        	
	        	for (int i = 0; i < numSimilarRaters; i++) {
	        		Rating r = similarRatings.get(i);
	        		String currRaterId = r.getItem();
	        		double weight = r.getValue();
	        		
	        		//get all the movie rated by the rater
	        		Rater myRater = RaterDatabase.getRater(currRaterId);
		    		if(myRater.hasRating(movieId)) {
		    			countRaters++;
		    			sum += weight * myRater.getRating(movieId);
		    		}
	        	}
	        	
	        	if(countRaters >= minimalRaters){
	        		weightedAverage = sum / countRaters;
	        		result.add(new Rating(movieId,weightedAverage));
	        	}
	    	}
	    	
	    	Collections.sort(result, Collections.reverseOrder());
	    	return result;
	    	
	    }
	    
	    
}

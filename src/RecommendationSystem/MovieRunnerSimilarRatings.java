package RecommendationSystem;

/**
 * This files provides an entry point for the recommendation system
 * Contains the main function and other functions to provide movie recommendations to a specified user 
 * depending on his/her movie ratings 
 */


import java.util.ArrayList;
import java.util.Collections;


public class MovieRunnerSimilarRatings {
	public void printAverageRatings (){
		FourthRatings fr = new FourthRatings("data/ratings_short.csv");
		System.out.println("-----------------------------------");
		//System.out.println("Total Number of Movies :" + sr.getMovieSize());
		RaterDatabase.initialize("ratings.csv");
		System.out.println("Total Number of Raters :" + RaterDatabase.size());
		
		MovieDatabase.initialize("ratedmovies_short.csv");
		System.out.println("Total Number of Movies :" + MovieDatabase.size());
		
		int minimalRaters = 1;
		ArrayList<Rating> ratingList = fr.getAverageRatings(minimalRaters);
			
		Collections.sort(ratingList);
		
		for(Rating rating : ratingList){
			//System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
		}
		
	}
	
	
	public void printAverageRatingsByYearAfterAndGenre(){
		FourthRatings fr = new FourthRatings("data/ratings_short.csv");
		MovieDatabase.initialize("ratedmoviesfull.csv");
		//String directors = "Charles Chaplin,Michael Mann,Spike Jonze";
		int minimalRaters = 1;
		int year = 1980;
		String genre = "Romance";
		
		AllFilters allfilter = new AllFilters();
		Filter yearFilter = new YearAfterFilter(year);
		Filter genreFilter = new GenreFilter(genre);
		
		allfilter.addFilter(yearFilter);
		allfilter.addFilter(genreFilter);
		
		
		ArrayList<Rating> filteredList = fr.getAverageRatingsByFilter(minimalRaters,allfilter);
		Collections.sort(filteredList);
		System.out.println(filteredList.size() + " movies matched");
		for(Rating movie : filteredList){
			System.out.println(movie.getValue() + " " + MovieDatabase.getYear(movie.getItem()) + " " + MovieDatabase.getTitle(movie.getItem()));
			System.out.println("    " + MovieDatabase.getGenres(movie.getItem()));
		}
	}
	
	
	public void printSimilarRatings(){
		RaterDatabase.initialize("ratings.csv");
		System.out.println("read data for " + RaterDatabase.size() + " raters");	
		MovieDatabase.initialize("ratedmoviesfull.csv");
		System.out.println("read data for " + MovieDatabase.size() + " movies");
		FourthRatings fr = new FourthRatings();
		
		ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
		ArrayList<Rater> raters = RaterDatabase.getRaters();
		
		ArrayList<Rating> ratings = new ArrayList<Rating>();
		
		int numSimilarRaters = 20;
		int minimalRaters = 5;
		String raterId = "65";
//		for(Rater CurrRater : raters){
//			result = fr.getSimilarRatings(CurrRater.getID(),numSimilarRaters, minimalRaters);
//		}
		
		ratings = fr.getSimilarRatings(raterId,numSimilarRaters, minimalRaters);
		if (ratings.size() == 0 || ratings.size() == 1)
	    	System.out.println(ratings.size() + " movie matched");
	    else
	    	System.out.println(ratings.size() + " movies matched");
	    for(int i=0; i< ratings.size(); i++) {
	    	if (i<15)
	    		System.out.printf("%d %.2f %s\n", i, ratings.get(i).getValue(), MovieDatabase.getTitle(ratings.get(i).getItem()));
		}
	}
	
	
	
	public void printSimilarRatingsByGenre(){
		RaterDatabase.initialize("ratings.csv");
		System.out.println("read data for " + RaterDatabase.size() + " raters");	
		MovieDatabase.initialize("ratedmoviesfull.csv");
		System.out.println("read data for " + MovieDatabase.size() + " movies");
		FourthRatings fr = new FourthRatings();
		
		ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
		ArrayList<Rater> raters = RaterDatabase.getRaters();
		
		ArrayList<Rating> ratings = new ArrayList<Rating>();
		
		int numSimilarRaters = 20;
		int minimalRaters = 5;
		String raterId = "964";
		String genre= "Mystery";
		GenreFilter genreFilter = new GenreFilter(genre);
//		for(Rater CurrRater : raters){
//			result = fr.getSimilarRatings(CurrRater.getID(),numSimilarRaters, minimalRaters);
//		}
		
		ratings = fr.getSimilarRatingsByFilter(raterId,numSimilarRaters, minimalRaters,genreFilter);
		if (ratings.size() == 0 || ratings.size() == 1)
	    	System.out.println(ratings.size() + " movie matched");
	    else
	    	System.out.println(ratings.size() + " movies matched");
	    for(int i=0; i< ratings.size(); i++) {
	    	if (i<15)
	    		System.out.printf("%d %.2f %s\n", i, ratings.get(i).getValue(), MovieDatabase.getTitle(ratings.get(i).getItem()));
		}
	}
	
	public void printSimilarRatingsByDirector(){
		RaterDatabase.initialize("ratings.csv");
		System.out.println("read data for " + RaterDatabase.size() + " raters");	
		MovieDatabase.initialize("ratedmoviesfull.csv");
		System.out.println("read data for " + MovieDatabase.size() + " movies");
		FourthRatings fr = new FourthRatings();
		
		ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
		ArrayList<Rater> raters = RaterDatabase.getRaters();
		
		ArrayList<Rating> ratings = new ArrayList<Rating>();
		
		int numSimilarRaters = 10;
		int minimalRaters = 2;
		String raterId = "120";
		String directors = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";
		DirectorsFilter directorsFilter = new DirectorsFilter(directors);
//		for(Rater CurrRater : raters){
//			result = fr.getSimilarRatings(CurrRater.getID(),numSimilarRaters, minimalRaters);
//		}
		
		ratings = fr.getSimilarRatingsByFilter(raterId,numSimilarRaters, minimalRaters,directorsFilter);
		if (ratings.size() == 0 || ratings.size() == 1)
	    	System.out.println(ratings.size() + " movie matched");
	    else
	    	System.out.println(ratings.size() + " movies matched");
	    for(int i=0; i< ratings.size(); i++) {
	    	if (i<15)
	    		System.out.printf("%d %.2f %s\n", i, ratings.get(i).getValue(), MovieDatabase.getTitle(ratings.get(i).getItem()));
		}
	}
	
	
	public void printSimilarRatingsByGenreAndMinutes(){
		RaterDatabase.initialize("ratings.csv");
		System.out.println("read data for " + RaterDatabase.size() + " raters");	
		MovieDatabase.initialize("ratedmoviesfull.csv");
		System.out.println("read data for " + MovieDatabase.size() + " movies");
		FourthRatings fr = new FourthRatings();
		
		ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
		ArrayList<Rater> raters = RaterDatabase.getRaters();
		
		ArrayList<Rating> ratings = new ArrayList<Rating>();
		
		int numSimilarRaters = 10;
		int minimalRaters = 3;
		String raterId = "168";
		String genre= "Drama";
		int minMinutes = 80;
		int maxMinutes = 160;
		GenreFilter genreFilter = new GenreFilter(genre);
		MinutesFilter minutesFilter = new MinutesFilter(minMinutes, maxMinutes);
		AllFilters filters = new AllFilters();
		filters.addFilter(genreFilter);
		filters.addFilter(minutesFilter);

		ratings = fr.getSimilarRatingsByFilter(raterId,numSimilarRaters, minimalRaters,filters);
		if (ratings.size() == 0 || ratings.size() == 1)
	    	System.out.println(ratings.size() + " movie matched");
	    else
	    	System.out.println(ratings.size() + " movies matched");
	    for(int i=0; i< ratings.size(); i++) {
	    	if (i<15)
	    		System.out.printf("%d %.2f %s\n", i, ratings.get(i).getValue(), MovieDatabase.getTitle(ratings.get(i).getItem()));
		}
	}
	
	
	public void printSimilarRatingsByYearAfterAndMinutes(){
		RaterDatabase.initialize("ratings.csv");
		System.out.println("read data for " + RaterDatabase.size() + " raters");	
		MovieDatabase.initialize("ratedmoviesfull.csv");
		System.out.println("read data for " + MovieDatabase.size() + " movies");
		FourthRatings fr = new FourthRatings();
		
		ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
		ArrayList<Rater> raters = RaterDatabase.getRaters();
		
		ArrayList<Rating> ratings = new ArrayList<Rating>();
		
		int numSimilarRaters = 10;
		int minimalRaters = 5;
		String raterId = "314";
		int year = 1975;
		int minMinutes = 70;
		int maxMinutes = 200;
		YearAfterFilter yearAfterFilter = new YearAfterFilter(year);
		MinutesFilter minutesFilter = new MinutesFilter(minMinutes, maxMinutes);
		AllFilters filters = new AllFilters();
		filters.addFilter(yearAfterFilter);
		filters.addFilter(minutesFilter);

		ratings = fr.getSimilarRatingsByFilter(raterId,numSimilarRaters, minimalRaters,filters);
		if (ratings.size() == 0 || ratings.size() == 1)
	    	System.out.println(ratings.size() + " movie matched");
	    else
	    	System.out.println(ratings.size() + " movies matched");
	    for(int i=0; i< ratings.size(); i++) {
	    	if (i<15)
	    		System.out.printf("%d %.2f %s\n", i, ratings.get(i).getValue(), MovieDatabase.getTitle(ratings.get(i).getItem()));
		}
	}
	
	public static void main(String[] args){
		MovieRunnerSimilarRatings m = new MovieRunnerSimilarRatings();
		//m.printAverageRatings();
		m.printSimilarRatings();
		//m.printSimilarRatingsByGenre();
		//m.printSimilarRatingsByDirector();
		//m.printSimilarRatingsByGenreAndMinutes();
		//m.printSimilarRatingsByYearAfterAndMinutes();
	}
}

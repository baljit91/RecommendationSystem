package RecommendationSystem;
//For Unit Testing Purpose

import java.util.ArrayList;
import java.util.Collections;


public class MovieRunnerWithFilters {
	public void printAverageRatings (){
		ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
		System.out.println("-----------------------------------");
		//System.out.println("Total Number of Movies :" + sr.getMovieSize());
		System.out.println("Total Number of Raters :" + tr.getRaterSize());
		
		MovieDatabase.initialize("ratedmovies_short.csv");
		System.out.println("Total Number of Movies :" + MovieDatabase.size());
		
		int minimalRaters = 1;
		ArrayList<Rating> ratingList = tr.getAverageRatings(minimalRaters);
			
		Collections.sort(ratingList);
		
		for(Rating rating : ratingList){
			//System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
		}
		
	}
	
	
	
	
	public void printAverageRatingsByYear(){
		ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
		MovieDatabase.initialize("ratedmoviesfull.csv");
		int year = 2000;
		int minimalRaters = 1;
		Filter filter = new YearAfterFilter(year);
		
		ArrayList<Rating> filteredList = tr.getAverageRatingsByFilter(minimalRaters,filter);
		Collections.sort(filteredList);
		System.out.println("found" + filteredList.size() + "movies");
		for(Rating movie : filteredList){
			System.out.println(movie.getValue() + " " + MovieDatabase.getYear(movie.getItem()) 
					+ " " + MovieDatabase.getTitle(movie.getItem()));
		}
		
	}
	
	public void printAverageRatingsByGenre(){
		ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
		MovieDatabase.initialize("ratedmoviesfull.csv");
		String genre = "Crime";
		int minimalRaters = 1;
		Filter filter = new GenreFilter(genre);
		
		ArrayList<Rating> filteredList = tr.getAverageRatingsByFilter(minimalRaters,filter);
		Collections.sort(filteredList);
		System.out.println("found" + filteredList.size() + "movies");
		for(Rating movie : filteredList){
			System.out.println(movie.getValue() + " " + MovieDatabase.getGenres(movie.getItem()) 
					+ " " + MovieDatabase.getTitle(movie.getItem()));
		}
	}
	
	
	public void printAverageRatingsByMinutes(){
		ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
		MovieDatabase.initialize("ratedmoviesfull.csv");
		int minimumMinutes = 110;
		int maximumMinutes = 170;
		int minimalRaters = 1;
		Filter filter = new MinutesFilter(minimumMinutes,maximumMinutes);
		
		ArrayList<Rating> filteredList = tr.getAverageRatingsByFilter(minimalRaters,filter);
		Collections.sort(filteredList);
		System.out.println("found" + filteredList.size() + "movies");
		for(Rating movie : filteredList){
			System.out.println(movie.getValue() + " Time :" + MovieDatabase.getMinutes(movie.getItem()) 
					+ " " + MovieDatabase.getTitle(movie.getItem()));
		}
	}
	
	
	public void printAverageRatingsByDirectors(){
		ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
		MovieDatabase.initialize("ratedmoviesfull.csv");
		String directors = "Charles Chaplin,Michael Mann,Spike Jonze";
		int minimalRaters = 1;
		Filter filter = new DirectorsFilter(directors);
		
		ArrayList<Rating> filteredList = tr.getAverageRatingsByFilter(minimalRaters,filter);
		Collections.sort(filteredList);
		System.out.println("found" + filteredList.size() + "movies");
		for(Rating movie : filteredList){
			System.out.println(movie.getValue() + " " + MovieDatabase.getTitle(movie.getItem()));
			System.out.println(MovieDatabase.getDirector(movie.getItem()));
		}
	}
	
	
	public void printAverageRatingsByYearAfterAndGenre(){
		ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
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
		
		
		ArrayList<Rating> filteredList = tr.getAverageRatingsByFilter(minimalRaters,allfilter);
		Collections.sort(filteredList);
		System.out.println(filteredList.size() + " movies matched");
		for(Rating movie : filteredList){
			System.out.println(movie.getValue() + " " + MovieDatabase.getYear(movie.getItem()) + " " + MovieDatabase.getTitle(movie.getItem()));
			System.out.println("    " + MovieDatabase.getGenres(movie.getItem()));
		}
	}
	
	public void printAverageRatingsByDirectorsAndMinutes (){
		ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
		MovieDatabase.initialize("ratedmoviesfull.csv");
		String directors = "Spike Jonze,Michael Mann,Charles Chaplin,Francis Ford Coppola";
		int minimumMinutes = 30;
		int maximumMinutes = 170;
		int minimalRaters = 1;
		
		
		AllFilters allfilter = new AllFilters();
		Filter minutesFilter = new MinutesFilter(minimumMinutes,maximumMinutes);
		Filter directorsFilter = new DirectorsFilter(directors);
		
		allfilter.addFilter(minutesFilter);
		allfilter.addFilter(directorsFilter);
		
		
		ArrayList<Rating> filteredList = tr.getAverageRatingsByFilter(minimalRaters,allfilter);
		Collections.sort(filteredList);
		System.out.println(filteredList.size() + " movies matched");
		for(Rating movie : filteredList){
			System.out.println(movie.getValue() + " " + MovieDatabase.getMinutes(movie.getItem()) + " " + MovieDatabase.getTitle(movie.getItem()));
			System.out.println("    " + MovieDatabase.getDirector(movie.getItem()));
		}
	}
	
	
	public static void main(String[] args){
		MovieRunnerWithFilters m = new MovieRunnerWithFilters();
		//m.printAverageRatings();
		//m.getAverageRatingOneMovie();
		//m.printAverageRatingsByYear();
		//m.printAverageRatingsByGenre();
		//m.printAverageRatingsByMinutes();
		//m.printAverageRatingsByDirectors();
		//m.printAverageRatingsByYearAfterAndGenre();
		//m.printAverageRatingsByDirectorsAndMinutes();
	}
}

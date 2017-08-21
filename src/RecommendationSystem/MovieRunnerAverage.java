package RecommendationSystem;
//For Unit Testing Purpose

import java.util.ArrayList;
import java.util.Collections;


public class MovieRunnerAverage {
	
	public void printAverageRatings (){
		SecondRatings sr = new SecondRatings("data/ratedmovies_short.csv", "data/ratings_short.csv");
		System.out.println("-----------------------------------");
		System.out.println("Total Number of Movies :" + sr.getMovieSize());
		System.out.println("Total Number of Raters :" + sr.getRaterSize());
		
		ArrayList<Rating> ratingList = sr.getAverageRatings(3);
		
		Collections.sort(ratingList);
		
		for(Rating rating : ratingList){
			System.out.println(rating.getValue() + " " + sr.getTitle(rating.getItem()));
		}
		
	}
	
	public void getAverageRatingOneMovie(){
		SecondRatings sr = new SecondRatings("data/ratedmoviesfull.csv", "data/ratings.csv");
		String inputMovieTitle = "The Godfather";
		
		String movieId = sr.getID(inputMovieTitle);
		double movieAvgRating = sr.getAverageByID(movieId,3);
		
		System.out.println("Average Rating for the movie ["+inputMovieTitle+"] is :"+movieAvgRating);
		
	}
	
	
	
	public static void main(String[] args){
		MovieRunnerAverage m = new MovieRunnerAverage();
		//m.printAverageRatings();
		//m.getAverageRatingOneMovie();
	}
	
}

package RecommendationSystem;
/**
 * Does analytics on movies based on number of ratings and raters who provided ratings
 */

import edu.duke.*;

import java.util.*;

import org.apache.commons.csv.*;

public class FirstRatings {
	public ArrayList<Movie> loadMovies(String filename){
		ArrayList<Movie> movies = new ArrayList<Movie>();
		
		FileResource fr = new FileResource(filename);
		for(CSVRecord rec : fr.getCSVParser()){
			
			String id = rec.get("id");
		    String title = rec.get("title");
		    String year = rec.get("year");
		    String genres = rec.get("genre");
		    String director = rec.get("director");
		    String country = rec.get("country");
		    String poster = rec.get("poster");
		    int minutes = Integer.parseInt(rec.get("minutes"));			
			Movie movie = new Movie(id, title, year, genres, director, country, poster, minutes);
			movies.add(movie);
		}
		
		return movies;
	}
	
	//To test and perform analytics on movies
	public void testLoadMovies(){
		String filename = "data/ratedmoviesfull.csv";
		//String filename = "data/ratedmovies_short.csv";
		
		ArrayList<Movie> movieList = loadMovies(filename);
		System.out.println("--------------------------------------------------------------");
		System.out.println(filename.substring(5) + " has " + movieList.size() + " movies");
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		String genre = "Comedy";
		int time = 150;
		int comedyGenreCount = 0;
		int moviesGT150 = 0;
		
		
		for(Movie movie : movieList){
			if(movie.getGenres().contains(genre)){
				comedyGenreCount++;
			}
			if(movie.getMinutes() > time){
				moviesGT150++;
			}
			if(!map.containsKey(movie.getDirector())){
				map.put(movie.getDirector(), 1);
			}
			else{
				map.put(movie.getDirector(),map.get(movie.getDirector()) + 1);
			}
		}
		
		System.out.println(comedyGenreCount + " movies include " + genre);
		System.out.println(moviesGT150 + " movies are greater than " + time + " minutes");
		
		int maxValue = Collections.max(map.values());
		for(String key : map.keySet()){
			if(map.get(key).equals(maxValue)){
				System.out.println(map.get(key) + " directed the maximum number of movies, which is " + maxValue);
				break;
			}
		}
		System.out.println("---------------------------------------------------------------");
	}
	
	//To load Raters Data from csv files
	public ArrayList<Rater> loadRaters(String filename){
		ArrayList<Rater> ratersList = new ArrayList<Rater>();
		
		FileResource fr = new FileResource(filename);
		int index = 0;
		
		for(CSVRecord rec : fr.getCSVParser()){
			String id = rec.get("rater_id");
			String item = rec.get("movie_id");
			double rating = Double.parseDouble(rec.get("rating"));
			
			if(index == 0){
				Rater rater = new EfficientRater(id);
				rater.addRating(item, rating);
				ratersList.add(index, rater);
				index++;
			}
			
			else if(ratersList.get(index - 1).getID().equals(id)){
				ratersList.get(index -1).addRating(item, rating);
			}
			else{
				Rater rater = new EfficientRater(id);
				rater.addRating(item, rating);
				ratersList.add(index, rater);
				index++;
			}	
		}
		
		return ratersList;
	}
	
	//Returns number of unique movies rated by a given rater (identified by his/her unique rater id)
	private int moviesRated(ArrayList<Rater> raterList, String rater_ID){
		int moviesRateCount = 0;
		for(Rater currRater : raterList){
			if(currRater.getID().equals(rater_ID))
				moviesRateCount = currRater.numRatings();
		}
		return moviesRateCount;
	}
	
	//Returns maximum number of ratings given by any rater
	private int maximumRatingGiven(final ArrayList<Rater> raterList){
		int maxRatingByRater = 0;
		for(Rater r : raterList){
			if(r.numRatings() > maxRatingByRater){
				maxRatingByRater = r.numRatings();
			}
		}
		return maxRatingByRater;
	}
	
	//Returns a list of all raters which provided maximum number of ratings
	public ArrayList<Rater> ratersWhoGaveMaxNumberOfRatings(ArrayList<Rater> raterList,int max){
		ArrayList<Rater> result = new ArrayList<Rater>();
		for(Rater r : raterList){
			if(r.numRatings() == max){
				result.add(r);
			}
		}
		return result;
	}
	
	//Returns number of raters who rated a particular movie
	public int numRatersOfMovie(ArrayList<Rater> raterList,String movieId){
		int numberOfRaters = 0;
		
		for(Rater currentRater : raterList){
			if(currentRater.hasRating(movieId))
				numberOfRaters++;
			}
		
		return numberOfRaters;
	}
	
	//Returns total number of unique movies rated by all the raters
	private int uniqueMoviesRated(ArrayList<Rater> raterList){
		HashSet<String> uniqueMovieList = new HashSet<String>();
		for(Rater currRater : raterList){
			ArrayList<String> moviesRatedByCurrRater = currRater.getItemsRated();
			for(String movie : moviesRatedByCurrRater)
				if(!uniqueMovieList.contains(movie))
					uniqueMovieList.add(movie);
		}
		return uniqueMovieList.size();
	}
		
	//To test and perform analytics on Raters
	public void testLoadRaters(){
		String filename = "data/ratings_short.csv";
		
		//String filename = "data/ratings.csv";
		
		ArrayList<Rater> raterList = loadRaters(filename);
		
		System.out.println("--------------------------------------------------------------");
		System.out.println(filename.substring(5) + " has " + raterList.size() + " raters");
		
		//int raterId = 2;
		int moviesRateCount = moviesRated(raterList, "2");
		System.out.println("Number of movies rated by the specified user :"+moviesRateCount);
		
		
		
		int maxRatingsByRater = maximumRatingGiven(raterList);
		System.out.println("Maximum number of ratings given by any rater  :"+maxRatingsByRater);
		
		ArrayList<Rater> raterListMaxRatings = ratersWhoGaveMaxNumberOfRatings(raterList, maxRatingsByRater);
		System.out.println("Number of raters who provided most number of ratings :"+raterListMaxRatings.size());
		
		int ratingsForAGivenMovie = numRatersOfMovie(raterList, "1798709");
		System.out.println("Number of raters who provided rating for the specified movie :"+ratingsForAGivenMovie);
	
		int uniqueMoviesRated = uniqueMoviesRated(raterList);
		System.out.println("Number of unique movies rated by all the raters :"+uniqueMoviesRated);
	
	}
	
	
	public static void main(String[] args){
		FirstRatings f = new FirstRatings();
		 f.testLoadRaters();;
		//System.out.print(res.size());
	}
}

package RecommendationSystem;
//returns the recommendation results.

import java.util.ArrayList;


public class RecommendationRunner implements Recommender {

	@Override
	public ArrayList<String> getItemsToRate() {
		// TODO Auto-generated method stub
		MovieDatabase.initialize("ratedmoviesfull.csv");
		ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
		ArrayList<String> result = new ArrayList<String>();
		
		int x = 0;
		while(x < 200){
			result.add(movies.get(x));
			x += 20;
		}
		return result;
	}

	@Override
	public void printRecommendationsFor(String webRaterID) {
		// TODO Auto-generated method stub
		FourthRatings fr = new FourthRatings();
		fr.getSimilarities(webRaterID);
	}

}

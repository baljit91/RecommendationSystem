package RecommendationSystem;
/**
 * A class that contains all the information of one rater
 * The information consists of raterID and his/her movies that hr/she rated along with the rating provided

* */

import java.util.ArrayList;
import java.util.HashMap;


public class EfficientRater implements Rater {
	 private String myID;
	    private HashMap<String,Rating> myRatings;

	    public EfficientRater(String id) {
	        myID = id;
	        myRatings = new HashMap<String,Rating>();
	    }

	    /*
	     * A method addRating that has two parameters, a String named item and a 
	     * double named rating.A new Rating is created and added to myRatings.
	     */
	    public void addRating(String item, double rating) {
	        myRatings.put(item,new Rating(item,rating));
	    }
	    
	    
	    public boolean hasRating(String item) {
	        return myRatings.containsKey(item);
	    }

	    public String getID() {
	        return myID;
	    }

	    public double getRating(String item) {
	        return myRatings.get(item).getValue();
	    }

	    public int numRatings() {
	        return myRatings.size();
	    }
	    
	    public ArrayList<String> getItemsRated() {
	        ArrayList<String> list = new ArrayList<String>();
	        for (String key : myRatings.keySet()) {
	            list.add(myRatings.get(key).getItem());
	        }
	        return list;
	    }
}

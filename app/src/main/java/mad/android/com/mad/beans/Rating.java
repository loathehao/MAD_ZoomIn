package mad.android.com.mad.beans;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class Rating extends BmobObject {
    private String movieRatingName;
    private AppUser author;
    private int movieRating;

    private BmobRelation rating;

    public String getMovieRatingName(){
        return movieRatingName;
    }

    public Rating setMovieRatingName(String movieRatingName){
        this.movieRatingName = movieRatingName;
        return this;
    }

    public int getMovieRating(){
        return movieRating;
    }

    public Rating setMovieRating(int movieRating){
        this.movieRating = movieRating;
        return this;
    }

    public AppUser getAuthor(){
        return author;
    }

    public Rating setAuthor(AppUser author){
        this.author = author;
        return this;
    }


}

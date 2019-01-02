package mad.android.com.mad.beans;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class Movie extends BmobObject {
    private double rating;
    private String title;
    private String genres;
    private String date;
    private String posterUrl;
    private String length;
    private String brief;
    private String casts;
    private String directors;
    private BmobRelation likes;

    public double getRating(){
        return rating;
    }
    public void setRating(double rating){
        this.rating=rating;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getGenres(){
        return genres;
    }
    public void setGenres(String genres){
        this.genres = genres;
    }

    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }

    public String getDirectors(){
        return directors;
    }
    public void setDirectors(String directors){
        this.directors = directors;
    }

    public String getCasts(){
        return casts;
    }
    public void setCasts(String casts){
        this.casts = casts;
    }

    public String getPosterUrl(){
        return posterUrl;
    }
    public void setPosterUrl(String posterUrl){
        this.posterUrl = posterUrl;
    }

    public String getLength(){
        return length;
    }
    public void setLength(String length){
        this.length = length;
    }

    public String getBrief(){
        return brief;
    }
    public void setBrief(String brief){
        this.brief = brief;
    }

    public BmobRelation getLikes() { return likes; }

    public Movie setLikes(BmobRelation likes) {
        this.likes = likes;
        return this;
    }

}

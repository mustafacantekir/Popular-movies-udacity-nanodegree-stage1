package app.com.example.mustafacan.movieapp.app;

import java.io.Serializable;

/**
 * Created by Mustafa Can on 23.04.2016.
 */
@SuppressWarnings("serial")
public class Movie implements Serializable {
    private String imageUrl;
    private String title;
    private String overview;
    private String userRating;
    private String releaseDate;

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;

    }



    public Movie(String imageUrl, String title, String overview, String userRating, String releaseDate){
        this.imageUrl=imageUrl;
        this.title=title;
        this.overview=overview;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }
}


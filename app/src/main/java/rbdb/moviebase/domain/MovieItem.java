package rbdb.moviebase.domain;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Created by Ruben on 15-6-2017.
 */

public class MovieItem implements Serializable {

    private String title;


    public MovieItem(String title) {
        this.title = title;

    }

    public MovieItem(String title, String contents, String status, DateTime updatedAt) {
        this.title = title;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title +
                '}';
    }
}

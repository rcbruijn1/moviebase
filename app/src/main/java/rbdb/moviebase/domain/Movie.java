package rbdb.moviebase.domain;

import org.joda.time.DateTime;

import java.io.Serializable;

public class Movie implements Serializable {

    private String title;
    private String contents;
    private String status;

    public Movie(String title, String contents) {
        this.title = title;
        this.contents = contents;
        this.status = null;
    }

    public Movie(String title, String contents, String status) {
        this.title = title;
        this.contents = contents;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

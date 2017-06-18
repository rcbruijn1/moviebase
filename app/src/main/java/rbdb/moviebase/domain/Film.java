package rbdb.moviebase.domain;

import org.joda.time.DateTime;

import java.io.Serializable;

public class Film implements Serializable {

    private String title;
    private String description;

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Film(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}

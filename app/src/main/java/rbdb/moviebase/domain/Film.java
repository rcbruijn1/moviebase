package rbdb.moviebase.domain;

import org.joda.time.DateTime;

import java.io.Serializable;

public class Film implements Serializable {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                '}';
    }

    private String title;

}

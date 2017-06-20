package rbdb.moviebase.domain;

import org.joda.time.DateTime;

import java.io.Serializable;

public class Film implements Serializable {

    private String title;
    private String description;
    private String inventoryID;

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", inventoryID='" + inventoryID + '\'' +
                '}';
    }


    public String getInventoryID() {
        return inventoryID;
    }

    public Film setInventoryID(String inventoryID) {
        this.inventoryID = inventoryID;
        return this;
    }


    public Film(String title, String description, String inventoryID) {
        this.title = title;
        this.description = description;
        this.inventoryID = inventoryID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

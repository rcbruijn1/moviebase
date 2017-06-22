package rbdb.moviebase.domain;

import org.joda.time.DateTime;

import java.io.Serializable;

public class Rental implements Serializable {

    private String title;
    private String description;
    private String customerID;
    private String inventoryID;

    public String getRentalID() {
        return rentalID;
    }

    public Rental setRentalID(String rentalID) {
        this.rentalID = rentalID;
        return this;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", customerID='" + customerID + '\'' +
                ", inventoryID='" + inventoryID + '\'' +
                ", rentalID='" + rentalID + '\'' +
                '}';
    }

    private String rentalID;

    public String getTitle() {
        return title;
    }

    public Rental(String title, String description, String customerID, String inventoryID, String rentalID) {
        this.title = title;
        this.description = description;
        this.customerID = customerID;
        this.inventoryID = inventoryID;
        this.rentalID = rentalID;
    }

    public Rental setTitle(String title) {
        this.title = title;


        return this;
    }

    public String getDescription() {
        return description;
    }

    public Rental setDescription(String description, String title, String customerID, String inventoryID) {
        this.description = description;
        return this;
    }



    public String getCustomerID() {
        return customerID;
    }

    public Rental setCustomerID(String customerID) {
        this.customerID = customerID;
        return this;
    }



    public String getInventoryID() {
        return inventoryID;
    }

    public Rental setInventoryID(String inventoryID) {
        this.inventoryID = inventoryID;
        return this;
    }




}

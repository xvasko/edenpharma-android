package sk.sytam.eden_pharma.models;

import com.google.gson.annotations.SerializedName;

public class Customer {

    private int id;
    private String name;
    private String city;
    private String street;
    private String zip;
    @SerializedName("phone_number")
    private String phoneNumber;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZip() {
        return zip;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

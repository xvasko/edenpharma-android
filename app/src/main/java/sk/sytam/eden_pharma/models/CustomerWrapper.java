package sk.sytam.eden_pharma.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerWrapper {

    @SerializedName("results")
    @Expose
    private List<Customer> customers = null;

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

}


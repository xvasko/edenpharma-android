package sk.sytam.eden_pharma.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class Order implements Serializable {

    private int id;
    @SerializedName("customer_id")
    private int customerId;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("customer_name")
    private String customerName;

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getUserId() {
        return userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public static final DiffUtil.ItemCallback<Order> DIFF_CALLBACK = new DiffUtil.ItemCallback<Order>() {
        @Override
        public boolean areItemsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return true; // TODO wtf is this
        }
    };
}

package sk.sytam.eden_pharma.models;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class OrderProduct {

    private int id;
    @SerializedName("order_id")
    private int orderId;
    @SerializedName("customer_id")
    private int customerId;
    private int quantity;
    @SerializedName("product_name")
    private String productName;

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }

    public static final DiffUtil.ItemCallback<OrderProduct> DIFF_CALLBACK = new DiffUtil.ItemCallback<OrderProduct>() {
        @Override
        public boolean areItemsTheSame(@NonNull OrderProduct oldItem, @NonNull OrderProduct newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull OrderProduct oldItem, @NonNull OrderProduct newItem) {
            return true; // TODO wtf is this
        }
    };
}

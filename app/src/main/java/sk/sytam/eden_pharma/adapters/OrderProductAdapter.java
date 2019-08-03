package sk.sytam.eden_pharma.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import sk.sytam.eden_pharma.R;
import sk.sytam.eden_pharma.activities.OrderActivity;
import sk.sytam.eden_pharma.models.Order;
import sk.sytam.eden_pharma.models.OrderProduct;

public class OrderProductAdapter extends PagedListAdapter<OrderProduct, OrderProductAdapter.OrderProductViewHolder> {

    private Context context;

    public OrderProductAdapter(Context context) {
        super(OrderProduct.DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public OrderProductAdapter.OrderProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_product, parent, false);
        return new OrderProductAdapter.OrderProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductAdapter.OrderProductViewHolder holder, int position) {
        OrderProduct orderProduct = getItem(position);
        holder.bindTo(orderProduct);
    }

    class OrderProductViewHolder extends RecyclerView.ViewHolder {

        private OrderProduct orderProduct;

        private TextView orderProductProductNameTextView;
        private TextView orderProductQuantityTextView;

        OrderProductViewHolder(@NonNull View itemView) {
            super(itemView);
            orderProductProductNameTextView = itemView.findViewById(R.id.item_order_product_product_name);
            orderProductQuantityTextView = itemView.findViewById(R.id.item_order_product_quantity);
        }

        void bindTo(OrderProduct orderProduct) {
            this.orderProduct = orderProduct;
            orderProductProductNameTextView.setText(orderProduct.getProductName());
            orderProductQuantityTextView.setText(orderProduct.getQuantity() + "");
        }

    }
}

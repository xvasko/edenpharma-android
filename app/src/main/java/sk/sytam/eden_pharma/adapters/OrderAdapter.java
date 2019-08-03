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
import sk.sytam.eden_pharma.activities.CustomerActivity;
import sk.sytam.eden_pharma.activities.OrderActivity;
import sk.sytam.eden_pharma.models.Order;

public class OrderAdapter extends PagedListAdapter<Order, OrderAdapter.CustomerViewHolder> {

    private Context context;

    public OrderAdapter(Context context) {
        super(Order.DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new CustomerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Order order = getItem(position);
        holder.bindTo(order);
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Order order;

        private TextView orderNameTextView;

        CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNameTextView = itemView.findViewById(R.id.item_order_name);
            itemView.setOnClickListener(this);
        }

        void bindTo(Order order) {
            this.order = order;

            orderNameTextView.setText("Objednávka #" + this.order.getId() + " pre " + this.order.getCustomerName());
        }

        @Override
        public void onClick(View v) {
            Intent orderActivity = new Intent(context, OrderActivity.class);
            orderActivity.putExtra("order_id", this.order.getId());
            orderActivity.putExtra("order_name", "Objednávka #" + this.order.getId() + " pre " + this.order.getCustomerName());
            context.startActivity(orderActivity);
        }

    }
}

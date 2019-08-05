package sk.sytam.eden_pharma.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sk.sytam.eden_pharma.R;
import sk.sytam.eden_pharma.activities.OrderActivity;
import sk.sytam.eden_pharma.models.Customer;
import sk.sytam.eden_pharma.models.Order;

public class SearchCustomerAdapter extends RecyclerView.Adapter<SearchCustomerAdapter.SearchCustomerViewHolder> {

    private List<Customer> customers;
    private Context context;

    public SearchCustomerAdapter(List<Customer> customers, Context context) {
        this.customers = customers;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchCustomerAdapter.SearchCustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer, parent, false);
        return new SearchCustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchCustomerAdapter.SearchCustomerViewHolder holder, int position) {
        Customer customer = customers.get(position);
        holder.bindTo(customer);
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public class SearchCustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Customer customer;
        TextView customerNameTextView;

        SearchCustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            customerNameTextView = itemView.findViewById(R.id.item_customer_name);
            itemView.setOnClickListener(this);
        }

        void bindTo(Customer customer) {
            this.customer = customer;
            customerNameTextView.setText(customer.getName());
        }

        @Override
        public void onClick(View v) {
            Intent orderActivity = new Intent(context, OrderActivity.class);
            Order order = new Order();
            order.setCustomerId(this.customer.getId());
            orderActivity.putExtra("order", order);
            context.startActivity(orderActivity);
        }
    }

    public void addCustomers(List<Customer> customers) {
        this.customers.addAll(customers);
        notifyDataSetChanged();
    }

    public void clearCustomers() {
        this.customers.clear();
        notifyDataSetChanged();
    }

}

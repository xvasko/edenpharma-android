package sk.sytam.eden_pharma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sk.sytam.eden_pharma.R;
import sk.sytam.eden_pharma.models.Customer;

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
        holder.customerNameTextView.setText(customer.getName());
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public static class SearchCustomerViewHolder extends RecyclerView.ViewHolder {

        TextView customerNameTextView;

        public SearchCustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            customerNameTextView = itemView.findViewById(R.id.item_customer_name);
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

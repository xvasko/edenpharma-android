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
import sk.sytam.eden_pharma.models.Customer;

public class CustomerAdapter extends PagedListAdapter<Customer, CustomerAdapter.CustomerViewHolder> {

    private Context context;

    public CustomerAdapter(Context context) {
        super(Customer.DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer, parent, false);
        return new CustomerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = getItem(position);
        holder.bindTo(customer);
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Customer customer;
        private TextView customerNameTextView;

        CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            customerNameTextView = itemView.findViewById(R.id.item_customer_name);
        }

        void bindTo(Customer customer) {
            if (customer != null) {
                this.customer = customer;
                customerNameTextView.setText(this.customer.getName());
            } else {
                customerNameTextView.setText("...");
            }

        }

        @Override
        public void onClick(View v) {
            Intent customerActivity = new Intent(context, CustomerActivity.class);
            customerActivity.putExtra("customer", customer);
            context.startActivity(customerActivity);
        }

    }
}

package sk.sytam.eden_pharma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import sk.sytam.eden_pharma.R;
import sk.sytam.eden_pharma.models.Customer;

public class CustomerAdapter extends PagedListAdapter<Customer, CustomerAdapter.CustomerViewHolder> {

    private Context context;
    private ArrayList<Customer> customers;

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

         private TextView customerNameTextView;

         CustomerViewHolder(@NonNull View itemView) {
             super(itemView);
             customerNameTextView = itemView.findViewById(R.id.item_customer_name);
         }

         void bindTo(Customer customer) {
             customerNameTextView.setText(customer.getName());
         }

         @Override
         public void onClick(View v) {

         }
     }
}

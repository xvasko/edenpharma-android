package sk.sytam.eden_pharma.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sk.sytam.eden_pharma.R;
import sk.sytam.eden_pharma.activities.AddProductToOrderActivity;
import sk.sytam.eden_pharma.activities.SearchProductActivity;
import sk.sytam.eden_pharma.models.Product;

public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.SearchProductViewHolder> {

    private List<Product> products;
    private SearchProductActivity context;

    public SearchProductAdapter(List<Product> products, SearchProductActivity context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchProductAdapter.SearchProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer, parent, false);
        return new SearchProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchProductAdapter.SearchProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bindTo(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class SearchProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Product product;
        TextView customerNameTextView;

        SearchProductViewHolder(@NonNull View itemView) {
            super(itemView);
            customerNameTextView = itemView.findViewById(R.id.item_customer_name);
            itemView.setOnClickListener(this);
        }

        void bindTo(Product product) {
            this.product = product;
            customerNameTextView.setText(product.getTitle());
        }

        @Override
        public void onClick(View v) {
            Intent addProductActivity = new Intent(context, AddProductToOrderActivity.class);
            addProductActivity.putExtra("product", product);
            context.setResult(Activity.RESULT_OK, addProductActivity);
            context.finish();
        }
    }

    public void addProducts(List<Product> customers) {
        this.products.addAll(customers);
        notifyDataSetChanged();
    }

    public void clearProducts() {
        this.products.clear();
        notifyDataSetChanged();
    }
}

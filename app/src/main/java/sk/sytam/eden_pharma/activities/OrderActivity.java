package sk.sytam.eden_pharma.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import sk.sytam.eden_pharma.R;
import sk.sytam.eden_pharma.adapters.OrderAdapter;
import sk.sytam.eden_pharma.adapters.OrderProductAdapter;
import sk.sytam.eden_pharma.models.Order;
import sk.sytam.eden_pharma.models.OrderProduct;
import sk.sytam.eden_pharma.viewmodels.CustomerActivityViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    TextView orderName;

    private PagedList<OrderProduct> orderProducts;
    private CustomerActivityViewModel customerActivityViewModel;
    private RecyclerView orderProductRecyclerView;
    private OrderProductAdapter orderProductAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent orderIntent = getIntent();
        orderName = findViewById(R.id.order_name);
        orderName.setText(orderIntent.getStringExtra("order_name"));
        final int orderId = orderIntent.getIntExtra("order_id", 0);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("objednávka");


        customerActivityViewModel = ViewModelProviders.of(this).get(CustomerActivityViewModel.class);
        orderProductRecyclerView = findViewById(R.id.order_products_recycler_view);
        swipeRefreshLayout = findViewById(R.id.order_products_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOrderProducts(orderId);
            }
        });
        getOrderProducts(orderId);


    }

    private void getOrderProducts(int orderId) {
        customerActivityViewModel.getOrderProducts(orderId).observe(this, new Observer<PagedList<OrderProduct>>() {
            @Override
            public void onChanged(PagedList<OrderProduct> orderProductsFromLiveData) {
                orderProducts = orderProductsFromLiveData;
                showOnRecyclerView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void showOnRecyclerView() {
        orderProductAdapter = new OrderProductAdapter(this);
        orderProductRecyclerView.setAdapter(orderProductAdapter);
        orderProductRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderProductAdapter.submitList(orderProducts);
    }
}

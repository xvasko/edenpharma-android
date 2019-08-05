package sk.sytam.eden_pharma.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.sytam.eden_pharma.MainActivity;
import sk.sytam.eden_pharma.R;
import sk.sytam.eden_pharma.adapters.OrderProductAdapter;
import sk.sytam.eden_pharma.api.Api;
import sk.sytam.eden_pharma.api.ApiI;
import sk.sytam.eden_pharma.models.Order;
import sk.sytam.eden_pharma.models.OrderProduct;
import sk.sytam.eden_pharma.models.Product;
import sk.sytam.eden_pharma.utils.SharedPref;
import sk.sytam.eden_pharma.viewmodels.CustomerActivityViewModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    private static final String TAG = "OrderActivity";
    public static final int ADD_PRODUCT_REQUEST = 2;

    TextView orderName;
    Button addProductButton, deleteOrderButton;
    ApiI api;
    Order order;

    private PagedList<OrderProduct> orderProducts;
    private CustomerActivityViewModel customerActivityViewModel;
    private RecyclerView orderProductRecyclerView;
    private OrderProductAdapter orderProductAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        api = Api.getInstance();
        orderName = findViewById(R.id.order_name);
        addProductButton = findViewById(R.id.add_product_button);
        deleteOrderButton = findViewById(R.id.delete_order_button);

        customerActivityViewModel = ViewModelProviders.of(this).get(CustomerActivityViewModel.class);
        orderProductRecyclerView = findViewById(R.id.order_products_recycler_view);
        swipeRefreshLayout = findViewById(R.id.order_products_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOrderProducts(order.getId());
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("objednávka");

        Intent orderIntent = getIntent();
        order = (Order) orderIntent.getSerializableExtra("order");

        // creating a new order
        if (order.getId() == 0) {
            createOrder();
        } else {
            addProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent addProduct = new Intent(OrderActivity.this, AddProductToOrderActivity.class);
                    addProduct.putExtra("order", order);
                    startActivityForResult(addProduct, ADD_PRODUCT_REQUEST);
                }
            });
            deleteOrderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteOrder();
                }
            });
            orderName.setText("Objednávka #" + order.getId() + " pre " + order.getCustomerName());
            getOrderProducts(order.getId());
        }
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

    private void createOrder() {
        Call<Order> call = api.createOrder("Token " + SharedPref.getInstance().getToken(), order.getCustomerId());
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Log.d(TAG, "onResponse: ");
                if (!response.isSuccessful()) {
                    return;
                }

                order = response.body();
                orderName.setText("Objednávka #" + order.getId() + " pre " + order.getCustomerName());
                addProductButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent addProduct = new Intent(OrderActivity.this, AddProductToOrderActivity.class);
                        addProduct.putExtra("order", order);
                        startActivityForResult(addProduct, ADD_PRODUCT_REQUEST);
                    }
                });
                deleteOrderButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteOrder();
                    }
                });
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    private void deleteOrder() {
        Call<Void> call = api.deleteOrder("Token " + SharedPref.getInstance().getToken(), order.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, "onResponse: onExistingOrder");
                if (!response.isSuccessful()) {
                    return;
                }

                Intent mainActivity = new Intent(OrderActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "onFailure: onExistingOrder");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PRODUCT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                getOrderProducts(order.getId());
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }
}

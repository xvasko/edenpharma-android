package sk.sytam.eden_pharma.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.sytam.eden_pharma.R;
import sk.sytam.eden_pharma.adapters.SearchCustomerAdapter;
import sk.sytam.eden_pharma.api.Api;
import sk.sytam.eden_pharma.models.Customer;
import sk.sytam.eden_pharma.models.CustomerWrapper;
import sk.sytam.eden_pharma.utils.SharedPref;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

// https://www.youtube.com/watch?v=tiXP__iYtq4
public class SearchCustomerActivity extends AppCompatActivity {

    private static final String TAG = "SearchCustomerActivity";

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SearchCustomerAdapter searchCustomerAdapter;
    private LinearLayoutManager layoutManager;

    private int pageNumber = 1;
    private boolean isLoading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount, previousTotal = 0;
    private int viewThreshold = 10;

    private Call<CustomerWrapper> call;
    private String query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_customer);

        recyclerView = findViewById(R.id.search_customer_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        progressBar = findViewById(R.id.search_customer_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        callFirstPage();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                if (dy > 0) {
                    if (isLoading) {
                        if (totalItemCount > previousTotal) {
                            isLoading = false;
                            previousTotal = totalItemCount;
                        }
                    }

                    if (!isLoading && (totalItemCount - visibleItemCount) <= (pastVisibleItems + viewThreshold)) {
                        pageNumber++;
                        performPagination();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void performPagination() {
        progressBar.setVisibility(View.VISIBLE);
//
//        if (call != null) {
//            call.cancel();
//        }

        if (query.equals("")) {
            call = Api.getInstance().getCustomers("Token " + SharedPref.getInstance().getToken(), pageNumber);
        } else {
            call = Api.getInstance().getCustomers("Token " + SharedPref.getInstance().getToken(), query, pageNumber);
        }
        call.enqueue(new Callback<CustomerWrapper>() {
            @Override
            public void onResponse(Call<CustomerWrapper> call, Response<CustomerWrapper> response) {
                Log.d(TAG, "onResponse: ");
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: failed");
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                CustomerWrapper customerWrapper = response.body();

                if (customerWrapper != null && customerWrapper.getCustomers() != null) {
                    List<Customer> customers = customerWrapper.getCustomers();
                    searchCustomerAdapter.addCustomers(customers);
                    Toast.makeText(SearchCustomerActivity.this, "Page number: " + pageNumber + " is loaded...", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CustomerWrapper> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    private void callFirstPage() {
        if (call != null) {
            call.cancel();
        }
        if (query.equals("")) {
            call = Api.getInstance().getCustomers("Token " + SharedPref.getInstance().getToken(), pageNumber);
        } else {
            call = Api.getInstance().getCustomers("Token " + SharedPref.getInstance().getToken(), query, pageNumber);
            searchCustomerAdapter.clearCustomers();
        }
        call.enqueue(new Callback<CustomerWrapper>() {
            @Override
            public void onResponse(Call<CustomerWrapper> call, Response<CustomerWrapper> response) {
                Log.d(TAG, "onResponse: ");
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: failed code: " + response.code());
                    return;
                }

                CustomerWrapper customerWrapper = response.body();

                if (customerWrapper != null && customerWrapper.getCustomers() != null) {
                    List<Customer> customers = customerWrapper.getCustomers();
                    searchCustomerAdapter = new SearchCustomerAdapter(customers, SearchCustomerActivity.this);
                    recyclerView.setAdapter(searchCustomerAdapter);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CustomerWrapper> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                query = "";
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                query = newText;
                pageNumber = 1;
                pastVisibleItems = 0;
                visibleItemCount = 0;
                totalItemCount = 0;
                previousTotal = 0;
                searchCustomerAdapter.clearCustomers();
                callFirstPage();
                return false;
            }
        });
        return true;
    }
}

package sk.sytam.eden_pharma;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import sk.sytam.eden_pharma.adapters.CustomerAdapter;
import sk.sytam.eden_pharma.models.Customer;
import sk.sytam.eden_pharma.viewmodels.MainActivityViewModel;

public class CustomersFragment extends Fragment {

    private PagedList<Customer> customers;
    private MainActivityViewModel mainActivityViewModel;
    private RecyclerView customersRecyclerView;
    private CustomerAdapter customerAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customers_tab, container, false);
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        customersRecyclerView = view.findViewById(R.id.customers_recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.customers_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        getCustomers();
        return view;
    }

    private void getCustomers() {
        mainActivityViewModel.getCustomerPagedList().observe(this, new Observer<PagedList<Customer>>() {
            @Override
            public void onChanged(PagedList<Customer> customersFromLiveData) {
                customers = customersFromLiveData;
                showOnRecyclerView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void showOnRecyclerView() {
        customerAdapter = new CustomerAdapter(getActivity());
        customersRecyclerView.setAdapter(customerAdapter);
        customersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        customerAdapter.submitList(customers);

    }
}

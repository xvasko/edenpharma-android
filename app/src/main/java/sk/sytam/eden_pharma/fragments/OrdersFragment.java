package sk.sytam.eden_pharma.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import sk.sytam.eden_pharma.R;
import sk.sytam.eden_pharma.adapters.OrderAdapter;
import sk.sytam.eden_pharma.models.Order;
import sk.sytam.eden_pharma.viewmodels.MainActivityViewModel;

public class OrdersFragment extends Fragment {

    private FloatingActionButton floatingActionButton;

    private PagedList<Order> orders;
    private MainActivityViewModel mainActivityViewModel;
    private RecyclerView ordersRecyclerView;
    private OrderAdapter orderAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orders_tab, container, false);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        ordersRecyclerView = view.findViewById(R.id.orders_recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.orders_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOrders();
            }
        });
        getOrders();

        floatingActionButton = view.findViewById(R.id.orders_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void getOrders() {
        mainActivityViewModel.getOrders().observe(this, new Observer<PagedList<Order>>() {
            @Override
            public void onChanged(PagedList<Order> ordersFromLiveData) {
                orders = ordersFromLiveData;
                showOnRecyclerView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void showOnRecyclerView() {
        orderAdapter = new OrderAdapter(getActivity());
        ordersRecyclerView.setAdapter(orderAdapter);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderAdapter.submitList(orders);
    }
}

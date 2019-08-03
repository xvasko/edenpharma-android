package sk.sytam.eden_pharma.paging;

import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.sytam.eden_pharma.api.Api;
import sk.sytam.eden_pharma.api.ApiI;
import sk.sytam.eden_pharma.models.Order;
import sk.sytam.eden_pharma.models.OrderWrapper;
import sk.sytam.eden_pharma.utils.SharedPref;

public class OrderDataSource extends PageKeyedDataSource<Long, Order> {

    private static final String TAG = "OrderDataSource";

    private ApiI api;
    private String token;

    OrderDataSource() {
        this.api = Api.getInstance();
        this.token = SharedPref.getInstance().getToken();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, Order> callback) {
        Log.d(TAG, "loadInitial: ");
        Call<OrderWrapper> call = api.getOrders("Token " + token, 1);
        call.enqueue(new Callback<OrderWrapper>() {
            @Override
            public void onResponse(Call<OrderWrapper> call, Response<OrderWrapper> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                OrderWrapper customerWrapper = response.body();

                if (customerWrapper != null && customerWrapper.getOrders() != null) {
                    List<Order> customers = customerWrapper.getOrders();
                    callback.onResult(customers, null, (long) 2);
                }

            }

            @Override
            public void onFailure(Call<OrderWrapper> call, Throwable t) {
                Log.d(TAG, "loadInitial: onFailure: ");
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Order> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, Order> callback) {
        Log.d(TAG, "loadAfter: ");
        Call<OrderWrapper> call = api.getOrders("Token " + token, params.key);
        call.enqueue(new Callback<OrderWrapper>() {
            @Override
            public void onResponse(Call<OrderWrapper> call, Response<OrderWrapper> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                OrderWrapper orderWrapper = response.body();

                if (orderWrapper != null && orderWrapper.getOrders() != null) {
                    List<Order> customers = orderWrapper.getOrders();
                    callback.onResult(customers, params.key + 1);
                }
            }

            @Override
            public void onFailure(Call<OrderWrapper> call, Throwable t) {
                Log.d(TAG, "loadAfter: onFailure: ");
            }
        });
    }
}

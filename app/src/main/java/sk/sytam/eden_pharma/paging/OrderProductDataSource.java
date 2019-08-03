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
import sk.sytam.eden_pharma.models.OrderProduct;
import sk.sytam.eden_pharma.models.OrderProductWrapper;
import sk.sytam.eden_pharma.utils.SharedPref;

public class OrderProductDataSource extends PageKeyedDataSource<Long, OrderProduct> {

    private static final String TAG = "OrderProductDataSource";

    private ApiI api;
    private String token;
    private int orderId;

    OrderProductDataSource(int orderId) {
        this.api = Api.getInstance();
        this.token = SharedPref.getInstance().getToken();
        this.orderId = orderId;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, OrderProduct> callback) {
        Log.d(TAG, "loadInitial: ");
        Call<OrderProductWrapper> call = api.getOrderProducts("Token " + token, orderId, 1);
        call.enqueue(new Callback<OrderProductWrapper>() {
            @Override
            public void onResponse(Call<OrderProductWrapper> call, Response<OrderProductWrapper> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                OrderProductWrapper orderProductWrapper = response.body();

                if (orderProductWrapper != null && orderProductWrapper.getOrderProducts() != null) {
                    List<OrderProduct> orderProducts = orderProductWrapper.getOrderProducts();
                    callback.onResult(orderProducts, null, (long) 2);
                }

            }

            @Override
            public void onFailure(Call<OrderProductWrapper> call, Throwable t) {
                Log.d(TAG, "loadInitial: onFailure: ");
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, OrderProduct> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, OrderProduct> callback) {
        Log.d(TAG, "loadAfter: ");
        Call<OrderProductWrapper> call = api.getOrderProducts("Token " + token, orderId, params.key);
        call.enqueue(new Callback<OrderProductWrapper>() {
            @Override
            public void onResponse(Call<OrderProductWrapper> call, Response<OrderProductWrapper> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                OrderProductWrapper orderProductWrapper = response.body();

                if (orderProductWrapper != null && orderProductWrapper.getOrderProducts() != null) {
                    List<OrderProduct> customers = orderProductWrapper.getOrderProducts();
                    callback.onResult(customers, params.key + 1);
                }
            }

            @Override
            public void onFailure(Call<OrderProductWrapper> call, Throwable t) {
                Log.d(TAG, "loadAfter: onFailure: ");
            }
        });
    }
}

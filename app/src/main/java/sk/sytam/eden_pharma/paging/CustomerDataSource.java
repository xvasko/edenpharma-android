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
import sk.sytam.eden_pharma.models.Customer;
import sk.sytam.eden_pharma.models.CustomerWrapper;
import sk.sytam.eden_pharma.utils.SharedPref;

public class CustomerDataSource extends PageKeyedDataSource<Long, Customer> {

    private static final String TAG = "CustomerDataSource";

    private ApiI api;
    private String token;

    CustomerDataSource() {
        this.api = Api.getInstance();
        this.token = SharedPref.getInstance().getToken();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, Customer> callback) {
        Log.d(TAG, "loadInitial: ");
        Call<CustomerWrapper> call = api.getCustomers("Token " + token, 1);
        call.enqueue(new Callback<CustomerWrapper>() {
            @Override
            public void onResponse(Call<CustomerWrapper> call, Response<CustomerWrapper> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                CustomerWrapper customerWrapper = response.body();

                if (customerWrapper != null && customerWrapper.getCustomers() != null) {
                    List<Customer> customers = customerWrapper.getCustomers();
                    callback.onResult(customers, null, (long) 2);
                }

            }

            @Override
            public void onFailure(Call<CustomerWrapper> call, Throwable t) {
                Log.d(TAG, "loadInitial: onFailure: ");
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Customer> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, Customer> callback) {
        Log.d(TAG, "loadAfter: ");
        Call<CustomerWrapper> call = api.getCustomers("Token " + token, params.key);
        call.enqueue(new Callback<CustomerWrapper>() {
            @Override
            public void onResponse(Call<CustomerWrapper> call, Response<CustomerWrapper> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                CustomerWrapper customerWrapper = response.body();

                if (customerWrapper != null && customerWrapper.getCustomers() != null) {
                    List<Customer> customers = customerWrapper.getCustomers();
                    callback.onResult(customers, params.key + 1);
                }
            }

            @Override
            public void onFailure(Call<CustomerWrapper> call, Throwable t) {
                Log.d(TAG, "loadAfter: onFailure: ");
            }
        });

    }
}

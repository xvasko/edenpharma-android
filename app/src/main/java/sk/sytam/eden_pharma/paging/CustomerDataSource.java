package sk.sytam.eden_pharma.paging;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sk.sytam.eden_pharma.EdenPharmaApi;
import sk.sytam.eden_pharma.models.Customer;
import sk.sytam.eden_pharma.models.CustomerWrapper;

public class CustomerDataSource extends PageKeyedDataSource<Long, Customer> {

    private EdenPharmaApi api;
    private Application application;

    public CustomerDataSource(EdenPharmaApi api, Application application) {
        this.api = api;
        this.application = application;
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:8000/api/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, Customer> callback) {

        Call<CustomerWrapper> call = api.getCustomers("Token " + "ffd5f607ad88b881cadf6ba63620140d1af44a59", 1);
        call.enqueue(new Callback<CustomerWrapper>() {
            @Override
            public void onResponse(Call<CustomerWrapper> call, Response<CustomerWrapper> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                List<Customer> customers = response.body().getCustomers();

                callback.onResult(customers, null, (long) 2);
            }

            @Override
            public void onFailure(Call<CustomerWrapper> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Customer> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, Customer> callback) {
        Call<CustomerWrapper> call = api.getCustomers("Token " + "ffd5f607ad88b881cadf6ba63620140d1af44a59", params.key);
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

            }
        });
    }
}

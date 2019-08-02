package sk.sytam.eden_pharma.viewmodels;

import android.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sk.sytam.eden_pharma.EdenPharmaApi;
import sk.sytam.eden_pharma.models.Customer;
import sk.sytam.eden_pharma.paging.CustomerDataSource;
import sk.sytam.eden_pharma.paging.CustomerDataSourceFactory;

public class MainActivityViewModel extends AndroidViewModel {


    private LiveData<PagedList<Customer>> customerPagedList;
    LiveData<CustomerDataSource> customerDataSourceLiveData;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EdenPharmaApi edenPharmaApi = retrofit.create(EdenPharmaApi.class);

        CustomerDataSourceFactory factory = new CustomerDataSourceFactory(edenPharmaApi, application);
        customerDataSourceLiveData = factory.getMutableLiveData();

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(4)
                .build();

        Executor executor = Executors.newFixedThreadPool(5);

        customerPagedList = (new LivePagedListBuilder<Long, Customer>(factory, config))
                .setFetchExecutor(executor)
                .build();
    }


    public LiveData<CustomerDataSource> getCustomerDataSourceLiveData() {
        return customerDataSourceLiveData;
    }

    public LiveData<PagedList<Customer>> getCustomerPagedList() {
        return customerPagedList;
    }
}

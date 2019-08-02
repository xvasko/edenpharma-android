package sk.sytam.eden_pharma.paging;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import sk.sytam.eden_pharma.EdenPharmaApi;

public class CustomerDataSourceFactory extends DataSource.Factory {

    private CustomerDataSource dataSource;
    private EdenPharmaApi api;
    private Application application;
    private MutableLiveData<CustomerDataSource> mutableLiveData;

    public CustomerDataSourceFactory(EdenPharmaApi api, Application application) {
        this.api = api;
        this.application = application;
        this.mutableLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource create() {
        dataSource = new CustomerDataSource(api, application);
        mutableLiveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<CustomerDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}

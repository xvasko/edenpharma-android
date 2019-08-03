package sk.sytam.eden_pharma.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import sk.sytam.eden_pharma.models.Customer;
import sk.sytam.eden_pharma.models.Order;
import sk.sytam.eden_pharma.paging.CustomerDataSourceFactory;
import sk.sytam.eden_pharma.paging.OrderDataSourceFactory;

public class MainActivityViewModel extends ViewModel {

    public LiveData<PagedList<Customer>> getCustomers() {
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(4)
                .build();
        return new LivePagedListBuilder<>(new CustomerDataSourceFactory(), config).build();
    }

    public LiveData<PagedList<Order>> getOrders() {
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(4)
                .build();
        return new LivePagedListBuilder<>(new OrderDataSourceFactory(), config).build();
    }
}

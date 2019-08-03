package sk.sytam.eden_pharma.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import sk.sytam.eden_pharma.models.OrderProduct;
import sk.sytam.eden_pharma.paging.OrderProductDataSourceFactory;

public class CustomerActivityViewModel extends ViewModel {

    public LiveData<PagedList<OrderProduct>> getOrderProducts(int orderId) {
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(4)
                .build();
        return new LivePagedListBuilder<>(new OrderProductDataSourceFactory(orderId), config).build();
    }

}
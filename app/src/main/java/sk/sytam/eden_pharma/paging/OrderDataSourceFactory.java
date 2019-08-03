package sk.sytam.eden_pharma.paging;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import sk.sytam.eden_pharma.models.Order;

public class OrderDataSourceFactory extends DataSource.Factory<Long, Order> {

    @NonNull
    @Override
    public DataSource<Long, Order> create() {
        return new OrderDataSource();
    }
}

package sk.sytam.eden_pharma.paging;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import sk.sytam.eden_pharma.models.Order;
import sk.sytam.eden_pharma.models.OrderProduct;

public class OrderProductDataSourceFactory extends DataSource.Factory<Long, OrderProduct> {

    private int orderId;

    public OrderProductDataSourceFactory(int orderId) {
        this.orderId = orderId;
    }

    @NonNull
    @Override
    public DataSource<Long, OrderProduct> create() {
        return new OrderProductDataSource(orderId);
    }
}

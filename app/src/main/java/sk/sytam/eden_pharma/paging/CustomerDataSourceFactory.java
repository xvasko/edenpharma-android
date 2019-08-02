package sk.sytam.eden_pharma.paging;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import sk.sytam.eden_pharma.models.Customer;

public class CustomerDataSourceFactory extends DataSource.Factory<Long, Customer> {

    @NonNull
    @Override
    public DataSource<Long, Customer> create() {
        return new CustomerDataSource();
    }
}

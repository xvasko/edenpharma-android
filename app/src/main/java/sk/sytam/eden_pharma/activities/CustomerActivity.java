package sk.sytam.eden_pharma.activities;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import sk.sytam.eden_pharma.R;
import sk.sytam.eden_pharma.databinding.ActivityCustomerBinding;
import sk.sytam.eden_pharma.models.Customer;

public class CustomerActivity extends AppCompatActivity {

    ActivityCustomerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer);

        Intent customerIntent = getIntent();
        Customer customer = (Customer) customerIntent.getSerializableExtra("customer");

        binding.setCustomer(customer);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("zákazník");

    }
}

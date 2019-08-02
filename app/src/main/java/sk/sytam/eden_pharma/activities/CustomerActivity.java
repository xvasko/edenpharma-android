package sk.sytam.eden_pharma.activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import sk.sytam.eden_pharma.R;

public class CustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

package sk.sytam.eden_pharma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sk.sytam.eden_pharma.account.LoginActivity;
import sk.sytam.eden_pharma.adapters.ViewPagerAdapter;
import sk.sytam.eden_pharma.models.Customer;
import sk.sytam.eden_pharma.models.CustomerWrapper;
import sk.sytam.eden_pharma.viewmodels.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setElevation(0);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedUser = preferences.getString("@string/session_username", "prihláste sa");
        getSupportActionBar().setTitle("Ste prihlásený ako " + savedUser);

        viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EdenPharmaApi edenPharmaApi = retrofit.create(EdenPharmaApi.class);

//        Call<CustomerWrapper> call = edenPharmaApi.getCustomers("Token " + "dc7e3aa81fda3ae353239d156e929b6cb1e73105");

//        call.enqueue(new Callback<CustomerWrapper>() {
//            @Override
//            public void onResponse(Call<CustomerWrapper> call, Response<CustomerWrapper> response) {
//
//                if (!response.isSuccessful()) {
//                    textView.setText("Code: " + response.code());
//                    return;
//                }
//
//                List<Customer> customers = response.body().getCustomers();
//
//                for (Customer customer : customers) {
//                    String content = "";
//                    content += "ID: " + customer.getId() + "\n";
//                    content += "Name: " + customer.getName() + "\n";
//
//                    textView.append(content);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<CustomerWrapper> call, Throwable t) {
//                textView.setText(t.getMessage());
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                preferences.edit().clear().apply();
                Intent mainActivity = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(mainActivity);
                Toast.makeText(this, "Odhlasujem!", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new OrdersFragment(), "Objednávky");
        viewPagerAdapter.addFragment(new CustomersFragment(), "Zákzníci");
        viewPager.setAdapter(viewPagerAdapter);
    }

}

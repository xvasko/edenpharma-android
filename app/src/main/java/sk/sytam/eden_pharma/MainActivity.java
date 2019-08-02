package sk.sytam.eden_pharma;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import sk.sytam.eden_pharma.account.LoginActivity;
import sk.sytam.eden_pharma.adapters.ViewPagerAdapter;
import sk.sytam.eden_pharma.fragments.CustomersFragment;
import sk.sytam.eden_pharma.fragments.OrdersFragment;
import sk.sytam.eden_pharma.utils.SharedPref;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String savedUser = SharedPref.getInstance(this).getUsername();
        getSupportActionBar().setTitle("Ste prihlásený ako " + savedUser);
        getSupportActionBar().setElevation(0);

        viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
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
                SharedPref.getInstance(this).clear();
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

package sk.sytam.eden_pharma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sk.sytam.eden_pharma.account.LoginActivity;
import sk.sytam.eden_pharma.models.Customer;
import sk.sytam.eden_pharma.models.CustomerWrapper;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedUser = preferences.getString("@string/session_username", "prihláste sa");
        getSupportActionBar().setTitle("Ste prihlásený ako " + savedUser);

        textView = findViewById(R.id.text_view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EdenPharmaApi edenPharmaApi = retrofit.create(EdenPharmaApi.class);

        Call<CustomerWrapper> call = edenPharmaApi.getCustomers("Token " + "dc7e3aa81fda3ae353239d156e929b6cb1e73105");

        call.enqueue(new Callback<CustomerWrapper>() {
            @Override
            public void onResponse(Call<CustomerWrapper> call, Response<CustomerWrapper> response) {

                if (!response.isSuccessful()) {
                    textView.setText("Code: " + response.code());
                    return;
                }

                List<Customer> customers = response.body().getCustomers();

                for (Customer customer : customers) {
                    String content = "";
                    content += "ID: " + customer.getId() + "\n";
                    content += "Name: " + customer.getName() + "\n";

                    textView.append(content);
                }

            }

            @Override
            public void onFailure(Call<CustomerWrapper> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });

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
}

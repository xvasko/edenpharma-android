package sk.sytam.eden_pharma.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sk.sytam.eden_pharma.api.Api;
import sk.sytam.eden_pharma.api.ApiI;
import sk.sytam.eden_pharma.MainActivity;
import sk.sytam.eden_pharma.R;
import sk.sytam.eden_pharma.utils.SharedPref;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private ProgressBar progressBar;
    private EditText username_edit_text;
    private EditText password_edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String savedToken = SharedPref.getInstance().getToken();

        if (savedToken != null) {
            Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainActivity);
        }

        Button button = findViewById(R.id.btn_login);

        username_edit_text = findViewById(R.id.input_username);
        password_edit_text = findViewById(R.id.input_password);
        progressBar = findViewById(R.id.login_request_loading_progress_bar);
        progressBar.setVisibility(View.GONE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Attempting to log in.");
                String username = username_edit_text.getText().toString();
                String password = password_edit_text.getText().toString();

                if (!username.equals("") && !password.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    logIn(username, password);
                }

            }
        });
    }

    private void logIn(final String username, final String password) {
        Call<TokenWrapper> call = Api.getInstance().logIn(username, password);
        call.enqueue(new Callback<TokenWrapper>() {
            @Override
            public void onResponse(Call<TokenWrapper> call, Response<TokenWrapper> response) {
                try {
                    String token = response.body().getToken();
                    Log.d(TAG, "onResponse: token: " + token);

                    if (!token.equals("")) {
                        setSessionParams(username, token);
                        progressBar.setVisibility(View.GONE);
                        username_edit_text.setText("");
                        password_edit_text.setText("");
                        Toast.makeText(LoginActivity.this, "Prihlásenie úspešné!", Toast.LENGTH_SHORT).show();
                        Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(mainActivity);
                    }
                } catch (NullPointerException e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Prihlásenie zlyhalo.", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: NullPointerException: " + e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<TokenWrapper> call, Throwable t) {
                Log.d(TAG, "onFailure: throwable: " + t.getMessage());
            }
        });
    }

    private void setSessionParams(String username, String token) {
        Log.d(TAG, "setSessionParams: Storing session variables: \n" +
                "username: " + username + "\n" +
                "token: " + token);

        SharedPref.getInstance().setUsername(username);
        SharedPref.getInstance().setToken(token);

    }

}

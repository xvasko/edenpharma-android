package sk.sytam.eden_pharma.activities;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.sytam.eden_pharma.R;
import sk.sytam.eden_pharma.api.Api;
import sk.sytam.eden_pharma.models.Order;
import sk.sytam.eden_pharma.models.Product;
import sk.sytam.eden_pharma.utils.SharedPref;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddProductToOrderActivity extends AppCompatActivity {

    public static final int SEARCH_PRODUCT_REQUEST = 1;  // The request code

    private Order order;
    private Product product;

    EditText productNameEditText;
    EditText productQuantityEditText;
    Button addProductToOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra("order");

        productNameEditText = findViewById(R.id.input_product_name);
        productNameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchProductActivity = new Intent(AddProductToOrderActivity.this, SearchProductActivity.class);
                startActivityForResult(searchProductActivity, SEARCH_PRODUCT_REQUEST);
            }
        });
        productQuantityEditText = findViewById(R.id.input_quantity);
        addProductToOrder = findViewById(R.id.add_product_to_order_button);
        addProductToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product != null) {
                    Call<Void> call = Api.getInstance().createOrderProduct(
                            "Token " + SharedPref.getInstance().getToken(),
                            order.getId(),
                            product.getId(),
                            Integer.parseInt(productQuantityEditText.getText().toString()));
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            setResult(RESULT_OK);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH_PRODUCT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                product = (Product) data.getSerializableExtra("product");
                productNameEditText.setText(product.getTitle());
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }
}

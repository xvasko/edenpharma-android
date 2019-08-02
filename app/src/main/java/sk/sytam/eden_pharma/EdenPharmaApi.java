package sk.sytam.eden_pharma;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sk.sytam.eden_pharma.account.TokenWrapper;
import sk.sytam.eden_pharma.models.CustomerWrapper;

public interface EdenPharmaApi {

    @GET("customers")
    Call<CustomerWrapper> getCustomers(@Header("Authorization") String token, @Query("page") long page);

    @FormUrlEncoded
    @POST("accounts/login")
    Call<TokenWrapper> logIn(
            @Field("username") String username,
            @Field("password") String password
    );

}

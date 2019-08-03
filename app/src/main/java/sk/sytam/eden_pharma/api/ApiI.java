package sk.sytam.eden_pharma.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sk.sytam.eden_pharma.account.TokenWrapper;
import sk.sytam.eden_pharma.models.CustomerWrapper;
import sk.sytam.eden_pharma.models.OrderProductWrapper;
import sk.sytam.eden_pharma.models.OrderWrapper;

public interface ApiI {

    @GET("customers")
    Call<CustomerWrapper> getCustomers(@Header("Authorization") String token, @Query("page") long page);

    @GET("orders")
    Call<OrderWrapper> getOrders(@Header("Authorization") String token, @Query("page") long page);

    @GET("order/{id}")
    Call<OrderProductWrapper> getOrderProducts(
            @Header("Authorization") String token,
            @Path("id") int id,
            @Query("page") long page);

    @FormUrlEncoded
    @POST("accounts/login")
    Call<TokenWrapper> logIn(
            @Field("username") String username,
            @Field("password") String password
    );

}

package sk.sytam.eden_pharma.api;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sk.sytam.eden_pharma.account.TokenWrapper;
import sk.sytam.eden_pharma.models.CustomerWrapper;
import sk.sytam.eden_pharma.models.Order;
import sk.sytam.eden_pharma.models.OrderProduct;
import sk.sytam.eden_pharma.models.OrderProductWrapper;
import sk.sytam.eden_pharma.models.OrderWrapper;
import sk.sytam.eden_pharma.models.ProductWrapper;

public interface ApiI {

    @GET("customers")
    Call<CustomerWrapper> getCustomers(@Header("Authorization") String token, @Query("page") long page);

    @GET("customers")
    Call<CustomerWrapper> getCustomers(
            @Header("Authorization") String token,
            @Query("q") String query,
            @Query("page") long page
    );

    @GET("products")
    Call<ProductWrapper> getProducts(@Header("Authorization") String token, @Query("page") long page);

    @GET("products")
    Call<ProductWrapper> getProducts(
            @Header("Authorization") String token,
            @Query("q") String query,
            @Query("page") long page
    );

    @GET("orders")
    Call<OrderWrapper> getOrders(@Header("Authorization") String token, @Query("page") long page);

    @GET("order/{id}")
    Call<OrderProductWrapper> getOrderProducts(
            @Header("Authorization") String token,
            @Path("id") int id,
            @Query("page") long page);

    @FormUrlEncoded
    @POST("order/{orderId}/order-product/{productId}/create")
    Call<Void> createOrderProduct(
            @Header("Authorization") String token,
            @Path("orderId") int orderId,
            @Path("productId") int productId,
            @Field("quantity") int quantity);

    @FormUrlEncoded
    @POST("accounts/login")
    Call<TokenWrapper> logIn(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("order/create")
    Call<Order> createOrder(
            @Header("Authorization") String token,
            @Field("customer_id") int customerId
    );

    @DELETE("order/{id}/delete")
    Call<Void> deleteOrder(
            @Header("Authorization") String token,
            @Path("id") int orderId
    );



}

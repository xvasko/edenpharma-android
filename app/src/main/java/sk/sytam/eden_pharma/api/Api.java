package sk.sytam.eden_pharma.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static final String API_URL = "http://10.0.2.2:8000/api/";

    private static volatile ApiI instance;

    public static ApiI getInstance() {
        if (instance == null) {
            synchronized (Api.class) {
                if (instance == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(API_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    instance = retrofit.create(ApiI.class);
                }
            }
        }
        return instance;
    }
}

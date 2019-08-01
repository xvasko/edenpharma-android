package sk.sytam.eden_pharma.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenWrapper {

    @SerializedName("token")
    @Expose
    private String token;

     String getToken() {
        return token;
    }

}

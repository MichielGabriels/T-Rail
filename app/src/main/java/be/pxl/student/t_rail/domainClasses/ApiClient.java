package be.pxl.student.t_rail.domainClasses;

import okhttp3.OkHttpClient;

public class ApiClient {

   private OkHttpClient client;
   public final String baseUrl = "https://api.irail.be/";

    public ApiClient(){
        client = new OkHttpClient();
    }

    public OkHttpClient getClient() {
        return client;
    }
}

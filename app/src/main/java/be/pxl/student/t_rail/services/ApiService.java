package be.pxl.student.t_rail.services;

import java.io.IOException;

import be.pxl.student.t_rail.domainClasses.ApiClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiService {

    private ApiClient client;

    public ApiService(){
        client = new ApiClient();
    }

    //parameter url is relative url
    public String doGetRequest(String url) throws IOException {
        String requestUrl = client.baseUrl + url;
        Request request = new Request.Builder().url(requestUrl).build();
        Response response = client.getClient().newCall(request).execute();
        return response.body().string();
    }

}

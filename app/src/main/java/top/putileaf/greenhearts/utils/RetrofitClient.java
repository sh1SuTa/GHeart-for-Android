package top.putileaf.greenhearts.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://localhost:8080/";

    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)  // 设置后端 API 基础地址
                    .addConverterFactory(GsonConverterFactory.create())  // 使用 Gson 作为转换器
                    .build();
        }
        return retrofit;
    }
}

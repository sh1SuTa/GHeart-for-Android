package top.putileaf.greenhearts.backendApi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

// 定义接口
public interface ApiService {
    @POST("register")
    Call<ApiResponse> register(@Body RegisterRequest request);  // 注册接口

    @POST("login")
    Call<ApiResponse> login(@Body LoginRequest request);  // 登录接口
}

// 示例请求体类
class RegisterRequest {
    private String username;
    private String password;

    public RegisterRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

class LoginRequest{
    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

// 示例响应类
class ApiResponse {
    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}

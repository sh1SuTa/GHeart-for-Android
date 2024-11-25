package top.putileaf.greenhearts.activities;


import static top.putileaf.greenhearts.utils.PublicApi.PUBLIC_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.putileaf.greenhearts.R;
import top.putileaf.greenhearts.entity.Result;
import top.putileaf.greenhearts.entity.User;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    EditText rePasswordEditText;

    OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        usernameEditText  = findViewById(R.id.et_r_username);
        passwordEditText  = findViewById(R.id.et_r_password);
        rePasswordEditText = findViewById(R.id.et_r_re_password);




    }

    public void backLogin(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // 销毁当前活动
    }
    public void register(View view){
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String rePassword = rePasswordEditText.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else if (!rePassword.equals(password)){
            Toast.makeText(this, "两次密码输入不相同", Toast.LENGTH_SHORT).show();
            return;
        }

        okHttpClient = new OkHttpClient.Builder().build();
        FormBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(PUBLIC_URL+"user/register")
                .post(formBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //System.out.println("响应："+response.body().string());
                String responseBody = response.body().string();
                Gson gson = new Gson();
                // 使用 TypeToken 来解析泛型
                Type resultType = new TypeToken<Result<User>>() {}.getType();
                Result<User> apiResponse = gson.fromJson(responseBody, resultType);
                if (apiResponse.getCode() == 1) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });





    }
}
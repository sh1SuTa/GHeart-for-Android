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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.putileaf.greenhearts.MainActivity;
import top.putileaf.greenhearts.R;
import top.putileaf.greenhearts.entity.Result;
import top.putileaf.greenhearts.entity.User;

public class LoginActivity extends AppCompatActivity {
    EditText usernameEditText;
    EditText passwordEditText;
    OkHttpClient okHttpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        usernameEditText  = findViewById(R.id.et_l_username);
        passwordEditText  = findViewById(R.id.et_l_password);
    }


    public void toRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish(); // 销毁当前活动
    }

    public void login(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        okHttpClient = new OkHttpClient.Builder().build();
        FormBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(PUBLIC_URL+"user/login")
                .post(formBody)
                .build();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseBody = response.body().string();
                Gson gson = new Gson();
                // 使用 TypeToken 来解析泛型
                Type resultType = new TypeToken<Result<String>>() {}.getType();
                Result<String> apiResponse = gson.fromJson(responseBody, resultType);

                if (apiResponse.getCode() == 1) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, apiResponse.getData(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                String token = apiResponse.getData();
                // 跳转到目标 Activity
                runOnUiThread(() -> {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    // 如果需要传递 token，可以通过 putExtra
                    intent.putExtra("TOKEN", token);
                    startActivity(intent);
                    finish();
                });
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });
    }
}
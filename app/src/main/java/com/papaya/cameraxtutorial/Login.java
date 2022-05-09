package com.papaya.cameraxtutorial;

import android.os.Bundle;
import android.os.Debug;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.papaya.cameraxtutorial.R;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

import javax.net.ssl.HttpsURLConnection;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public class Login extends AppCompatActivity {
    String appID = "fitable-lscah";
    EditText emailTxtField;
    EditText pwdTxtField;
    String email, pwd;
    String[] params = {};
    String jwt;
    App app;
    @Override
    protected void onCreate(Bundle savedBundleInstance) {

        super.onCreate(savedBundleInstance);
        setContentView(R.layout.activity_login);
        Realm.init(this);
        app = new App(new AppConfiguration.Builder(appID).build());
        Button loginBtn = findViewById(R.id.loginBtn);
        emailTxtField = findViewById(R.id.emailTxtField);
        pwdTxtField = findViewById(R.id.passTxtField);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailTxtField != null && pwdTxtField != null) {
                    email = emailTxtField.getText().toString();
                    pwd = pwdTxtField.getText().toString();
                    postLogin(email, pwd);
                }
            }
        });



    }

    public void postLogin(String email, String pwd) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://rest.cosync.net/api/appuser/login");
                    HttpsURLConnection urlCon = (HttpsURLConnection) url.openConnection();
                    urlCon.setRequestMethod("POST");
                    urlCon.setRequestProperty("app-token", "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImM4MjJmNDU2ZTg4NzQ2MWJiNjQyMGNmNzc2ZTU5ZjA3Iiwic2NvcGUiOiJhcHAiLCJpYXQiOjE2NDg0NTUyOTl9.bq4bI00jcgDmWkxBfAYqZDq7yV4h7Z8vdHc3DHPpag12HfzSNgaezkLt8qPUXxosN2ryxlTN1BnVXQbbA-_BhAG4QQY6w6Ga8g6nmcfzJplBPKESydoNR5U3c8o6Ok-6VNnCMJnpusd3GbnfNq3VPXirW_BcoyUH8o9r4HeD2aUaGOXThuyT0rgOWu5xLfOIRIMnv_Gixp-VkKXZxlWUqkjGdvv8umJodhI664DU5lYLgedks-OC8cSa2CXo5P3UXokztK6lhRvre3JFGV-kp4Uen0W0kXm5FNlOb5zAZG8QWJoxWS4brvutIgD29vBDvZ0qUSGMb6qkUZmKhgSsc1xADmZBPR90VWMRRxINdozSfniEaTrlPTP9TJmsL_WfuqGK_4AwgiysQ2tuIKBIHakPYdl_kOfaiEWGvDK81YLgSmMg-XsQYh2PdssqbzjT8mX1JxXNF0hcBJ8MaUQfOsEov3r20FuhJhtNXb9zYlgMwwE9U0y08fgL7k_p5A8l");
                    urlCon.setDoOutput(true);
                    urlCon.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("handle", email);
                    jsonParam.put("password", pwd);
                    Log.i("COSYNC", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(urlCon.getOutputStream());
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();


                    if (urlCon.getResponseCode() == 200) {
                        InputStream in = urlCon.getInputStream();
                        InputStreamReader isw = new InputStreamReader(in);
                        JsonReader jsonReader = new JsonReader(isw);
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            String key = jsonReader.nextName();
                            if (key.equals("jwt")) {
                                String value = jsonReader.nextString();
                                jwt = value;
                            } else {
                                jsonReader.skipValue();
                            }
                        }
                        jsonReader.close();
                        realmLogin();

                    } else {
                        Log.e("COSYNC", urlCon.getResponseMessage());
                        Log.e("COSYNC", String.valueOf(urlCon.getResponseCode()));
                        InputStream in = urlCon.getInputStream();
                        InputStreamReader isw = new InputStreamReader(in);
                        JsonReader jsonReader = new JsonReader(isw);
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            Log.e("COSYNC", jsonReader.nextString());
                        }
                        jsonReader.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        thread.start();
    }

    public void realmLogin() {
        Log.v("JWT", jwt);
        try {
            if (jwt != null) {
                Credentials customJWTCredentials = Credentials.jwt(jwt);
                AtomicReference<User> user = new AtomicReference<User>();
                app.loginAsync(customJWTCredentials, it -> {
                    if (it.isSuccess()) {
                        Log.v("AUTH", "Login successfully with JWT");
                        user.set(app.currentUser());
                    } else {
                        Log.e("AUTH", it.getError().toString());
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

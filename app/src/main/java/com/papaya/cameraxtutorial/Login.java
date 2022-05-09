package com.papaya.cameraxtutorial;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.papaya.cameraxtutorial.R;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicReference;

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
    @Override
    protected void onCreate(Bundle savedBundleInstance) {

        super.onCreate(savedBundleInstance);
        setContentView(R.layout.activity_login);
        Realm.init(this);
        App app = new App(new AppConfiguration.Builder(appID).build());
        Button loginBtn = findViewById(R.id.loginBtn);
        emailTxtField = findViewById(R.id.emailTxtField);
        pwdTxtField = findViewById(R.id.passTxtField);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginTask loginTask = new LoginTask();
                if (emailTxtField != null && pwdTxtField != null) {
                    email = emailTxtField.getText().toString();
                    pwd = pwdTxtField.getText().toString();
                    params = new String[]{email, pwd};
                }

                jwt = loginTask.execute(params).toString();
                Log.v("JWT", jwt);
                try {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



    }
}

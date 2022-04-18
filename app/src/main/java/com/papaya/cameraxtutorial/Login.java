package com.papaya.cameraxtutorial;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public class Login extends AppCompatActivity {
    String appID = "fitable-lscah";
    @Override
    protected void onCreate(Bundle savedBundleInstance) {

        super.onCreate(savedBundleInstance);
        App app = new App(new AppConfiguration.Builder(appID).build());

        Credentials customJWTCredentials = Credentials.jwt("<token>");

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
}

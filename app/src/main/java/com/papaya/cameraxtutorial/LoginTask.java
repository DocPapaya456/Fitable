package com.papaya.cameraxtutorial;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class LoginTask extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... strings) {
        String email, pwd;
        try {
            URL url;
            HttpsURLConnection urlCon = null;

            try {
                url = new URL("https://rest.cosync.net/api/appuser/login");
                urlCon = (HttpsURLConnection) url.openConnection();
                urlCon.setRequestMethod("POST");
                urlCon.setRequestProperty("app-token", "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImM4MjJmNDU2ZTg4NzQ2MWJiNjQyMGNmNzc2ZTU5ZjA3Iiwic2NvcGUiOiJhcHAiLCJpYXQiOjE2NDg0NTUyOTl9.bq4bI00jcgDmWkxBfAYqZDq7yV4h7Z8vdHc3DHPpag12HfzSNgaezkLt8qPUXxosN2ryxlTN1BnVXQbbA-_BhAG4QQY6w6Ga8g6nmcfzJplBPKESydoNR5U3c8o6Ok-6VNnCMJnpusd3GbnfNq3VPXirW_BcoyUH8o9r4HeD2aUaGOXThuyT0rgOWu5xLfOIRIMnv_Gixp-VkKXZxlWUqkjGdvv8umJodhI664DU5lYLgedks-OC8cSa2CXo5P3UXokztK6lhRvre3JFGV-kp4Uen0W0kXm5FNlOb5zAZG8QWJoxWS4brvutIgD29vBDvZ0qUSGMb6qkUZmKhgSsc1xADmZBPR90VWMRRxINdozSfniEaTrlPTP9TJmsL_WfuqGK_4AwgiysQ2tuIKBIHakPYdl_kOfaiEWGvDK81YLgSmMg-XsQYh2PdssqbzjT8mX1JxXNF0hcBJ8MaUQfOsEov3r20FuhJhtNXb9zYlgMwwE9U0y08fgL7k_p5A8l");
                email = strings[0];
                pwd = strings[1];
                String emailData = "handle: " + email;
                String pwdData = "password: " + pwd;
                urlCon.setDoOutput(true);
                urlCon.getOutputStream().write(emailData.getBytes());
                urlCon.getOutputStream().write(pwdData.getBytes());
                if (urlCon.getResponseCode() == 200) {
                    InputStream in = urlCon.getInputStream();
                    InputStreamReader isw = new InputStreamReader(in);
                    JsonReader jsonReader = new JsonReader(isw);
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        String key = jsonReader.nextName();
                        if (key.equals("jwt")) {
                            String value = jsonReader.nextString();
                            return value;
                        } else {
                            jsonReader.skipValue();
                        }
                    }
                } else {
                    Log.e("COSYNC", urlCon.getResponseMessage());
                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlCon != null) {
                    urlCon.disconnect();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}


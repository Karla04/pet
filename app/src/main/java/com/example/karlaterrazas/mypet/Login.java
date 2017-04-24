package com.example.karlaterrazas.mypet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Login extends Activity implements OnClickListener {

    private EditText user, pass;
    private Button mSubmit, mRegister;

    private ProgressDialog pDialog;

    // Clase JSONParser
    JSONParser jsonParser = new JSONParser();


    //private static final String LOGIN_URL = "http://192.168.1.68/cas/login.php";

    private static final String LOGIN_URL = "http://130.100.4.87/cas/login.php";

    // La respuesta del JSON es
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        setTitle("MyPet");


        // setup input fields
        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);

        // setup buttons
        mSubmit = (Button) findViewById(R.id.login);
        mRegister = (Button) findViewById(R.id.register);

        // register listeners
        mSubmit.setOnClickListener(this);
        mRegister.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.login:
                new AttemptLogin().execute();
                break;
            case R.id.register:
                Intent i = new Intent(this, Register.class);
                startActivity(i);
                break;

            default:
                break;
        }
    }

    class AttemptLogin extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Conectando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            int success;
            String username = user.getText().toString();
            String password = pass.getText().toString();
            try {
                // Building Parameters
                List params = new ArrayList();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST",
                        params);

                // check your log for json response
                Log.d("Iniciar sesión", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login correcto", json.toString());
                    // save user data
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(Login.this);
                    Editor edit = sp.edit();
                    edit.putString("username", username);
                    edit.commit();

                    Intent i = new Intent(Login.this, WelcomeActivity.class);
                    finish();
                    startActivity(i);
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Login incorrecto", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(Login.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }
}
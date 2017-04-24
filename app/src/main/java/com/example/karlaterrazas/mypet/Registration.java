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

public class Registration extends Activity implements OnClickListener {

    private EditText user, pass;
    private Button mSubmit, mRegister;

    private ProgressDialog pDialog;

    // Clase JSONParser
    JSONParser jsonParser = new JSONParser();


    // si trabajan de manera local "localhost" :
    // En windows tienen que ir, run CMD > ipconfig
    // buscar su IP
    // y poner de la siguiente manera
    // "http://xxx.xxx.x.x:1234/cas/login.php";

    private static final String LOGIN_URL = "http://basededatosremotas.meximas.com/cas/login.php";

    // La respuesta del JSON es
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // setup input fields
        user = (EditText) findViewById(R.id.et_email);
        pass = (EditText) findViewById(R.id.et_password);

        // setup buttons
        mSubmit = (Button) findViewById(R.id.login);
        mRegister = (Button) findViewById(R.id.btn_registrar);

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
            case R.id.btn_registrar:
                Intent i = new Intent(this, WelcomeActivity.class);
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
            pDialog = new ProgressDialog(Registration.this);
            pDialog.setMessage("Attempting login...");
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
                Log.d("Login attempt", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login Successful!", json.toString());
                    // save user data
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(Registration.this);
                    Editor edit = sp.edit();
                    edit.putString("username", username);
                    edit.commit();

                    Intent i = new Intent(Registration.this, WelcomeActivity.class);
                    finish();
                    startActivity(i);
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
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
                Toast.makeText(Registration.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }
}

/*import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    EditText et_nombre;
    EditText et_apellido_p;
    EditText et_apellido_m;
    EditText et_correo_e;
    EditText et_contraseña;
    EditText et_confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Regístrate en MyPet");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_registration);

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_apellido_p = (EditText) findViewById(R.id.et_apellido_p);
        et_apellido_m = (EditText) findViewById(R.id.et_apellido_m);
        et_correo_e = (EditText) findViewById(R.id.et_correo_e);
        et_contraseña = (EditText) findViewById(R.id.et_contraseña);

    }

    public void registrar(View view){

        DBHelper admin=new DBHelper(this,"instituto",null,1);
        SQLiteDatabase db=admin.getWritableDatabase();
        String nombre=et_nombre.getText().toString();
        String apellido_paterno=et_apellido_p.getText().toString();
        String apellido_materno=et_apellido_m.getText().toString();
        String correo=et_correo_e.getText().toString();
        String contrasena=et_apellido_p.getText().toString();

        ContentValues values=new ContentValues();
        values.put("codigo",nombre);
        values.put("apellido_paterno",apellido_paterno);
        values.put("apellido_materno",apellido_materno);
        values.put("correo",correo);
        values.put("contrasena",contrasena);

        db.insert("usuarios",null,values);
        db.close();

        Intent ven=new Intent(this,WelcomeActivity.class);
        String nom = et_nombre.getText().toString();
        ven.putExtra("nombre", nom);
        startActivity(ven);

    }

    public void welcome_intent() {
        Intent intent = new Intent(Registration.this, WelcomeActivity.class);
        String nombre = et_nombre.getText().toString();
        intent.putExtra("nombre", nombre);
        startActivity(intent);
        finish();
    }
}

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Back();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Back() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }



}

/*
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    EditText et_nombre;
    EditText et_apellido_p;
    EditText et_apellido_m;
    EditText et_correo_e;
    EditText et_contraseña;
    EditText et_confirmar;
    Button btn_registrar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setTitle("Regístrate en MyPet");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_apellido_p = (EditText) findViewById(R.id.et_apellido_p);
        et_apellido_m = (EditText) findViewById(R.id.et_apellido_m);
        et_correo_e = (EditText) findViewById(R.id.et_correo_e);
        et_contraseña = (EditText) findViewById(R.id.et_contraseña);
        et_confirmar = (EditText) findViewById(R.id.et_confirmar);
        btn_registrar = (Button) findViewById(R.id.btn_registrar);
        btn_registrar.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Back();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Back() {
        Intent intent = new Intent(Registration.this, StartActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClick(View v) {
        if (v == btn_registrar)
            btn_registar_OnClick();
    };

    public void welcome_intent() {
        Intent intent = new Intent(Registration.this, WelcomeActivity.class);
        String nombre = et_nombre.getText().toString();
        intent.putExtra("nombre", nombre);
        startActivity(intent);
        finish();
    }

    public void user_intent() {
        Intent intent = new Intent(Registration.this, MainActivity.class);
        String nombre = et_nombre.getText().toString();
        String apellido_paterno = et_apellido_p.getText().toString();
        String apellido_materno = et_apellido_m.getText().toString();
        String correo_electronico = et_correo_e.getText().toString();
        intent.putExtra("nombre", nombre);
        intent.putExtra("apellido_paterno", apellido_paterno);
        intent.putExtra("apellido_materno", apellido_materno);
        intent.putExtra("correo_electronico", correo_electronico);
        startActivity(intent);
        finish();
    }


    public void btn_registar_OnClick() {
        welcome_intent();
        user_intent();
        finish();
    }
}*/

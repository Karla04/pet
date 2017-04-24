package com.example.karlaterrazas.mypet;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class LoginActivity extends Activity implements OnClickListener{
    EditText user, pass;
    private Button  mRegister;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    private static final String REGISTER_URL = "http://basededatosremotas.meximas.com/cas/register.php";

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (EditText)findViewById(R.id.et_email);
        pass = (EditText)findViewById(R.id.et_password);


        mRegister = (Button)findViewById(R.id.email_sign_in_button);
        mRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        new CreateUser().execute();

    }

    class CreateUser extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Creating User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            String username = user.getText().toString();
            String password = pass.getText().toString();
            try {
                // Building Parameters
                List params = new ArrayList();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");

                //Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(
                        REGISTER_URL, "POST", params);

                // full json response
                Log.d("Registering attempt", json.toString());

                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("User Created!", json.toString());
                    finish();
                    return json.getString(TAG_MESSAGE);
                }else{
                    Log.d("Registering Failure!", json.getString(TAG_MESSAGE));
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
            if (file_url != null){
                Toast.makeText(LoginActivity.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }
}
/*import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText et_email;
    EditText et_password;
    private Cursor fila;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    public void ingresar(View v){
        DBHelper admin=new DBHelper(this,"instituto",null,1);
        SQLiteDatabase db=admin.getWritableDatabase();
        String email=et_email.getText().toString();
        String contrasena=et_password.getText().toString();
        fila=db.rawQuery("select correo,contrasena from usuarios where correo='"+email+"' and contrasena='"+contrasena+"'",null);

        if (fila.moveToFirst()){
            String em=fila.getString(0);
            String pass=fila.getString(1);
            if (email.equals(em)&&contrasena.equals(pass)){
                Intent ven=new Intent(this,MainActivity.class);
                startActivity(ven);
                et_email.setText("");
                et_password.setText("");
            }

        }



    }
    public void registro(View v){
        Intent ven=new Intent(this,Registration.class);
        startActivity(ven);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //onBackPressed();}
                Back();
                return true;
        }
        return super.onOptionsItemSelected(item);
    };

    public void Back(){
        Intent intent=new Intent(LoginActivity.this,StartActivity.class);
        startActivity(intent);
        finish();
    }
}


/*

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>, OnClickListener {

    */
/**
     * Credenciales de pruebas
     *
     *//*

    private static final String DUMMY_EMAIL = "@utec";
    private static final String DUMMY_PASSWORD = "mypet";

    */
/**
     * Keep track of the login task to ensure we can cancel it if requested.
     *//*

    //Simula una espera de n milsegundos la petición del servidor.
    private UserLoginTask mAuthTask = null; //Comprueba que no se hayan enviado datos aún

    // UI references.
    private ImageView mLogoView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private TextInputLayout mFloatLabelEmail;
    private TextInputLayout mFloatLabelPassword;
    private View mProgressView;
    private View mLoginFormView;
    Button btn_olvidaste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Referencias de los views declarados
        mLogoView = (ImageView) findViewById(R.id.image_logo);
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mFloatLabelEmail = (TextInputLayout) findViewById(R.id.float_label_email);
        mFloatLabelPassword = (TextInputLayout) findViewById(R.id.float_label_password);
        Button mSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        btn_olvidaste = (Button) findViewById(R.id.btn_olvidaste);
        btn_olvidaste.setOnClickListener(this);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);


        // Edición del campo de texto para password
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));

            }
        });
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors. (Restablece errores)
        mFloatLabelEmail.setError(null);
        mFloatLabelPassword.setError(null);

        // Store values at the time of the login attempt. (Obtiene los datos del usuario como variables)
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mFloatLabelPassword.setError(getString(R.string.error_field_required));
            focusView = mFloatLabelPassword;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mFloatLabelPassword.setError(getString(R.string.error_invalid_password));
            focusView = mFloatLabelPassword;
            cancel = true;
        }

        // Verificar si el campo tiene contenido.
        if (TextUtils.isEmpty(email)) {
            mFloatLabelEmail.setError(getString(R.string.error_field_required));
            focusView = mFloatLabelEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mFloatLabelEmail.setError(getString(R.string.error_invalid_email));
            focusView = mFloatLabelEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt. (Sincronia)
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }

    }

    private void showProgress(boolean show) {
        //Muestra la barra de progreso
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        //Oculta el formulario
        int visibility = show ? View.GONE : View.VISIBLE;
        mLogoView.setVisibility(visibility);
        mLoginFormView.setVisibility(visibility);
    }

    private boolean isEmailValid(String email) {
        return email.length() == 30; //Tamaño exactamente igual a 30
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4; //Tamaño no inferior o igual a 4
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(LoginActivity.this, RecoveryActivity.class);
        startActivity(intent);
        finish();
    }

    private class UserLoginTask extends AsyncTask<Void, Void, Integer> {
        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override

        protected Integer doInBackground(Void... params) {

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return 1;
            }

            if (!mEmail.equals(DUMMY_EMAIL)) {
                return 1;
            }

            if (!mPassword.equals(DUMMY_PASSWORD)) {
                return 1;
            }

            return 1;

        }

        @Override
        protected void onPostExecute(final Integer success) {
            mAuthTask = null;
            showProgress(false);

            switch (success) {
                case 1:
                    showAppointmentsScreen();
                    break;
                case 2:
                    showLoginError("Correo electrónico o contraseña inválidos");
                    break;
            }
        }

        private void showAppointmentsScreen() {
        }

        private void showLoginError(String error) {
            Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //onBackPressed();}
                Back();
            return true;
        }
        return super.onOptionsItemSelected(item);
    };

    public void Back(){
        Intent intent=new Intent(LoginActivity.this,StartActivity.class);
        startActivity(intent);
        finish();
        }
    }*/

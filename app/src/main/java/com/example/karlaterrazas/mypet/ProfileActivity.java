package com.example.karlaterrazas.mypet;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfileActivity extends AppCompatActivity {

    private ListView lv_pet_list;
    private ArrayAdapter adapter;
    //private String getPetsURL = "http://192.168.1.68/mypet/getPet.php";
    //private String getPetsURL = "http://192.168.1.215/mypet/getPet.php";
    private String getPetsURL = "http://130.100.4.87/mypet/getPet.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle("Perfil");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        lv_pet_list = (ListView) findViewById(R.id.lv_pet_list);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        lv_pet_list.setAdapter(adapter);

        webServiceRest(getPetsURL);
    }

    private void webServiceRest(String requestURL) {
        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult = "";
            while ((line = bufferedReader.readLine()) != null) {
                webServiceResult += line;
            }
            bufferedReader.close();
            parseInformation(webServiceResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseInformation(String jsonResult) {
        JSONArray jsonArray = null;
        String id_mascota;
        String tipo_mascota;
        String nombre;
        String sexo;
        String fecha_nacimiento;
        String foto;
        String id_usuario;
        try {
            jsonArray = new JSONArray(jsonResult);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                tipo_mascota = jsonObject.getString("tipo_mascota");
                nombre = jsonObject.getString("nombre");
                sexo = jsonObject.getString("sexo");
                fecha_nacimiento = jsonObject.getString("fecha_nacimiento");
                adapter.add("\n" + "\n" + "Tipo mascosta: "+ tipo_mascota  + "\n" + "\n" + "Nombre: "
                        + nombre + "\n" + "\n" + "Sexo: " + sexo + "\n" + "\n" + "Fecha de nacimiento: "
                        + fecha_nacimiento + "\n" + "\n");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
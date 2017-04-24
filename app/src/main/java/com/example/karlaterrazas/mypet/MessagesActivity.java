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

public class MessagesActivity extends AppCompatActivity {

    private ListView lv_mes_list;
    private ArrayAdapter adapter;
    //private String getMessURL = "http://192.168.1.68/mypet/getMess.php";
    //private String getMessURL = "http://192.168.1.215/mypet/getMess.php";
    private String getMessURL = "http://130.100.4.87/mypet/getMess.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        setTitle("Mensajes");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        lv_mes_list = (ListView) findViewById(R.id.lv_mes_list);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        lv_mes_list.setAdapter(adapter);

        webServiceRest(getMessURL);
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
        String id_mensaje;
        String mensaje;
        String fecha;
        String id_usuario;
        try {
            jsonArray = new JSONArray(jsonResult);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                fecha = jsonObject.getString("fecha");
                mensaje = jsonObject.getString("mensaje");
                adapter.add("\n" + "\n" + "Fecha: " + fecha + "\n" + "\n" + mensaje + "\n" + "\n");
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
                // onBackPressed();}
                Back();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Back() {
        Intent intent = new Intent(MessagesActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
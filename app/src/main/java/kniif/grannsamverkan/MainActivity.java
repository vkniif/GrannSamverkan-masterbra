package kniif.grannsamverkan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class MainActivity extends AppCompatActivity {
    //Hämta ett unikt namn för att identifiera vår LOG
    String TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Url som tänker anropas
        String url = "https://www.google.se/";
        //Initiera StringRequesten
        //Konstruktorn plockar med en url, en respons lyssnare
        // OCh en som lyssnar efter felmeddelanden
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Skriv ut den respons som nås av urlen vi anropar
                Log.d(TAG, response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Felmeddelande om vi inte får kontakt med urlen
                Toast.makeText(getApplicationContext(), "Error while reading",
                        Toast.LENGTH_SHORT).show();

            }
        }
        );

        //Lägger in en request i detta fall stringRequest till Requestkön
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    public void ActMap1 (View view){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);}


}

package com.example.fazazi.muslimprayer.Request;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.layali.codedreamer.muslim.Models.Sourat.Sourat;
import com.layali.codedreamer.muslim.recitator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;


public class SouratRequest {

    public interface RecitatorInteface {
        void onSuccess(ArrayList<Sourat> sourats);

        void onError(String message);
    }

    private RequestQueue queue;
    private static final String TAG = "APP";


    public SouratRequest(RequestQueue queue) {
        this.queue = queue;
    }

    public void getSongList(final RecitatorInteface callback) {
        String lan = Locale.getDefault().getLanguage();
        String URL = "http://api.islamhouse.com/v1/lxoPK9Obr8vqoLcZ/quran/get-recitation/" + recitator.selectedRecitator + "/ar/json/";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: " + response);
                ArrayList<Sourat> Sourats = new ArrayList<>();
                JSONArray jsonArray = null;
                String f = "";

                try {
                    jsonArray = response.getJSONArray("attachments");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jsonArray.length() > 0 && null != jsonArray) {
                    int u = jsonArray.length();
                    for (int i = 0; i < u; i++) {
                        try {
                            JSONObject songObject = jsonArray.getJSONObject(i);
                            String id = songObject.getString("id");
                            String title = songObject.getString("title");
                            String url = songObject.getString("url");
                            Double duration = songObject.getDouble("duration");

                            Sourat sourat = new Sourat(id, title, i + "", duration, url);
                            Sourats.add(sourat);

                        } catch (JSONException e) {
                            Log.d(TAG, "onResponse: " + e.getMessage());
                            callback.onError("Une erreur s'est produite" + e.getMessage());
                            e.printStackTrace();
                        }
                    }

                    callback.onSuccess(Sourats);

                } else {
                    callback.onError("Aucune chanson trouvée");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: " + error.getMessage());
                callback.onError("Une erreur s'est produite");
            }
        });

        queue.add(request);

    }
}
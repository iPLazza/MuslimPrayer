package com.example.fazazi.muslimprayer.Request;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.layali.codedreamer.muslim.Models.recitator.Recitator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;


public class RecitatorRequest {

    public interface RecitatorInteface {
        void onSuccess(ArrayList<Recitator> songs);

        void onError(String message);
    }

    private RequestQueue queue;
    private static final String TAG = "APP";


    public RecitatorRequest(RequestQueue queue) {
        this.queue = queue;
    }

    public void getSongList(final RecitatorInteface callback) {
        String lan = Locale.getDefault().getLanguage();
        String URL = "http://api.islamhouse.com/v1/lxoPK9Obr8vqoLcZ/quran/get-category/364794/ar/json/";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: " + response);
                ArrayList<Recitator> Recitator = new ArrayList<>();
                JSONArray jsonArray = null;
                String f = "";

                try {
                    jsonArray = response.getJSONArray("authors");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jsonArray.length() > 0 && null != jsonArray) {
                    int u = jsonArray.length();
                    for (int i = 0; i < u; i++) {
                        try {
                            JSONObject songObject = jsonArray.getJSONObject(i);
                            Long id = Long.parseLong(songObject.getString("id"));
                            String title = songObject.getString("title");
                            String url = songObject.getString("api_url");

                            JSONObject recitationInfo = songObject.getJSONObject("recitations_info");
                            JSONArray recitaions = recitationInfo.getJSONArray("recitations_ids");


                            Recitator recitator = new Recitator(title, url, id, (Integer) recitaions.get(0));
                            Recitator.add(recitator);

                        } catch (JSONException e) {
                            Log.d(TAG, "onResponse: " + e.getMessage());
                            callback.onError("Une erreur s'est produite" + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    callback.onSuccess(Recitator);
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
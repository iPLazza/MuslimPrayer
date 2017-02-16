package com.example.fazazi.muslimprayer.Request;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.layali.codedreamer.muslim.Models.recitator.audio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;


public class audioRequest {

    public interface audioInteface {
        void onSuccess(ArrayList<audio> audios);

        void onError(String message);
    }

    private RequestQueue queue;
    private static final String TAG = "APP";


    public audioRequest(RequestQueue queue) {
        this.queue = queue;
    }

    public void getSongList(final audioInteface callback) {
        String lan = Locale.getDefault().getLanguage();
        String URL = "http://api.islamhouse.com/v1/lxoPK9Obr8vqoLcZ/dynamic/ramadan/get-content-by-type/audios/ar/ar/1/100/json";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: " + response);
                ArrayList<audio> audios = new ArrayList<>();
                JSONArray jsonArray = null;
                String f = "";

                try {
                    jsonArray = response.getJSONArray("data");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jsonArray.length() > 0 && null != jsonArray) {
                    int u = jsonArray.length();
                    for (int i = 0; i < u; i++) {
                        try {
                            JSONObject songObject = jsonArray.getJSONObject(i);

                            String title = songObject.getString("title");
                            String description = songObject.getString("description");
                            JSONArray a = songObject.getJSONArray("attachments");
                            String url = a.getJSONObject(0).getString("url");
                            audio audio = new audio(title, description, url);

                            audios.add(audio);

                        } catch (JSONException e) {
                            Log.d(TAG, "onResponse: " + e.getMessage());
                            callback.onError("Une erreur s'est produite" + e.getMessage());
                            e.printStackTrace();
                        }
                    }

                    callback.onSuccess(audios);

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
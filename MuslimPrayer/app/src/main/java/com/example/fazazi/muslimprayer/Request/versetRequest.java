package com.example.fazazi.muslimprayer.Request;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.layali.codedreamer.muslim.Models.recitator.verset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class versetRequest {

    public interface RecitatorInteface {
        void onSuccess(ArrayList<verset> sourats);

        void onError(String message);
    }

    private RequestQueue queue;
    private static final String TAG = "APP";


    public versetRequest(RequestQueue queue) {
        this.queue = queue;
    }

    public void getSongList(final RecitatorInteface callback) {
        String lan = Locale.getDefault().getLanguage();
        String URL = "http://api.globalquran.com/surah/114/quran-simple?key=1ca71837959d04e19d49f8e48517627a";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: " + response);


              ArrayList<verset> Sourats = new ArrayList<>();
                verset sourat = new verset("12", "Fati7a", " قُلْ أَعُوذُ بِرَبِّ النَّاسِ", "41");
                Sourats.add(sourat);
                /*
                try {
                    parseJson(response, Sourats);
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray d = null;*/

                //if (d.length() > 0 && null != d) {
                   /* int u = jsonArray.length();
                    for (int i = 0; i < u; i++) {
                        try {
                            JSONObject songObject = d.getJSONObject(i);
                            String id = songObject.getString("id");
                            String title = songObject.getString("soura");
                            String url = songObject.getString("aya");
                            String duration = songObject.getString("verset");

                            verset sourat = new verset(id, title, url, duration);
                            Sourats.add(sourat);

                        } catch (JSONException e) {
                            Log.d(TAG, "onResponse: " + e.getMessage());
                            callback.onError("Une erreur s'est produite" + e.getMessage());
                            e.printStackTrace();
                        }*/
                //}

                callback.onSuccess(Sourats);

                // } else {
                // callback.onError("Aucune chanson trouvée");
            }
            // }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: " + error.getMessage());
                callback.onError("Une erreur s'est produite");
            }
        });

        queue.add(request);

    }

    public List<verset> parseJson(JSONObject jsonObject, List<verset> versets) throws ParseException, JSONException {

        Iterator<?> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String obj = iterator.next().toString();

            if (jsonObject.get(obj) instanceof JSONArray) {


            } else {
                if (jsonObject.get(obj) instanceof JSONObject) {
                    String id = jsonObject.getString("id");
                    String title = jsonObject.getString("soura");
                    String url = jsonObject.getString("aya");
                    String duration = jsonObject.getString("verset");
                    versets.add(new verset(id, title, url, duration));

                    parseJson((JSONObject) jsonObject.get(obj), versets);
                } else {


                }
            }
        }
        return null;
    }
}
package com.example.fazazi.muslimprayer.Request;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.layali.codedreamer.muslim.Models.PrayTime;
import com.layali.codedreamer.muslim.Utility.GPSTracker;
import com.layali.codedreamer.muslim.preyerTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class prayTimeRequest implements LocationListener {

    private JSONArray preyTimes;
    private String stringLatitude, stringLongitude;
    private Location mLocation;
    private GPSTracker gps;
    private double latitude, longitude;

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public interface prayTimeInterface {
        void onSuccess(PrayTime prayTime);

        void onError(String message);
    }

    private RequestQueue queue;
    private static final String TAG = "APP";


    public prayTimeRequest(RequestQueue queue) {
        this.queue = queue;
    }

    public void getPrayTime(final prayTimeInterface callback) {
        String URL = null;
        try {
            URL = getURL();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: " + response);
                try {
                    preyTimes = response.getJSONArray("times");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (preyTimes.length() > 0) {
                    try {
                        callback.onSuccess(new PrayTime(preyTimes.get(0) + "",
                                preyTimes.get(1) + "",
                                preyTimes.get(2) + "",
                                preyTimes.get(3) + "",
                                preyTimes.get(4) + "",
                                preyTimes.get(6) + ""
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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

    private String getURL() throws IOException {
        gps = new GPSTracker(preyerTime.context);
        DecimalFormat mFormat = new DecimalFormat("00");
        turnOnGps();


        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        } else {
            gps.showSettingsAlert();
        }


        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
                Locale.getDefault());
        Date currentLocalTime = calendar.getTime();
        DateFormat date = new SimpleDateFormat("Z");
        String localTime = date.format(currentLocalTime);


        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        return "http://api.islamhouse.com/v1/lxoPK9Obr8vqoLcZ/services/praytime/get-times/"
                + year + "/" + mFormat.format(Double.valueOf(month)) + "/" +
                mFormat.format(Double.valueOf(day)) + "/MWL/" + latitude + "/" + longitude + "/+" + localTime.charAt(2) + "/json";
    }

    private void turnOnGps() {
        String provider = android.provider.Settings.Secure.getString(preyerTime.context.getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.contains("gps")) {
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            preyerTime.context.sendBroadcast(poke);
        }
    }
}

    /*private void getAdress() throws IOException {
        Geocoder geocoder;
        List<Address> addresses;

        if (ContextCompat.checkSelfPermission(preyerTime.context, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                preyerTime.context.getPackageManager().PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(preyerTime.context, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        preyerTime.context.getPackageManager().PERMISSION_GRANTED) {
            geocoder = new Geocoder(preyerTime.context, Locale.getDefault());

            addresses = geocoder.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            Toast.makeText(preyerTime.context, address + "  " + city + " " + state + " " + country, Toast.LENGTH_SHORT).show();
        } else {

        }

    }*/



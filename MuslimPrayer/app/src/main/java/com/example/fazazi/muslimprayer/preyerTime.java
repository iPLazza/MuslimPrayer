package com.example.fazazi.muslimprayer;


import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.layali.codedreamer.muslim.Models.PrayTime;
import com.layali.codedreamer.muslim.Request.prayTimeRequest;
import com.layali.codedreamer.muslim.Utility.VolleySingleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class preyerTime extends Fragment {
    public static final String NOTIFICATION_SERVICE = "notification";


    public static Context context;
    private LocationManager locationManager;
    private Criteria criteria;
    private String provider;
    private PrayTime globalprayTime;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private TextView choroukValue, FajrValue, DohrValue, AasrValue, MaghrebValue, ichaaValue;
    boolean dayTest = true;
    private TextView hour, minute;
    private TextView second;
    private MediaPlayer mp;


    public preyerTime() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.preyertime, container, false);
        context = view.getContext();
        initialize(view);
        getPrayTime();

        return view;
    }

    private void initialize(View view) {


       // startActivity(getActivity().getIntent(),new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "your message";

        /*builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                getActivity().startActivity(new Intent(action));
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });
        builder.create().show();*/
        mp = MediaPlayer.create(getContext().getApplicationContext(), R.raw.azan);
        choroukValue = (TextView) view.findViewById(R.id.Chorouk);
        FajrValue = (TextView) view.findViewById(R.id.Fajr);
        DohrValue = (TextView) view.findViewById(R.id.Dohr);
        AasrValue = (TextView) view.findViewById(R.id.Alaasr);
        MaghrebValue = (TextView) view.findViewById(R.id.maghreb);
        ichaaValue = (TextView) view.findViewById(R.id.ichaa);
        hour = (TextView) view.findViewById(R.id.tiempo);

    }

    private void getPrayTime() {
        RequestQueue queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
        prayTimeRequest request = new prayTimeRequest(queue);

        request.getPrayTime(new prayTimeRequest.prayTimeInterface() {
            public PrayTime prayTime;

            @Override
            public void onSuccess(PrayTime prayTime) {
                globalprayTime = prayTime;
                if (globalprayTime != null) {
                    choroukValue.setText(getString(R.string.chorouk) + "\n" + globalprayTime.getChorouk());
                    FajrValue.setText(getString(R.string.fajr) + "\n" + globalprayTime.getFajr());
                    DohrValue.setText(getString(R.string.dohr) + "\n" + globalprayTime.getDohr());
                    AasrValue.setText(getString(R.string.asr) + "\n" + globalprayTime.getAsr());
                    MaghrebValue.setText(getString(R.string.maghreb) + "\n" + globalprayTime.getMaghreb());
                    ichaaValue.setText(getString(R.string.ichaa) + "\n" + globalprayTime.getIchae());

                    getMinumumTime(globalprayTime.getAsr());

                    new CountDownTimer(10000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            String time = String.format("%d:%d:%d",
                                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                            );
                            hour.setText(time);

                        }

                        public void onFinish() {
                            try {
                                mp.start();
                                notifyPlayer("Dohr");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }.start();
                }
                //    Toast.makeText(getActivity(), prayTime.getMaghreb() + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message) {

            }
        });
    }


    private void getMinumumTime(String... Times) {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);


        StringTokenizer Time = new StringTokenizer(Times[0], ":");
        int Hour = Integer.parseInt(Time.nextToken());
        int Minutes = Integer.parseInt(Time.nextToken());

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        Date date = null;

        try {
            date = sdf.parse(Times[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String formattedTime = sdf.format(date);
        System.out.println(formattedTime);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();


        String provider = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (provider.contains("gps")) { //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            this.getContext().sendBroadcast(poke);
        }
    }


    private void notifyPlayer(String prayTime) {
        final NotificationManager mNotification =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        final Intent launchNotifiactionIntent = new Intent(getContext(), preyerTime.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),
                2, launchNotifiactionIntent,
                PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder builder = new Notification.Builder(getContext())
                .setWhen(System.currentTimeMillis())
                .setTicker(prayTime).setContentTitle(prayTime)
                .setSmallIcon(R.drawable.ic_scratch)
                .setContentTitle(prayTime).
                        addAction(R.drawable.ic_play_circle_outline_black, "Play", pendingIntent).
                        addAction(R.drawable.ic_pause_circle_outline_black, "Pause", pendingIntent)
                .setContentText(prayTime)
                .setContentIntent(pendingIntent);

        Notification notification = new Notification.BigPictureStyle(builder)
                .bigPicture(BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_scratch)).build();

        mNotification.notify(562, notification);
        // Get RemoteView and id's needed
        final RemoteViews contentView = notification.bigContentView;
        final int iconId = android.R.id.icon1;


        //Picasso.with(getContext()).load(art).into(contentView, iconId, 562, notification);
    }
}


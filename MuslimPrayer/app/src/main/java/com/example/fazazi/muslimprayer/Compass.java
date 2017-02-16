package com.example.fazazi.muslimprayer;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Compass extends Fragment implements SensorEventListener{

    private ImageView image;
    private float currentDegree = 0f;
    private SensorManager mSensorManager;
    private TextView tvHeading;
    Context Context;

    public Compass() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compass, container, false);
        image = (ImageView) view.findViewById(R.id.imageViewCompass);
        tvHeading = (TextView) view.findViewById(R.id.tvHeading);
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        /*String currentTime = DateFormat.getTimeInstance().format(new Date());
        Intent intent = new Intent();
        PendingIntent pIntent = PendingIntent.getActivity(this.getActivity(), 0, intent, 0);

        Notification noti = new Notification.Builder(this.getActivity())
                .setTicker("Ticker Title")
                .setContentTitle("Content Title")
                .setContentText("Notification content.")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pIntent).getNotification();
        noti.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) Context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);*/



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);
        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");
        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        // how long the animation will take place
        ra.setDuration(210);
        // set the animation after the end of the reservation status
        ra.setFillAfter(true);
        // Start the animation
        image.startAnimation(ra);
        currentDegree = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

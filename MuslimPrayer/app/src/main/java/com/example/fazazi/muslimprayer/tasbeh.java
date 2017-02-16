package com.example.fazazi.muslimprayer;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class tasbeh extends Fragment implements View.OnClickListener{

    private ImageView img,refresh;
    private TextView number;
    private int i=0;
    private View trait;
    private Animation animation;
    private View view;
    private FragmentActivity activity;
    private View alphaColorView;

    public tasbeh() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tasbeh, container, false);
        alphaColorView = (View)view.findViewById(R.id.alphaColorView);
        alphaColorView.setBackgroundColor(getColorWithAlpha(Color.BLACK, 0.2f));
        activity = this.getActivity();
        number = (TextView) view.findViewById(R.id.number);
        refresh = (ImageView) view.findViewById(R.id.lblrefresh);

        refresh.setOnClickListener(this);

        img = (ImageView) view.findViewById(R.id.imageView);
        animation = AnimationUtils.loadAnimation(activity, R.anim.animation);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(animation);
                i+=1;
                number.setText(String.valueOf(i));
                if(i>100){
                    number.setText(String.valueOf("0"));
                    i=0;
                }

                if(i>=33&&i<=65){
                    number.setTextColor(Color.parseColor("#6ea625"));
                }
                if(i==33){
                    Toast.makeText(getActivity(), "HAMD" + "", Toast.LENGTH_SHORT).show();
                }
                if(i>=66&&i<=100){
                    number.setTextColor(Color.parseColor("#de3136"));
                }
                if(i==66){
                    Toast.makeText(getActivity(), "TASBIH" + "", Toast.LENGTH_SHORT).show();
                }
                if(i>=0&&i<=32){
                    number.setTextColor(Color.parseColor("#737373"));
                }
                if(i==100){
                    Toast.makeText(getActivity(), "TAKBIR" + "", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    public void onClickBall(View v) {
        //img.startAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lblrefresh:
                number.setText(String.valueOf("0"));
                i=0;
                break;
        }
    }

    public static int getColorWithAlpha(int color, float ratio) {
        int newColor = 0;
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }
}



package com.example.fazazi.muslimprayer;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    private TextView currentDate, currentHijriDate;

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;
    TabHost tabHost;

    private Drawable oldBackground = null;
    private int currentColor = 0xFF666666;

    public Home() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the ViewPager and set an adapter

        tabs = (PagerSlidingTabStrip) v.findViewById(R.id.tabs);
        pager = (ViewPager) v.findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());

        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);

        /*tabHost = (TabHost) v.findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("Recitator");
        tabSpec.setContent(R.id.recitateur);
        tabSpec.setIndicator("Recitator");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Ayat");
        tabSpec.setContent(R.id.ayat);
        tabSpec.setIndicator("Ayat");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Sourates");
        tabSpec.setContent(R.id.sourates);
        tabSpec.setIndicator("Sourates");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Livres");
        tabSpec.setContent(R.id.livres);
        tabSpec.setIndicator("Livres");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Audio");
        tabSpec.setContent(R.id.audio);
        tabSpec.setIndicator("Audio");
        tabHost.addTab(tabSpec);


        String currentDateString = DateFormat.getDateInstance().format(new Date());
        currentDate = (TextView)v.findViewById(R.id.currentDate);
        currentHijriDate = (TextView)v.findViewById(R.id.currentHijriDate);
        currentDate.setText(currentDateString);

        String[] iMonthNames = {getString(R.string.moharam), getString(R.string.safar), getString(R.string.rabia1),
                getString(R.string.rabia2), getString(R.string.joumada1), getString(R.string.joumada2), getString(R.string.rajab),
                getString(R.string.chaabane), getString(R.string.ramadan), getString(R.string.chawwal), getString(R.string.qida), getString(R.string.hijja) };
        // This Value is used to give the correct day +- 1 day
        boolean dayTest=true;
        double[] iDate = kuwaiticalendar(dayTest);
        String outputIslamicDate = (int)iDate[5] + " "
                + iMonthNames[(int) iDate[6]] + " " + (int)iDate[7];
        currentHijriDate.setText(String.valueOf(outputIslamicDate));*/

        return v;
    }

    static double gmod(double n, double m) {
        return ((n % m) + m) % m;
    }

    static double[] kuwaiticalendar(boolean adjust) {
        Calendar today = Calendar.getInstance();
        int adj = 0;
        if (adjust) {
            adj = 0;
        } else {
            adj = 1;
        }

        if (adjust) {
            int adjustmili = 1000 * 60 * 60 * 24 * adj;
            long todaymili = today.getTimeInMillis() + adjustmili;
            today.setTimeInMillis(todaymili);
        }
        double day = today.get(Calendar.DAY_OF_MONTH);
        double month = today.get(Calendar.MONTH);
        double year = today.get(Calendar.YEAR);

        double m = month + 1;
        double y = year;
        if (m < 3) {
            y -= 1;
            m += 12;
        }

        double a = Math.floor(y / 100.);
        double b = 2 - a + Math.floor(a / 4.);

        if (y < 1583)
            b = 0;
        if (y == 1582) {
            if (m > 10)
                b = -10;
            if (m == 10) {
                b = 0;
                if (day > 4)
                    b = -10;
            }
        }

        double jd = Math.floor(365.25 * (y + 4716)) + Math.floor(30.6001 * (m + 1)) + day
                + b - 1524;

        b = 0;
        if (jd > 2299160) {
            a = Math.floor((jd - 1867216.25) / 36524.25);
            b = 1 + a - Math.floor(a / 4.);
        }
        double bb = jd + b + 1524;
        double cc = Math.floor((bb - 122.1) / 365.25);
        double dd = Math.floor(365.25 * cc);
        double ee = Math.floor((bb - dd) / 30.6001);
        day = (bb - dd) - Math.floor(30.6001 * ee);
        month = ee - 1;
        if (ee > 13) {
            cc += 1;
            month = ee - 13;
        }
        year = cc - 4716;

        double wd = gmod(jd + 1, 7) + 1;

        double iyear = 10631. / 30.;
        double epochastro = 1948084;
        double epochcivil = 1948085;

        double shift1 = 8.01 / 60.;

        double z = jd - epochastro;
        double cyc = Math.floor(z / 10631.);
        z = z - 10631 * cyc;
        double j = Math.floor((z - shift1) / iyear);
        double iy = 30 * cyc + j;
        z = z - Math.floor(j * iyear + shift1);
        double im = Math.floor((z + 28.5001) / 29.5);
        if (im == 13)
            im = 12;
        double id = z - Math.floor(29.5001 * im - 29);

        double[] myRes = new double[8];

        myRes[0] = day; // calculated day (CE)
        myRes[1] = month - 1; // calculated month (CE)
        myRes[2] = year; // calculated year (CE)
        myRes[3] = jd - 1; // julian day number
        myRes[4] = wd - 1; // weekday number
        myRes[5] = id; // islamic date
        myRes[6] = im - 1; // islamic month
        myRes[7] = iy; // islamic year

        return myRes;
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
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"RECITATOR", "AYAT", "SOURATES", "LIVRES", "AUDIO"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            return SuperAwesomeCardFragment.newInstance(position);
        }

    }
}

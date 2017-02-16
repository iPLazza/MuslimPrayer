package com.example.fazazi.muslimprayer;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.layali.codedreamer.muslim.Adapters.RecitatorAdapter;
import com.layali.codedreamer.muslim.Dao.RecitateurDao;
import com.layali.codedreamer.muslim.Models.recitator.Recitator;
import com.layali.codedreamer.muslim.Request.RecitatorRequest;
import com.layali.codedreamer.muslim.Utility.VolleySingleton;

import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class recitator extends Fragment {

    static final int dialog_id = 1;
    RecitateurDao mydb;
    private ImageView favorite;
    private TextView tv_title;
    private ArrayList<Recitator> recitatorList;
    private RecyclerView recycler;
    private View view;
    public static String selectedRecitator;
    public static Context context;
    private RecitatorAdapter mAdapter;
    private static final int NUMBER_OF_COLUMNS = 2;
    private static final int HIGH_RATING_THRESHOLD = 90;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private RelativeLayout relativeLayout;

    public recitator() {

    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_recitator, container, false);

        mydb = new RecitateurDao(this.getActivity());
        favorite = (ImageView) view.findViewById(R.id.imageView);
        tv_title = (TextView) view.findViewById(R.id.tv_title);

        context = container.getContext();

        initializeViews();

        getRecitatorList();

        recitatorList = new ArrayList<>();

        // recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mAdapter = new RecitatorAdapter(view.getContext(), recitatorList, new RecitatorAdapter.RecyclerItemClickListener() {
            @Override
            public void onClickListener(Recitator recitator, int position) {

            }
        });

        recycler.setAdapter(mAdapter);
        final GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), NUMBER_OF_COLUMNS);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(mAdapter);
        recycler.setItemAnimator(new SlideInUpAnimator());

        //DragScrollBar materialScrollBar = new DragScrollBar(getContext(), recycler, true);

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) view.findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(R.color.divider_red_color);
        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                relativeLayout.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.GONE);
                refreshContent();
            }
        });
        return view;
    }

    private void initializeViews() {
        recycler = (RecyclerView) view.findViewById(R.id.RecitatorRecycler);
        recycler.setVisibility(View.GONE);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.recitatorwait);
    }

    private void getRecitatorList() {
        RequestQueue queue = VolleySingleton.getInstance(view.getContext()).getRequestQueue();
        RecitatorRequest request = new RecitatorRequest(queue);

        request.getSongList(new RecitatorRequest.RecitatorInteface() {
            @Override
            public void onSuccess(ArrayList<Recitator> recitators) {

                relativeLayout.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);

                recitatorList.addAll(recitators);
                mAdapter.notifyDataSetChanged();
                mAdapter.setSelectedPosition(0);

            }

            @Override
            public void onError(String message) {
                Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshContent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getRecitatorList();
                recitatorList = new ArrayList<>();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new RecitatorAdapter(view.getContext(), recitatorList, new RecitatorAdapter.RecyclerItemClickListener() {
                            @Override
                            public void onClickListener(Recitator recitator, int position) {

                            }
                        });
                        recycler.setAdapter(mAdapter);
                        mWaveSwipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        }, 1);
    }

    private void changeSelectedSong(int index) {
        mAdapter.notifyItemChanged(mAdapter.getSelectedPosition());
        mAdapter.setSelectedPosition(index);
        mAdapter.notifyItemChanged(index);
    }

    public void validate(View view){


        boolean res=mydb.insertRecord(tv_title.getText().toString(),"test".toString(),1);
        if(res==true)
            Toast.makeText(this.getActivity(), "Recitateur bien ajouter a vos favoris", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this.getActivity(), "Une erreur est survenu", Toast.LENGTH_SHORT).show();
        mydb.close();
    }


}

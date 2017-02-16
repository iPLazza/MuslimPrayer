package com.example.fazazi.muslimprayer;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.layali.codedreamer.muslim.Adapters.versetAdapter;
import com.layali.codedreamer.muslim.Request.versetRequest;
import com.layali.codedreamer.muslim.Utility.VolleySingleton;

import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 */
public class verset extends Fragment {
    private ArrayList<com.layali.codedreamer.muslim.Models.recitator.verset> recitatorList;
    private RecyclerView recycler;
    private View view;
    public static String selectedRecitator;
    public static Context context;
    private versetAdapter mAdapter;
    private static final int NUMBER_OF_COLUMNS = 1;
    private static final int HIGH_RATING_THRESHOLD = 90;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;

    public verset() {

    }


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

        view = inflater.inflate(R.layout.fragment_verset, container, false);

        context = container.getContext();
        initializeViews();

        getRecitatorList();

        recitatorList = new ArrayList<>();


        mAdapter = new versetAdapter(getContext(), recitatorList, new versetAdapter.RecyclerItemClickListener() {

            @Override
            public void onClickListener(com.layali.codedreamer.muslim.Models.recitator.verset recitator, int position) {

            }
        });
        recycler.setAdapter(mAdapter);
        final GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), NUMBER_OF_COLUMNS);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(mAdapter);
        recycler.setItemAnimator(new SlideInUpAnimator());


        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) view.findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(R.color.divider_red_color);
        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
        return view;
    }

    private void initializeViews() {
        recycler = (RecyclerView) view.findViewById(R.id.RecitatorRecycler);
    }

    private void getRecitatorList() {
        RequestQueue queue = VolleySingleton.getInstance(view.getContext()).getRequestQueue();
        versetRequest request = new versetRequest(queue);

        request.getSongList(new versetRequest.RecitatorInteface() {


            @Override
            public void onSuccess(ArrayList<com.layali.codedreamer.muslim.Models.recitator.verset> sourats) {

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
                mAdapter = new versetAdapter(getContext(), recitatorList, new versetAdapter.RecyclerItemClickListener() {

                    @Override
                    public void onClickListener(com.layali.codedreamer.muslim.Models.recitator.verset recitator, int position) {

                    }
                });

                recycler.setAdapter(mAdapter);
                mWaveSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1);
    }

    private void changeSelectedSong(int index) {
        mAdapter.notifyItemChanged(mAdapter.getSelectedPosition());
        mAdapter.setSelectedPosition(index);
        mAdapter.notifyItemChanged(index);
    }

}

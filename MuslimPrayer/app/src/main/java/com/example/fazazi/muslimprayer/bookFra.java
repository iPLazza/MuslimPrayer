package com.example.fazazi.muslimprayer;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.layali.codedreamer.muslim.Adapters.bookAdapter;
import com.layali.codedreamer.muslim.Models.recitator.book;
import com.layali.codedreamer.muslim.Request.bookRequest;
import com.layali.codedreamer.muslim.Utility.VolleySingleton;

import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class bookFra extends Fragment {

    private ArrayList<book> recitatorList;
    private RecyclerView recycler;
    private View view;
    public static String selectedRecitator;
    public static Context context;
    private bookAdapter mAdapter;
    private static final int NUMBER_OF_COLUMNS = 2;
    private static final int HIGH_RATING_THRESHOLD = 90;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;

    public bookFra() {

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

        view = inflater.inflate(R.layout.fragment_book, container, false);

        context = container.getContext();
        initializeViews();

        getRecitatorList();

        recitatorList = new ArrayList<>();

        // recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mAdapter = new bookAdapter(getContext(), recitatorList, new bookAdapter.RecyclerItemClickListener() {
            @Override
            public void onClickListener(book recitator, int position) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
                        .parse(recitator.getUrl()));
                startActivity(browserIntent);
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
        bookRequest request = new bookRequest(queue);

        request.getSongList(new bookRequest.bookInteface() {
            @Override
            public void onSuccess(ArrayList<book> recitators) {
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
                        mAdapter = new bookAdapter(view.getContext(), recitatorList, new bookAdapter.RecyclerItemClickListener() {
                            @Override
                            public void onClickListener(book recitator, int position) {

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


}

package com.example.fazazi.muslimprayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.layali.codedreamer.muslim.Adapters.audioAdapter;
import com.layali.codedreamer.muslim.Models.recitator.audio;
import com.layali.codedreamer.muslim.Request.audioRequest;
import com.layali.codedreamer.muslim.Utility.Utility;
import com.layali.codedreamer.muslim.Utility.VolleySingleton;

import java.io.IOException;
import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class audioramadan extends Fragment {

    private ArrayList<audio> recitatorList;
    private RecyclerView recycler;
    private View view;
    public static Context context;
    private audioAdapter mAdapter;
    private static final int NUMBER_OF_COLUMNS = 1;
    private static final int HIGH_RATING_THRESHOLD = 90;

    private int currentIndex;
    private TextView tb_title, tb_duration, tv_time;
    private ImageView iv_play, iv_next, iv_previous, iv_share;
    private ProgressBar pb_loader, pb_main_loader;
    private static MediaPlayer mediaPlayer = new MediaPlayer();
    private long currentSongLength;
    private SeekBar seekBar;
    private boolean firstLaunch = true;
    private String newString;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private String art;


    public audioramadan() {

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

        view = inflater.inflate(R.layout.fragment_audioramadan, container, false);

        initializeViews();

        getRecitatorList();

        recitatorList = new ArrayList<>();
        mAdapter = new audioAdapter(getContext(), recitatorList, new audioAdapter.RecyclerItemClickListener() {
            @Override
            public void onClickListener(audio recitator, int position) {
                notifyPlayer(recitator);
                changeSelectedSong(position);
                prepareSong(recitator);
            }
        });

        recycler = (RecyclerView) view.findViewById(R.id.audio);
        recycler.setAdapter(mAdapter);
        final GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), NUMBER_OF_COLUMNS);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(mAdapter);
        recycler.setItemAnimator(new SlideInUpAnimator());


        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                togglePlay(mp);
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (currentIndex + 1 < recitatorList.size()) {
                    audio next = recitatorList.get(currentIndex + 1);
                    changeSelectedSong(currentIndex + 1);
                    prepareSong(next);
                } else {
                    audio next = recitatorList.get(0);
                    changeSelectedSong(0);
                    prepareSong(next);
                }
            }
        });


        //Gestion de la seekbar
        handleSeekbar();

        //Controle de la chanson
        pushPlay();
        pushPrevious();
        pushNext();


        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) view.findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(R.color.divider_dark_color);
        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });





      /*  FloatingActionsMenu fab = (FloatingActionsMenu) view.findViewById(R.id.right_labels);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        return view;
    }

    private void initializeViews() {
        tb_title = (TextView) view.findViewById(R.id.tb_title);
        tb_duration = (TextView) view.findViewById(R.id.tv_time);
        iv_play = (ImageView) view.findViewById(R.id.iv_play);
        iv_next = (ImageView) view.findViewById(R.id.iv_next);
        iv_previous = (ImageView) view.findViewById(R.id.iv_previous);
        pb_loader = (ProgressBar) view.findViewById(R.id.pb_loader);
        pb_main_loader = (ProgressBar) view.findViewById(R.id.pb_main_loader);
        recycler = (RecyclerView) view.findViewById(R.id.recyclerSong);
        seekBar = (SeekBar) view.findViewById(R.id.seekbar);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
    }

    private void getRecitatorList() {
        RequestQueue queue = VolleySingleton.getInstance(view.getContext()).getRequestQueue();
        audioRequest request = new audioRequest(queue);

        request.getSongList(new audioRequest.audioInteface() {
            @Override
            public void onSuccess(ArrayList<audio> sourats) {
                recitatorList.addAll(sourats);
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
                mAdapter = new audioAdapter(getContext(), recitatorList, new audioAdapter.RecyclerItemClickListener() {
                    @Override
                    public void onClickListener(audio recitator, int position) {
                        notifyPlayer(recitator);
                        changeSelectedSong(position);
                        prepareSong(recitator);
                    }
                });
                recycler.setAdapter(mAdapter);
                mWaveSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1);
    }

    private void handleSeekbar() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void prepareSong(audio song) {


        // currentSongLength = (long) (song.getDuration() * 1000);
        pb_loader.setVisibility(View.VISIBLE);
        tb_title.setVisibility(View.GONE);
        iv_play.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selector_play));
        tb_title.setText(song.getTitle());
        //tb_duration.setText(Utility.convertDuration((long) (song.getDuration() * 1000))); //TODO
        String stream = song.getUrl();
        mediaPlayer.reset();

        try {
            mediaPlayer.setDataSource(stream);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void togglePlay(MediaPlayer mp) {

        if (mp.isPlaying()) {
            mp.stop();
            mp.reset();
        } else {
            pb_loader.setVisibility(View.GONE);
            tb_title.setVisibility(View.VISIBLE);
            mp.start();
            iv_play.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selector_pause));
            final Handler mHandler = new Handler();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    seekBar.setMax((int) currentSongLength / 1000);
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                    tv_time.setText(Utility.convertDuration((long) mediaPlayer.getCurrentPosition()));
                    mHandler.postDelayed(this, 1000);

                }
            });
        }

    }

    private void changeSelectedSong(int index) {
        mAdapter.notifyItemChanged(mAdapter.getSelectedPosition());
        currentIndex = index;
        //  ImageView imageView = (ImageView) view.findViewById(R.id.iv_play_active);
       /* Glide.with(view.getContext()).load(R.drawable.playing).centerCrop().into(imageView);*/
        mAdapter.setSelectedPosition(currentIndex);
        mAdapter.notifyItemChanged(currentIndex);
    }

    private void notifyPlayer(audio song) {
        /*final NotificationManager mNotification = (NotificationManager) view.getContext().getSystemService(NOTIFICATION_SERVICE);

        final Intent launchNotifiactionIntent = new Intent(this, sourate.);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this,
                2, launchNotifiactionIntent,
                PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder builder = new Notification.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setTicker(song.getTitle()).setContentTitle(Utility.convertDuration(song.getDuration()))
                .setSmallIcon(R.drawable.ic_scratch)
                .setContentTitle(song.getTitle()).
                        addAction(R.drawable.ic_media_play, "Play", pendingIntent).
                        addAction(R.drawable.ic_media_pause, "Pause", pendingIntent)
                .setContentText(Utility.convertDuration(song.getDuration()))
                .setContentIntent(pendingIntent);

        Notification notification = new Notification.BigPictureStyle(builder)
                .bigPicture(BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_scratch)).build();

        mNotification.notify(562, notification);
        // Get RemoteView and id's needed
        final RemoteViews contentView = notification.bigContentView;
        final int iconId = android.R.id.icon1;


        Picasso.with(getApplicationContext()).load(art).into(contentView, iconId, 562, notification);

        Vibrator v = (Vibrator) this.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(50);*/
    }

    private void pushPlay() {
        iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer.isPlaying() && mediaPlayer != null) {
                    iv_play.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selector_play));
                    mediaPlayer.pause();
                } else {
                    if (firstLaunch) {
                        audio song = recitatorList.get(0);
                        changeSelectedSong(0);
                        prepareSong(song);
                    } else {
                        mediaPlayer.start();
                        firstLaunch = false;
                    }
                    iv_play.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selector_pause));
                }

            }
        });
    }

    private void pushPrevious() {

        iv_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstLaunch = false;
                if (mediaPlayer != null) {

                    if (currentIndex - 1 >= 0) {
                        audio previous = recitatorList.get(currentIndex - 1);
                        changeSelectedSong(currentIndex - 1);
                        prepareSong(previous);
                    } else {
                        changeSelectedSong(recitatorList.size() - 1);
                        prepareSong(recitatorList.get(recitatorList.size() - 1));
                    }

                }
            }
        });

    }

    private void pushNext() {

        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstLaunch = false;
                if (mediaPlayer != null) {

                    if (currentIndex + 1 < recitatorList.size()) {
                        audio next = recitatorList.get(currentIndex + 1);
                        changeSelectedSong(currentIndex + 1);
                        prepareSong(next);
                    } else {
                        changeSelectedSong(0);
                        prepareSong(recitatorList.get(0));
                    }

                }
            }
        });

    }


}

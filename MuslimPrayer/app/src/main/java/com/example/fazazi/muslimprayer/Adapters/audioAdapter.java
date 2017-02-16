package com.example.fazazi.muslimprayer.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.layali.codedreamer.muslim.Models.recitator.audio;
import com.layali.codedreamer.muslim.R;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class audioAdapter extends RecyclerView.Adapter<audioAdapter.SongViewHolder> implements AdapterView.OnClickListener {

    private Context context;
    private ArrayList<audio> songList;
    private RecyclerItemClickListener listener;
    private int selectedPosition;
    private View view;
    private String uri;


    public audioAdapter(Context context, ArrayList<audio> songList, RecyclerItemClickListener listener) {
        this.context = context;
        this.songList = songList;
        this.listener = listener;

    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audiorow, parent, false);

        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        final audio audio = songList.get(position);
        if (audio != null) {
            holder.tv_title.setText(audio.getTitle() + "");
            holder.tv_title.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), "fonts/mushaf.ttf"));
            String desc = audio.getDescription();
            if (desc.length() > 90) {
                holder.tv_description.setText(audio.getDescription().substring(0, 85) + "...");
            } else {
                holder.tv_description.setText(audio.getDescription() + "");
            }

           /* String resource = "sora" + position;
            int id = view.getResources().getIdentifier(resource, "drawable", "com.layali.codedreamer.muslim");
            Glide.with(view.getContext()).load(id).centerCrop().fitCenter().into(holder.iv_artwork);*/


            holder.bind(audio, listener);
        }

    }

    private int getColor() {
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color1 = generator.getRandomColor();
        return color1;
    }

    private String getInitLetter(String Name) {
        StringTokenizer stringTokenizer = new StringTokenizer(Name, " ");
        String Result = "";
        if (stringTokenizer.countTokens() > 1) {
            Result = Result + stringTokenizer.nextToken().substring(0, 1) + " "
                    + stringTokenizer.nextToken().substring(0, 1);
        }
        return Result;
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }


    @Override
    public void onClick(View v) {

    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {


        private ImageView listen, share, favorite;
        private TextView tv_title, tv_description;

        public SongViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.textView4);
            tv_description = (TextView) itemView.findViewById(R.id.view);
            share = (ImageView) itemView.findViewById(R.id.iv_artwork);
            listen = (ImageView) itemView.findViewById(R.id.share);
            favorite = (ImageView) itemView.findViewById(R.id.share);


        }


        public void bind(final audio recitator, final RecyclerItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(recitator, getLayoutPosition());
                }
            });

        }
    }

    public interface RecyclerItemClickListener {
        void onClickListener(audio recitator, int position);
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}
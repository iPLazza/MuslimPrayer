package com.example.fazazi.muslimprayer.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.layali.codedreamer.muslim.Models.recitator.verset;
import com.layali.codedreamer.muslim.R;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class versetAdapter extends RecyclerView.Adapter<versetAdapter.SongViewHolder> implements AdapterView.OnClickListener {

    private Context context;
    private ArrayList<verset> songList;
    private RecyclerItemClickListener listener;
    private int selectedPosition;
    private View view;
    private String uri;


    public versetAdapter(Context context, ArrayList<verset> songList, RecyclerItemClickListener listener) {
        this.context = context;
        this.songList = songList;
        this.listener = listener;

    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.versetrow, parent, false);

        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        final verset verse = songList.get(position);
        if (verse != null) {
            holder.tv_verset.setText(verse.getVerse());
            holder.tv_aya.setText(verse.getAyah() + "");

            holder.bind(verse, listener);
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

        private ImageView iv_fav, iv_share;
        private TextView tv_verset, tv_aya, tv_order;

        public SongViewHolder(View itemView) {
            super(itemView);
            tv_aya = (TextView) itemView.findViewById(R.id.aya);
            tv_verset = (TextView) itemView.findViewById(R.id.view);
            iv_fav = (ImageView) itemView.findViewById(R.id.iv_artwork);
            iv_share = (ImageView) itemView.findViewById(R.id.share);

        }


        public void bind(final verset recitator, final RecyclerItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(recitator, getLayoutPosition());
                }
            });

        }
    }

    public interface RecyclerItemClickListener {
        void onClickListener(verset recitator, int position);
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}
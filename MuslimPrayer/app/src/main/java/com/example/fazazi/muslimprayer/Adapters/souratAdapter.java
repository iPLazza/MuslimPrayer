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
import android.widget.Toast;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.layali.codedreamer.muslim.Models.Sourat.Sourat;
import com.layali.codedreamer.muslim.R;
import com.layali.codedreamer.muslim.Utility.Utility;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class souratAdapter extends RecyclerView.Adapter<souratAdapter.SongViewHolder> implements AdapterView.OnClickListener {

    private Context context;
    private ArrayList<Sourat> songList;
    private RecyclerItemClickListener listener;
    private int selectedPosition;
    private View view;
    private String uri;


    public souratAdapter(Context context, ArrayList<Sourat> songList, RecyclerItemClickListener listener) {
        this.context = context;
        this.songList = songList;
        this.listener = listener;

    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.souratrow, parent, false);

        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        final Sourat sourat = songList.get(position);
        if (sourat != null) {
            holder.tv_title.setText(sourat.getTitle() + "");
            holder.tv_title.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), "fonts/mushaf.ttf"));
            holder.tv_order.setText((sourat.getOrder() + 1) + "");
            holder.tv_duration.setText(Utility.convertDuration((long) (sourat.getDuration() * 1000)) + "");


            String resource = "sora" + position;
            int id = view.getResources().getIdentifier(resource, "drawable", "com.layali.codedreamer.muslim");
            Glide.with(view.getContext()).load(id).centerCrop().fitCenter().into(holder.iv_artwork);

            holder.iv_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(view.getContext(), sourat.getTitle() + "", Toast.LENGTH_SHORT).show();
                }
            });
            holder.bind(sourat, listener);
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

        private final ImageView iv_menu, iv_artwork;
        private ImageView listen, share, read;
        private TextView tv_title, tv_duration, tv_order;

        public SongViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_duration = (TextView) itemView.findViewById(R.id.tv_duration);
            tv_order = (TextView) itemView.findViewById(R.id.tv_ordre);
            iv_artwork = (ImageView) itemView.findViewById(R.id.iv_artwork);
            iv_menu = (ImageView) itemView.findViewById(R.id.share);

        }


        public void bind(final Sourat recitator, final RecyclerItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(recitator, getLayoutPosition());
                }
            });

        }
    }

    public interface RecyclerItemClickListener {
        void onClickListener(Sourat recitator, int position);
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}
package com.example.fazazi.muslimprayer.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.layali.codedreamer.muslim.Models.recitator.book;
import com.layali.codedreamer.muslim.R;
import com.layali.codedreamer.muslim.Utility.downloader;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class bookAdapter extends RecyclerView.Adapter<bookAdapter.SongViewHolder> implements AdapterView.OnClickListener {

    private Context context;
    private ArrayList<book> songList;
    private RecyclerItemClickListener listener;
    private int selectedPosition;
    private View view;
    private String uri;


    public bookAdapter(Context context, ArrayList<book> songList, RecyclerItemClickListener listener) {
        this.context = context;
        this.songList = songList;
        this.listener = listener;

    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookrow, parent, false);

        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        final book book = songList.get(position);
        if (book != null) {
            holder.tv_title.setText(book.getTitle() + "");
            holder.tv_title.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), "fonts/mushaf.ttf"));
            String desc = book.getDescription();
            if (desc.length() > 90) {
                holder.tv_description.setText(book.getDescription().substring(0, 85) + "...");
            } else {
                holder.tv_description.setText(book.getDescription() + "");
            }
            holder.author.setText(book.getAuthor() + "");
            holder.imageView5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File path = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS);
                    downloader.DownloadFile(book.getUrl(), path);
                    Toast.makeText(context, "Downloading" + book.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
           /* String resource = "sora" + position;
            int id = view.getResources().getIdentifier(resource, "drawable", "com.layali.codedreamer.muslim");
            Glide.with(view.getContext()).load(id).centerCrop().fitCenter().into(holder.iv_artwork);*/


            holder.bind(book, listener);
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


        private ImageView listen, share, favorite, imageView5;
        private TextView tv_title, tv_description, author;

        public SongViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.textView4);
            tv_description = (TextView) itemView.findViewById(R.id.view);
            share = (ImageView) itemView.findViewById(R.id.iv_artwork);
            listen = (ImageView) itemView.findViewById(R.id.share);
            favorite = (ImageView) itemView.findViewById(R.id.share);
            author = (TextView) itemView.findViewById(R.id.textView5);
            imageView5 = (ImageView) itemView.findViewById(R.id.imageView5);

        }


        public void bind(final book recitator, final RecyclerItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(recitator, getLayoutPosition());
                }
            });

        }
    }

    public interface RecyclerItemClickListener {
        void onClickListener(book recitator, int position);
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}
package com.example.fazazi.muslimprayer.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.layali.codedreamer.muslim.Models.recitator.Recitator;
import com.layali.codedreamer.muslim.R;
import com.layali.codedreamer.muslim.recitator;
import com.layali.codedreamer.muslim.sourate;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class RecitatorAdapter extends RecyclerView.Adapter<RecitatorAdapter.SongViewHolder> {

    private Context context;
    private ArrayList<Recitator> songList;
    private RecyclerItemClickListener listener;
    private int selectedPosition;
    private View view;


    public RecitatorAdapter(Context context, ArrayList<Recitator> songList, RecyclerItemClickListener listener) {
        this.context = context;
        this.songList = songList;
        this.listener = listener;

    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recitatorrow2, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        final Recitator recit = songList.get(position);
        if (recit != null) {
            holder.tv_title.setText(recit.getName() + "");
            holder.iv_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), recit.getName() + "", Toast.LENGTH_SHORT).show();
                }
            });
            String uri = "";
            if (position < 3) {
                uri = "@drawable/r" + position;
            } else {
                uri = "@drawable/r1";
            }

            int imageResource = view.getContext().getResources().getIdentifier(uri, null, view.getContext().getPackageName());
            Drawable res = view.getContext().getResources().getDrawable(imageResource);
            holder.iv_artwork.setImageDrawable(res);
            holder.iv_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recitator.selectedRecitator = recit.getRecitationId() + "";
                    Fragment fragment = new sourate();
                    FragmentManager fragmentManager = ((FragmentActivity) recitator.context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragment);
                    fragmentTransaction.commit();
                }
            });

            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                             SugarRecord.save(recit);
                            Toast.makeText(view.getContext(), recit.getName() + " saved ", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
            holder.bind(recit, listener);
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


    public static class SongViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_artwork;
        private ImageView iv_menu, iv_listen, share;
        private TextView tv_title;

        public SongViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            iv_artwork = (ImageView) itemView.findViewById(R.id.iv_artwork);
            iv_menu = (ImageView) itemView.findViewById(R.id.share);
            share = (ImageView) itemView.findViewById(R.id.imageView);

        }


        public void bind(final Recitator recitator, final RecyclerItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == iv_menu.getId()) {
                    }
                }
            });
        }
    }

    public interface RecyclerItemClickListener {
        void onClickListener(Recitator recitator, int position);
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}
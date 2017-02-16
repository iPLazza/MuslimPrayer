package com.example.fazazi.muslimprayer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Allah_names extends Fragment {

    private PullRefreshLayout layout;
    private List<Noms> noms = new ArrayList<Noms>();

    public Allah_names() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate  The layout for this fragment
        View view = inflater.inflate(R.layout.fragment_allah_names, container, false);
        final PullRefreshLayout layout = (PullRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        NameList();
        ArrayAdapter<Noms> adapter = new MyListAdapter();
        ListView list = (ListView) view.findViewById(R.id.ListName);
        list.setAdapter(adapter);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout.setRefreshing(false);
                    }
                }, 1200);
            }
        });
        return view;
    }
    public void NameList(){
        noms.add(new Noms(" Ar-rahman ","  The Beneficient ",R.drawable.name1));
        noms.add(new Noms(" Ar-rahim ","  The Mercifull ",R.drawable.name2));
        noms.add(new Noms(" Al-malik "," The Sovereign Lord ",R.drawable.name3));
        noms.add(new Noms(" Al-quddoos ","  The Holy ",R.drawable.name4));
        noms.add(new Noms(" As-salam ","  The Source of Peace ",R.drawable.name5));
        noms.add(new Noms(" Al-mu'min ","  The Guardian of Faith ",R.drawable.name6));
        noms.add(new Noms(" Al-muhaymin ","  The protector ",R.drawable.name7));
        noms.add(new Noms(" Al-aziz ","  The Mighty ",R.drawable.name8));
        noms.add(new Noms(" Al-jabbar ","  The Compeller ",R.drawable.name9));
        noms.add(new Noms(" Al-mutakabbir ","  The Majestic ",R.drawable.name10));
        noms.add(new Noms(" 1l-khaliq ","  The Creator ",R.drawable.name11));
        noms.add(new Noms(" Al-bari ","  The Evolver ",R.drawable.name12));
        noms.add(new Noms(" Al-musawwir ","  The Fashioner ",R.drawable.name13));
        noms.add(new Noms(" Al-ghaffar ","  The Forgiver ",R.drawable.name14));
        noms.add(new Noms(" Al-qahhar ","  The Subdue ",R.drawable.name15));
        noms.add(new Noms(" Al-wahhab "," The Bestover ",R.drawable.name17));
        noms.add(new Noms(" Ar-razzaq "," The Provider ",R.drawable.name18));
        noms.add(new Noms(" Al-fattah "," The Opner ",R.drawable.name19));
        noms.add(new Noms(" Al-alim "," The All Knowing ",R.drawable.name20));
        noms.add(new Noms(" Al-Qabiz "," The Constrictor ",R.drawable.name21));
        noms.add(new Noms(" Al-basit "," The Expender ",R.drawable.name22));
        noms.add(new Noms(" Al-khafiz "," The Abaser ",R.drawable.name23));
        noms.add(new Noms(" Al-rafi "," The Exalter ",R.drawable.name24));
        noms.add(new Noms(" Al-muizz "," The Honourer ",R.drawable.name25));
        noms.add(new Noms(" Al-muzil "," The Dishonourer ",R.drawable.name26));
        noms.add(new Noms(" Al-sami "," The All Hearing ",R.drawable.name27));
        noms.add(new Noms(" Al-basir "," The All Seeing ",R.drawable.name28));
        noms.add(new Noms(" Al-hakam "," The Judge ",R.drawable.name29));
        noms.add(new Noms(" Al-adl "," The Just ",R.drawable.name30));
        noms.add(new Noms(" Al-latif "," The Subtle One ",R.drawable.name31));
        noms.add(new Noms(" Al-khabir "," The Aware ",R.drawable.name32));
        noms.add(new Noms(" Al-halim "," The Forbearing One ",R.drawable.name33));
        noms.add(new Noms(" Al-azim "," The Great One ",R.drawable.name34));
        noms.add(new Noms(" Al-ghafur "," The All Forgiving ",R.drawable.name35));
        noms.add(new Noms(" Ash-shakur "," The Appreciative ",R.drawable.name36));
        noms.add(new Noms(" Al-ali "," The Most High ",R.drawable.name37));
        noms.add(new Noms(" Al-kabir "," The Most Great ",R.drawable.name38));
        noms.add(new Noms(" Al-hafiz "," The Preserver ",R.drawable.name39));
        noms.add(new Noms(" Al-muqit "," The Maintainer ",R.drawable.name40));
        noms.add(new Noms(" Al-haseeb "," The Reckoner ",R.drawable.name41));
        noms.add(new Noms(" Al-jalil "," The Sublime One ",R.drawable.name42));
        noms.add(new Noms(" Al-karim "," The Generous One ",R.drawable.name43));
        noms.add(new Noms(" Ar-raqib "," The Watchfull ",R.drawable.name44));
        noms.add(new Noms(" Al-mujib "," The Responsive ",R.drawable.name45));
        noms.add(new Noms(" Al-wasi "," The All-Embracing ",R.drawable.name46));
        noms.add(new Noms(" Al-hakeem "," The Wise ",R.drawable.name47));
        noms.add(new Noms(" Al-wadud "," The Loving ",R.drawable.name48));
        noms.add(new Noms(" Al-majeed "," The Most Glorious One ",R.drawable.name49));
        noms.add(new Noms(" Al-ba'ith "," The Resurrector ",R.drawable.name50));
        noms.add(new Noms(" Ash-shaheed "," The Witness ",R.drawable.name51));
        noms.add(new Noms(" Al-haqq "," The Truth ",R.drawable.name52));
        noms.add(new Noms(" Al-wakil "," The Trustee ",R.drawable.name53));
        noms.add(new Noms(" Al-qawi "," The Most Strong ",R.drawable.name54));
        noms.add(new Noms(" Al-mateen "," The Firm One ",R.drawable.name55));
        noms.add(new Noms(" Al-wali "," The Protecting Friend ",R.drawable.name56));
        noms.add(new Noms(" Al-hameed "," The Praiseworthy ",R.drawable.name57));
        noms.add(new Noms(" Al-Muhsi "," The Reckoner ",R.drawable.name58));
        noms.add(new Noms(" Al-mubdi "," The Originator ",R.drawable.name59));
        noms.add(new Noms(" Al-mu'id "," The Restorer ",R.drawable.name60));
        noms.add(new Noms(" Al-muhyi "," The Giver Of Life ",R.drawable.name61));
        noms.add(new Noms(" Al-mumit "," The Creator Of Death ",R.drawable.name62));
        noms.add(new Noms(" Al-hayee "," The Alive ",R.drawable.name63));
        noms.add(new Noms(" Al-qayyum "," The Self-Subsisting ",R.drawable.name64));
        noms.add(new Noms(" Al-wajid "," The Finder ",R.drawable.name65));
        noms.add(new Noms(" Al-majid ","Tne Noble ",R.drawable.name66));
        noms.add(new Noms(" Al-wahid "," The Unique ",R.drawable.name67));
        noms.add(new Noms(" Al-ahad "," The One ",R.drawable.name68));
        noms.add(new Noms(" As-samad "," The Eternal ",R.drawable.name69));
        noms.add(new Noms(" Al-qadir "," The Able ",R.drawable.name70));
        noms.add(new Noms(" Al-muqtadir "," The Powerful ",R.drawable.name71));
        noms.add(new Noms(" Al-muqaddim "," The Expediter ",R.drawable.name72));
        noms.add(new Noms(" Al-mu'akhkhir "," The Delayer ",R.drawable.name73));
        noms.add(new Noms(" Al-awwal "," The First ",R.drawable.name74));
        noms.add(new Noms(" Al-aakhir "," The Last ",R.drawable.name75));
        noms.add(new Noms(" Az-zahir "," The Manifest ",R.drawable.name76));
        noms.add(new Noms(" Al-batin "," The Hiddeen ",R.drawable.name77));
        noms.add(new Noms(" Al-wali "," The Governor ",R.drawable.name78));
        noms.add(new Noms(" Al-muta'ali "," The Most Exalted ",R.drawable.name79));
        noms.add(new Noms(" Al-barr "," The Source Of All Goodness ",R.drawable.name70));
        noms.add(new Noms(" At-tawwab "," The Acceptor Of Repentance ",R.drawable.name81));
        noms.add(new Noms(" Al-muntaqim "," The Avenger ",R.drawable.name82));
        noms.add(new Noms(" Al-'afuw "," The Pardoner ",R.drawable.name83));
        noms.add(new Noms(" Ar-raoof "," The Compassionate ",R.drawable.name84));
        noms.add(new Noms(" Malik-ul-mulk "," The Eternal Owner Of Sovereignty ",R.drawable.name85));
        noms.add(new Noms(" Ul-jalal-wal-ikram "," The Lord Of Majesty And Bounty ",R.drawable.name86));
        noms.add(new Noms(" Al-muqsit ","  The Equitable ",R.drawable.name87));
        noms.add(new Noms(" Al-jaamay ","  The Gra Therer ",R.drawable.name88));
        noms.add(new Noms(" Al-ghani "," The Self-Sufficient ",R.drawable.name89));
        noms.add(new Noms(" Al-mughni ","  The Enricher ",R.drawable.name90));
        noms.add(new Noms(" Al-maanay ","  The Preventer ",R.drawable.name91));
        noms.add(new Noms(" Ad-daarr ","  The Distresser ",R.drawable.name92));
        noms.add(new Noms(" An-naafay ","  The Propitious ",R.drawable.name93));
        noms.add(new Noms(" An-noor ","  The Light ",R.drawable.name94));
        noms.add(new Noms(" Al-haadi ","  The Guide ",R.drawable.name95));
        noms.add(new Noms(" Al-badei ","  The Incomparable ",R.drawable.name96));
        noms.add(new Noms(" Al-baqi ","  The Everlasting ",R.drawable.name97));
        noms.add(new Noms(" Al-waris ","  The Supreme Inheritor ",R.drawable.name98));
        noms.add(new Noms(" Ar-rasheed ","  The Guide To  The Right Path ",R.drawable.name99));
        noms.add(new Noms(" A-saboor ","  The Patient ",R.drawable.name100));
    }

    private class MyListAdapter extends ArrayAdapter<Noms> {
        public MyListAdapter(){
            super(Allah_names.this.getActivity(), R.layout.item_names_list, noms);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if(itemView == null){
                itemView = getActivity().getLayoutInflater().inflate(R.layout.item_names_list,parent, false);
            }
            Noms currentNom = noms.get(position);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.icon);
            imageView.setImageResource(currentNom.getIconID());

            TextView Text1 = (TextView) itemView.findViewById(R.id.name1FR);
            Text1.setText(currentNom.getName1());

            TextView Text2 = (TextView) itemView.findViewById(R.id.name2EN);
            Text2.setText(currentNom.getName2());
            return itemView;
            //return super.getView(position, convertView, parent);
        }
    }


}

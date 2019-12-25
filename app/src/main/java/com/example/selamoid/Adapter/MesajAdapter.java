package com.example.selamoid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.selamoid.Model.Mesaj;
import com.example.selamoid.R;

import java.util.List;

public class MesajAdapter extends ArrayAdapter<Mesaj> {

    private static final int BENİM_MESAJ = 0;
    private static final int BOT_MESAJ = 1;

    public MesajAdapter(@NonNull Context icerik, List<Mesaj> veri) {
        super(icerik, R.layout.kullanici_sorgu_layout,veri);
    }

    @Override
    public int getItemViewType(int konum) {

        Mesaj item = getItem(konum);

        if (item.getBenim()) {
            return BENİM_MESAJ;
        }
        else {
            return BOT_MESAJ;
        }
    }

    @NonNull
    @Override
    public View getView(int konum, @Nullable View convertView, @NonNull ViewGroup parent) {

        int gorunmeSekli = getItemViewType(konum);

        if (gorunmeSekli == BENİM_MESAJ) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.kullanici_sorgu_layout,parent,false);

            TextView textView = convertView.findViewById(R.id.text);
            textView.setText(getItem(konum).getIcerik());
        }
        else if (gorunmeSekli == BOT_MESAJ) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.bot_cevap_layout,parent,false);

            TextView textView = convertView.findViewById(R.id.text);
            textView.setText(getItem(konum).getIcerik());
        }

        convertView.findViewById(R.id.chatMessageView).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {Toast.makeText(getContext(), "Tıklandı...", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return convertView;
    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
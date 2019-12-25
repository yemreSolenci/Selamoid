package com.example.selamoid.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selamoid.Adapter.MesajAdapter;
import com.example.selamoid.DosyaIslemleri;
import com.example.selamoid.Model.Mesaj;
import com.example.selamoid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private MesajAdapter adapter;
    RelativeLayout rLayout;

    private ListView listView;
    private EditText editTextMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViev);
        final FloatingActionButton btnGndr = findViewById(R.id.btnGndr);
        editTextMsg = findViewById(R.id.editTextMsg);
        editTextMsg.setOnEditorActionListener(editorListener);
        rLayout = findViewById(R.id.relativeLayout);

        adapter = new MesajAdapter(this, new ArrayList<Mesaj>());
        listView.setAdapter(adapter);

        btnGndr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiklandi();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        rLayout.setBackgroundColor(getResources().getColor(AyarlarActivity.drawableID));
    }

    private void tiklandi() {
        String mesaj = editTextMsg.getText().toString();
        if (TextUtils.isEmpty(mesaj)) {
            Toast.makeText(MainActivity.this, "Lütfen bir sorgu girin", Toast.LENGTH_SHORT).show();
            return;
        }
        gndrMesaj(mesaj);

        AssetManager assetManager = getAssets();
        botCevap(DosyaIslemleri.main(assetManager, mesaj));

        //editText temizleme
        editTextMsg.setText("");
        listView.setSelection(adapter.getCount() - 1);
    }

    private TextView.OnEditorActionListener editorListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                tiklandi();
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        switch (id) {
            case R.id.paylas:
                botCevap("Paylaşmak istemene sevindim ama henüz Google Play'e yüklü değilim.");
                listView.setSelection(adapter.getCount() - 1);
                return true;

            case R.id.ayarlar:
                Intent ayarlaragec = new Intent(MainActivity.this, AyarlarActivity.class);
                startActivity(ayarlaragec);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void botCevap (String cevap){
        Mesaj chatMesaj = new Mesaj(false, cevap);
        adapter.add(chatMesaj);
    }

    private void gndrMesaj (String mesaj){
        Mesaj chatMesaj = new Mesaj(true, mesaj);
        adapter.add(chatMesaj);
    }
}
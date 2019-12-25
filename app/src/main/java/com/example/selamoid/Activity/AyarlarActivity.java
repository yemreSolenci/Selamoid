package com.example.selamoid.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.example.selamoid.R;
public class AyarlarActivity extends AppCompatActivity {

    LinearLayout lLayout;
    public static int drawableID = 2131099785;
    int nextColor =6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ayarlar_layout);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.ayarlar_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Ayarlar");

        lLayout = findViewById(R.id.linearLayout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Button arkaPlanBtn = (Button) findViewById(R.id.arkaPlanBtn);

        arkaPlanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View someView = findViewById(android.R.id.content);
                View root = someView.getRootView();
                Resources res = getResources();
                final TypedArray myImages = res.obtainTypedArray(R.array.image);

                nextColor++;

                drawableID = myImages.getResourceId(nextColor, -1);
                lLayout.setBackgroundColor(getResources().getColor(drawableID));
                Log.i("Reng:  ", String.valueOf(nextColor));
                if (nextColor >=7)
                    nextColor = 0;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        lLayout.setBackgroundColor(getResources().getColor(drawableID));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent geriDon = new Intent(getApplicationContext(), MainActivity.class);
            NavUtils.navigateUpTo(this, geriDon);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
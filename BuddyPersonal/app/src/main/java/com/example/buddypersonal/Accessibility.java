package com.example.buddypersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import java.util.Locale;

public class Accessibility extends AppCompatActivity {

    Context context;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility);

        RadioButton eng = (RadioButton)findViewById(R.id.rdb_english);
        eng.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                context = LocaleHelper.setLocale(Accessibility.this, "en");
                resources = context.getResources();

//                Locale locale = new Locale("en-rEN");
//                Locale.setDefault(locale);
//                Configuration config = new Configuration();
//                config.locale = locale;
            }
        });

        RadioButton malt = (RadioButton)findViewById(R.id.rdb_maltese);
        malt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                context = LocaleHelper.setLocale(Accessibility.this, "mt");
                resources = context.getResources();

//                Locale locale = new Locale("mt-rMT");
//                Locale.setDefault(locale);
//                Configuration config = new Configuration();
//                config.locale = locale;
            }
        });

        RadioButton span = (RadioButton)findViewById(R.id.rbd_spanish);
        span.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                context = LocaleHelper.setLocale(Accessibility.this, "es");
                resources = context.getResources();

//                Locale locale = new Locale("es-rES");
//                Locale.setDefault(locale);
//                Configuration config = new Configuration();
//                config.locale = locale;
            }
        });
    }
}
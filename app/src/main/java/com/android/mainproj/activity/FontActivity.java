package com.android.mainproj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.mainproj.R;
import com.android.mainproj.log.LogService;

public class FontActivity extends AppCompatActivity
{
    private ImageButton btn_font_back;

    private TextView tv_font_change;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.activity_font);

            init();

            setting();

            addListener();
        }
        catch (Exception ex)
        {
            LogService.error(this, ex.getMessage(), ex);
        }
    }

    private void init()
    {
        tv_font_change = findViewById(R.id.tv_font_change);

        btn_font_back = findViewById(R.id.btn_font_back);
    }

    private void setting()
    {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/nexon.ttf");

        tv_font_change.setTypeface(typeface);
    }

    private void addListener()
    {
        btn_font_back.setOnClickListener(listener_back);
    }

    private View.OnClickListener listener_back = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            finish();
        }
    };
}
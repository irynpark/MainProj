package com.android.mainproj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mainproj.R;
import com.android.mainproj.log.LogService;
import com.android.mainproj.util.ConvertUnitUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ScrollActivity extends AppCompatActivity
{
    private ImageButton btn_scroll_back;

    private LinearLayout layout_scroll;

    private FloatingActionButton fab_scroll;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.activity_scroll);

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
        btn_scroll_back = findViewById(R.id.btn_scroll_back);

        layout_scroll = findViewById(R.id.layout_scroll);

        fab_scroll = findViewById(R.id.fab_scroll);
    }

    private void setting()
    {

    }

    private void addListener()
    {
        btn_scroll_back.setOnClickListener(listener_back_click);

        fab_scroll.setOnClickListener(listener_fab_click);
    }

    private TextView createNewView()
    {
        TextView textView = new TextView(this);

        textView.setText("새롭게 추가된 뷰입니다.");

        textView.setLayoutParams
        (
               new LinearLayout.LayoutParams
                       (
                               ViewGroup.LayoutParams.MATCH_PARENT,
                               ConvertUnitUtil.ConvertSizeToDP(this, 50)
                       )
        );

        textView.setGravity(Gravity.CENTER);

        textView.setTextColor(Color.rgb(0,0,0));

        textView.setBackgroundColor(Color.rgb(255,174,201));

        return textView;
    }

    private View.OnClickListener listener_back_click = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            finish();
        }
    };

    private View.OnClickListener listener_fab_click = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            layout_scroll.addView(createNewView());
        }
    };
}
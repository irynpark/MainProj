package com.android.mainproj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.mainproj.R;
import com.android.mainproj.lifecycle.TestLifeCycle;
import com.android.mainproj.log.LogService;

public class TestActivity extends AppCompatActivity {

    private Activity activity;
    private TextView tv_name;

    private Button btn_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.activity_test);

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
        activity = this;

        tv_name = findViewById(R.id.tv_name);

        btn_exit = findViewById(R.id.btn_exit);

    }

    private void setting()
    {
        this.getLifecycle().addObserver(new TestLifeCycle());

        Intent intent = getIntent();

        String id = intent.getStringExtra("ID");

        String pw = intent.getStringExtra("PW");

        LogService.debug(this,pw);

        tv_name.setText(id);
    }

    private void addListener()
    {
        btn_exit.setOnClickListener(Listener_exit);
    }

    private View.OnClickListener Listener_exit = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            finish();
        }
    };
}
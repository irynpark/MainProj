package com.android.mainproj.activity;

import static com.android.mainproj.config.RequestCodeConfig.REQ_CODE;
import static com.android.mainproj.config.RequestCodeConfig.REQ_MAIN_ACTIVITY;
import static com.android.mainproj.config.ResultCodeConfig.RESULT_CODE_ACTIVITY_RESULT_OK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.mainproj.R;
import com.android.mainproj.log.LogService;

public class ResultCodeActivity extends AppCompatActivity
{
    private ImageButton btn_res_code_back;

    private Button btn_res_code_send;

    private Button btn_res_param_send;

    private Button btn_req_code_send;

    private Button btn_code_move;

    private Button btn_code_custom;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.activity_result_code);

            init();

            addListener();

        }
        catch (Exception ex)
        {
            LogService.error(this, ex.getMessage(), ex);
        }
    }

    public void init()
    {
        btn_res_code_back = findViewById(R.id.btn_res_code_back);

        btn_res_code_send = findViewById(R.id.btn_res_code_send);

        btn_res_param_send = findViewById(R.id.btn_res_param_send);

        btn_req_code_send = findViewById(R.id.btn_req_code_send);

        btn_code_move = findViewById(R.id.btn_code_move);

        btn_code_custom = findViewById(R.id.btn_code_custom);
    }

    public void addListener()
    {
        btn_res_code_back.setOnClickListener(listener_back_clcik);

        btn_res_code_send.setOnClickListener(listener_res_code_send);

        btn_res_param_send.setOnClickListener(listener_param_send);

        btn_req_code_send.setOnClickListener(listener_req_code_send);

        btn_code_move.setOnClickListener(listener_res_code_move);

        btn_code_custom.setOnClickListener(listener_res_code_custom);
    }

    private View.OnClickListener listener_back_clcik = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            setResult(33);
            finish();
        }
    };

    private View.OnClickListener listener_res_code_send = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            setResult(RESULT_CODE_ACTIVITY_RESULT_OK);
            finish();
        }
    };

    private View.OnClickListener listener_param_send = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent();
            intent.putExtra("DATA", "TEST_DATA");

            setResult(RESULT_CODE_ACTIVITY_RESULT_OK, intent);
            finish();
        }
    };

    private View.OnClickListener listener_req_code_send = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = getIntent();

            int reqCode = intent.getIntExtra(REQ_CODE, -1);

            if(reqCode == REQ_MAIN_ACTIVITY)
            {
                intent.putExtra("DATA", "MAIN_DATA");
            }

            setResult(RESULT_CODE_ACTIVITY_RESULT_OK, intent);

            finish();
        }
    };

    private View.OnClickListener listener_res_code_move = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = getIntent();

            int reqCode = intent.getIntExtra(REQ_CODE, -1);

            if(reqCode == REQ_MAIN_ACTIVITY)
            {
                intent.putExtra("DATA", "CODE_MOVE");
            }

            setResult(RESULT_CODE_ACTIVITY_RESULT_OK, intent);

            finish();
        }
    };

    private View.OnClickListener listener_res_code_custom = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = getIntent();

            int reqCode = intent.getIntExtra(REQ_CODE, -1);

            if(reqCode == REQ_MAIN_ACTIVITY)
            {
                intent.putExtra("DATA", "CODE_CUSTOM");
            }

            setResult(RESULT_CODE_ACTIVITY_RESULT_OK, intent);

            finish();
        }
    };

}
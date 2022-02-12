package com.android.mainproj.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.mainproj.R;
import com.android.mainproj.dialog.CustomDialog;
import com.android.mainproj.log.LogService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDialogActivity extends AppCompatActivity
{
    private Activity activity;
    private TextView tv_dialog_result;
    private ImageButton btn_custom_dialog_back;
    private Button btn_call_custom_dialog;
    private Button btn_call_calender_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.activity_custom_dialog);
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
        tv_dialog_result = findViewById(R.id.tv_dialog_result);
        btn_custom_dialog_back = findViewById(R.id.btn_custom_dialog_back);
        btn_call_custom_dialog = findViewById(R.id.btn_call_custom_dialog);
        btn_call_calender_dialog = findViewById(R.id.btn_call_calender_dialog);
    }

    private void setting()
    {

    }

    private void addListener()
    {
        btn_custom_dialog_back.setOnClickListener(listener_back_click);
        btn_call_custom_dialog.setOnClickListener(listener_call_custom_dialog);
        btn_call_calender_dialog.setOnClickListener(listener_call_calender_dialog);
    }

    private View.OnClickListener listener_back_click = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            finish();
        }
    };

    private View.OnClickListener listener_call_custom_dialog = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            CustomDialog customDialog =  new CustomDialog(activity, CustomDialog.DialogMode.MODE_CONFIRM);
            customDialog.setTitle("타이틀 입니다.");
            customDialog.setContent("내용을 입력하였습니다.");
            customDialog.setDialogClickListener(new CustomDialog.OnDialogClickListener()
            {
                @Override
                public void onYesClick()
                {
                    tv_dialog_result.setText("커스텀 다이얼로그의 예 버튼 클릭");
                }

                @Override
                public void onNoClick()
                {
                    tv_dialog_result.setText("커스텀 다이얼로그의 아니오 버튼 클릭");
                }
            });
            customDialog.show();
        }
    };

    private View.OnClickListener listener_call_calender_dialog = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            CustomDialog customDialog =  new CustomDialog(activity, CustomDialog.DialogMode.MODE_CALENDAR);
            customDialog.setTitle("캘린더 타이틀 입니다.");
            customDialog.setCalendarDialogClickListener(new CustomDialog.OnCalendarDialogClickListener() {
                @Override
                public void onDoneClick(Date selectDate) {

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

                    tv_dialog_result.setText(simpleDateFormat.format(selectDate) + "을 선택하셨습니다.");

                }
            });
            customDialog.show();
        }
    };
}
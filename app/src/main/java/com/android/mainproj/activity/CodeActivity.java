package com.android.mainproj.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.mainproj.R;
import com.android.mainproj.log.LogService;
import com.android.mainproj.util.ConvertUnitUtil;

import java.util.ArrayList;

public class CodeActivity extends AppCompatActivity
{
    private Activity activity;

    private LinearLayout mainLayout;

    private ImageButton btn_code_back;

    private ArrayList<Button> buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView();

            init();

            addListener();
        }
        catch (Exception ex)
        {
            LogService.error(this, ex.getMessage(), ex);
        }

    }

    private void setContentView()
    {
        // 메인 레이아웃 생성
        mainLayout = new LinearLayout(this);

        mainLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        mainLayout.setOrientation(LinearLayout.VERTICAL);

        mainLayout.setBackgroundColor(Color.rgb(239,228,176));

        // 메뉴 레이아웃 생성
        LinearLayout menuLayout = new LinearLayout(this);

        menuLayout.setLayoutParams(new LinearLayout.LayoutParams
                (
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        ConvertUnitUtil.ConvertSizeToDP(this,46)
                )
        );

        menuLayout.setOrientation(LinearLayout.HORIZONTAL);

        menuLayout.setBackgroundColor(Color.rgb(255, 255, 255));

        btn_code_back = new ImageButton(this);

        btn_code_back.setLayoutParams
        (
                new LinearLayout.LayoutParams
                        (
                                ConvertUnitUtil.ConvertSizeToDP(this, 100),
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        )
        );

        btn_code_back.setImageResource(R.drawable.icon_back_arrow);

        btn_code_back.setBackgroundColor(Color.TRANSPARENT);

        btn_code_back.setScaleType(ImageView.ScaleType.FIT_START);

        btn_code_back.setPadding
        (
                ConvertUnitUtil.ConvertSizeToDP(this, 35),
                ConvertUnitUtil.ConvertSizeToDP(this, 15),
                0,
                ConvertUnitUtil.ConvertSizeToDP(this, 15)
        );

        menuLayout.addView(btn_code_back);

        mainLayout.addView(menuLayout);

        Button button;

        buttonList = new ArrayList<>();

        for(int i = 1; i <=10; i++)
        {
            button = new Button(this);

            button.setText(i + "번째 버튼");

            button.setTag(i);

            buttonList.add(button);

            mainLayout.addView(button);
        }

        setContentView(mainLayout);
    }

    private void init()
    {
        activity = this;
    }

    private void addListener()
    {
        btn_code_back.setOnClickListener(listener_back_click);

        for(int i = 0; i < buttonList.size(); i++)
        {
            buttonList.get(i).setOnClickListener(listener_btn_list_click);
        }
    }

    private View.OnClickListener listener_back_click = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            finish();
        }
    };

    private View.OnClickListener listener_btn_list_click = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int id = (int)v.getTag();

            if(id == 1)
            {
                Toast.makeText(activity, "첫번째 클릭!", Toast.LENGTH_SHORT).show();
            }
            else if(id == 2)
            {
                Toast.makeText(activity, "두번째 클릭!", Toast.LENGTH_SHORT).show();
            }
            else if(id == 3)
            {
                Toast.makeText(activity, "세번째 클릭!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(activity, "찐따버튼 클릭!", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
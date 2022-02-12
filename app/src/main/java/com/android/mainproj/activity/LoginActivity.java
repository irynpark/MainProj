package com.android.mainproj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.mainproj.R;
import com.android.mainproj.lifecycle.LoginLifeCycle;
import com.android.mainproj.log.LogService;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private Activity activity;

    private InputFilter filter_text;

    private EditText et_login_id, et_login_pw;

    private Button btn_login;

    private ImageButton btn_pw_show;

    private CheckBox chk_auto_login;

    private Boolean btnPwFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.activity_login);

            init();

            setting();

            addListener();
        }
        catch(Exception ex)
        {
            LogService.error(this, ex.getMessage(), ex);
        }
    }

    private void init()
    {
        activity = this;

        et_login_id = findViewById(R.id.et_login_id);

        et_login_pw = findViewById(R.id.et_login_pw);

        btn_login = findViewById(R.id.btn_login);

        btn_pw_show = findViewById(R.id.btn_pw_show);

        chk_auto_login = findViewById(R.id.chk_auto_login);
    }

    private void setting()
    {
        this.getLifecycle().addObserver(new LoginLifeCycle());
        filter_text = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
            {
                Pattern pattern = Pattern.compile("^[a-zA-Z0-9]*$");

                if(pattern.matcher(source).matches() == false)
                {
                    return "";
                }

                return null;
            }
        };
    }

    private void addListener()
    {
        et_login_id.setFilters(new InputFilter[]{filter_text});

        et_login_pw.setFilters(new InputFilter[]{filter_text});

        btn_pw_show.setOnClickListener(listener_pw_show);

        chk_auto_login.setOnClickListener(listener_auto_login);

        btn_login.setOnClickListener(listener_login);

    }

    private View.OnClickListener listener_pw_show = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if(btnPwFlag == true)
            {
                btn_pw_show.setImageResource(R.drawable.icon_passwd_hide);

                et_login_pw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            else
            {
                btn_pw_show.setImageResource(R.drawable.icon_passwd_show);

                et_login_pw.setInputType(InputType.TYPE_CLASS_TEXT);
            }

            btnPwFlag = !btnPwFlag;

        }
    };

    private View.OnClickListener listener_auto_login = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if(chk_auto_login.isChecked())
            {
                LogService.info(activity, "자동 로그인 상태가 활성화되었습니다.");
            }
            else
            {
                LogService.info(activity, "자동 로그인 상태가 비활성화되었습니다.");
            }
        }
    };

    private View.OnClickListener listener_login = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            try
            {
                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra("ID", et_login_id.getText().toString());
                intent.putExtra("PW", et_login_pw.getText().toString());

                startActivity(intent);
            }
            catch (Exception ex)
            {
                LogService.error(activity, ex.getMessage(), ex);
                Toast.makeText(activity, "로그인실패!", Toast.LENGTH_SHORT).show();
            }
        }
    };

}
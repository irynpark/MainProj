package com.android.mainproj.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mainproj.R;
import com.android.mainproj.log.LogService;
import com.google.android.material.navigation.NavigationView;

public class NaviActivity extends AppCompatActivity
{
    private Activity activity;

    private DrawerLayout layout_navi;

    private Toolbar tb_navi;

    private NavigationView nv_navi;

    private TextView tv_navi_login_id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.activity_navi);

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

        layout_navi = findViewById(R.id.layout_navi);

        tb_navi = findViewById(R.id.tb_navi);

        nv_navi = findViewById(R.id.nv_navi);

        //tv_navi_login_id = findViewById(R.id.tv_navi_login_id);
        tv_navi_login_id = nv_navi.getHeaderView(0).findViewById(R.id.tv_navi_login_id);
    }

    private void setting()
    {
        Intent intent = getIntent();

        String loginId = intent.getStringExtra("ID");

        tv_navi_login_id.setText(loginId);

        setSupportActionBar(tb_navi);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_navi_menu);
    }

    private void addListener()
    {
        nv_navi.setNavigationItemSelectedListener(listener_navi_menu_click);
    }

    private NavigationView.OnNavigationItemSelectedListener listener_navi_menu_click = new NavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            if(item.getItemId() == R.id.icon_post)
            {
                Toast.makeText(activity, "Post Menu Click", Toast.LENGTH_SHORT).show();
                layout_navi.closeDrawer(GravityCompat.START);

                return true;
            }
            else if(item.getItemId() == R.id.icon_home)
            {
                Toast.makeText(activity, "Home Menu Click", Toast.LENGTH_SHORT).show();
                layout_navi.closeDrawer(GravityCompat.START);

                return true;
            }
            else if(item.getItemId() == R.id.icon_list)
            {
                Toast.makeText(activity, "List Menu Click", Toast.LENGTH_SHORT).show();
                layout_navi.closeDrawer(GravityCompat.START);

                return true;
            }


            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            layout_navi.openDrawer(GravityCompat.START);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
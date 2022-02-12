package com.android.mainproj.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.mainproj.R;
import com.android.mainproj.adapter.TabAdapter;
import com.android.mainproj.fragment.TabOneFragment;
import com.android.mainproj.fragment.TabTwoFragment;
import com.android.mainproj.log.LogService;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity
{
    private Activity activity;

    private TabLayout tl_tab;

    private ViewPager2 vp_tab;

    private TabAdapter tabAdapter;

    private ImageButton btn_tab_back;

    private List<Fragment> fragmentList;

    private TabOneFragment fragment_tab1;

    private TabTwoFragment fragment_tab2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.activity_tab);

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

        btn_tab_back = findViewById(R.id.btn_tab_back);

        tl_tab = findViewById(R.id.tl_tab);

        vp_tab = findViewById(R.id.vp_tab);

        fragmentList = new ArrayList<>();

        fragment_tab1 = new TabOneFragment();

        fragment_tab2 = new TabTwoFragment();

        tabAdapter = new TabAdapter(this, fragmentList);
    }

    private void setting()
    {
        fragmentList.add(fragment_tab1);

        fragmentList.add(fragment_tab2);

        vp_tab.setAdapter(tabAdapter);

        tl_tab.addTab(tl_tab.newTab());

        tl_tab.addTab(tl_tab.newTab());

        new TabLayoutMediator(tl_tab, vp_tab, new TabLayoutMediator.TabConfigurationStrategy()
        {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position)
            {
                TextView textView = new TextView(activity);

                if(position == 0)
                {
                    textView.setText(("탭1"));
                }
                else if(position == 1)
                {
                    textView.setText("탭2");
                }

                tab.setCustomView(textView);
            }
        }).attach();

        vp_tab.setCurrentItem(0);
    }

    private void addListener()
    {
        btn_tab_back.setOnClickListener(Listener_back);

        tl_tab.addOnTabSelectedListener(listener_tab_click);
    }

    private TabLayout.OnTabSelectedListener listener_tab_click = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab)
        {
            LogService.info(activity, ((TextView)tab.getCustomView()).getText().toString() + " 선택");
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab)
        {
            LogService.info(activity, ((TextView)tab.getCustomView()).getText().toString() + " 비선택");
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab)
        {
            LogService.info(activity, ((TextView)tab.getCustomView()).getText().toString() + " 재선택");
        }
    };

    private View.OnClickListener Listener_back = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            finish();
        }
    };

}
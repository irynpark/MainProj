package com.android.mainproj.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mainproj.R;
import com.android.mainproj.adapter.RecycleAdapter;
import com.android.mainproj.log.LogService;
import com.android.mainproj.vo.RecycleMemberVo;

import java.util.ArrayList;

public class RecycleActivity extends AppCompatActivity
{
    private Activity activity;

    private EditText et_rv_item_name, et_rv_item_age;

    private Button btn_rv_item_add;

    private ImageButton btn_recycle_back;

    /*
    기존의 ListView는 커스터마이징 하기에 힘들었고, 구조적인 문제로 성능상의 문제가 있었다.
    RecyclerView는 ListView의 문제를 해결하기위해 개발자에게 더 다양한 형태로 커스터마이징 할수 있도록 제공되었다.
    RecyclerView와 ListView의 가장 큰 차이점은 LayoutManager와 ViewHolder패턴의 의무적인 사용
    Item에 대한 뷰의 변형이나 애니메이션 할 수 있는 개념이 추가
    */
    private RecyclerView rv_item;

    private RecycleAdapter recycleAdapter;

    private ArrayList<RecycleMemberVo> memberList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.activity_recycle);

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

        et_rv_item_name = findViewById(R.id.et_rv_item_name);

        et_rv_item_age = findViewById(R.id.et_rv_item_age);

        btn_rv_item_add = findViewById(R.id.btn_rv_item_add);

        btn_recycle_back = findViewById(R.id.btn_recycle_back);

        rv_item = findViewById(R.id.rv_item);

        memberList = new ArrayList<>();

        recycleAdapter = new RecycleAdapter(activity, memberList);
    }

    private void setting()
    {
        rv_item.setAdapter(recycleAdapter);

        // 리사이클 뷰 아이템의 수평 배치 갯수를 설정
        GridLayoutManager gManager = new GridLayoutManager(activity, 1);

        rv_item.setLayoutManager(gManager);
    }

    private void addListener()
    {
        btn_rv_item_add.setOnClickListener(listener_item_add);

        btn_recycle_back.setOnClickListener(listener_back_click);
    }

    private View.OnClickListener listener_item_add = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            String name = et_rv_item_name.getText().toString();
            String age = et_rv_item_age.getText().toString();

            recycleAdapter.addItem(new RecycleMemberVo(name, age));
        }
    };

    private View.OnClickListener listener_back_click = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            finish();
        }
    };
}
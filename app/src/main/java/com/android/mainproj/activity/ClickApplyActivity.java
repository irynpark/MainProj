package com.android.mainproj.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mainproj.R;
import com.android.mainproj.adapter.ClickApplyAdapter;
import com.android.mainproj.log.LogService;

import java.util.ArrayList;
import java.util.List;

public class ClickApplyActivity extends AppCompatActivity implements View.OnClickListener, View.OnCreateContextMenuListener
{
    private Activity activity;

    private ImageButton btn_click_apply_back;

    private EditText et_click_item_add;

    private Button btn_click_item_add;

    private RecyclerView rv_click_apply;

    private ClickApplyAdapter clickApplyAdapter;

    private List<String> itemNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.activity_click_apply);

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

        btn_click_apply_back = findViewById(R.id.btn_click_apply_back);

        et_click_item_add = findViewById(R.id.et_click_item_add);

        btn_click_item_add = findViewById(R.id.btn_click_item_add);

        rv_click_apply = findViewById(R.id.rv_click_apply);

        itemNameList = new ArrayList<>();

        clickApplyAdapter = new ClickApplyAdapter(this, itemNameList, this, this);
    }

    private void setting()
    {
        rv_click_apply.setAdapter(clickApplyAdapter);;

        GridLayoutManager gManager = new GridLayoutManager(activity, 1);

        rv_click_apply.setLayoutManager(gManager);
    }

    private void addListener()
    {
        btn_click_apply_back.setOnClickListener(this);

        btn_click_item_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();

        if(id == R.id.btn_click_apply_back)
        {
            finish();
        }
        else if(id == R.id.btn_click_item_add)
        {
            String item = et_click_item_add.getText().toString();

            if(item.isEmpty() == false)
            {
                itemNameList.add(item);

                clickApplyAdapter.notifyDataSetChanged();
            }
            else
            {
                Toast.makeText(activity, "추가할 아이템 이름을 입력하여 주세요", Toast.LENGTH_SHORT).show();
            }
        }
        else if(id == R.id.btn_click_item)
        {
            String tag = v.getTag().toString();

            Toast.makeText(activity, tag + " 클릭", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        clickApplyAdapter.setCurrentPosition((int) v.getTag());

        menu.setHeaderTitle("텍스트 배경 변경");
        menu.add(0, 201, 0, "빨강");
        menu.add(0, 202, 0, "파랑");
        menu.add(0, 203, 0, "초록");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();

        int postion = clickApplyAdapter.getCurrentPosition();

        TextView tv_click_name = rv_click_apply.findViewHolderForLayoutPosition(postion).itemView.findViewById(R.id.tv_click_name);

        if(id == 201)
        {
            tv_click_name.setBackgroundColor(Color.RED);
        }
        else if(id == 202)
        {
            tv_click_name.setBackgroundColor(Color.BLUE);
        }
        else if(id == 203)
        {
            tv_click_name.setBackgroundColor(Color.GREEN);
        }

        return super.onContextItemSelected(item);
    }
}
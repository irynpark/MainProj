package com.android.mainproj.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.mainproj.R;
import com.android.mainproj.log.LogService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListActivity extends AppCompatActivity
{
    private Activity activity;

    private EditText et_item;

    private Button btn_item_add, btn_item_edit, btn_item_del;

    private ImageButton btn_array_back;

    private ListView lv_array;

    private ArrayAdapter arrayAdapter;

    private List<String> itemList = new ArrayList<String>(Arrays.asList("첫 번째", "두 번째"));

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.activity_array);

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

        et_item = findViewById(R.id.et_array_item);

        btn_item_add = findViewById(R.id.btn_array_item_add);

        btn_item_edit = findViewById(R.id.btn_array_item_edit);

        btn_item_del = findViewById(R.id.btn_array_item_del);

        btn_array_back = findViewById(R.id.btn_array_back);

        lv_array = findViewById(R.id.lv_array);

        arrayAdapter = new ArrayAdapter<String>(ArrayListActivity.this, android.R.layout.simple_list_item_single_choice, itemList);
        // listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, main_item);
    }

    private void setting()
    {
        lv_array.setAdapter(arrayAdapter);

        lv_array.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    private void addListener()
    {
        lv_array.setOnItemClickListener(listener_item_click);

        btn_item_add.setOnClickListener(listener_item_add);

        btn_item_edit.setOnClickListener(listener_item_edit);

        btn_item_del.setOnClickListener(listener_item_del);

        btn_array_back.setOnClickListener(listener_back_click);

        lv_array.setOnItemClickListener(listener_item_click);
    }

    private AdapterView.OnItemClickListener listener_item_click = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            String item = itemList.get(position);
            Toast.makeText(ArrayListActivity.this, position + " : " + item, Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener listener_item_add = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            itemList.add(et_item.getText().toString());
            arrayAdapter.notifyDataSetChanged();
        }
    };

    private View.OnClickListener listener_item_edit = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int index = lv_array.getCheckedItemPosition();

            if(index < 0)
            {
                Toast.makeText(ArrayListActivity.this, "편집 대상을 선택하세요.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                itemList.set(index, et_item.getText().toString());
                lv_array.clearChoices();
                arrayAdapter.notifyDataSetChanged();
            }
        }
    };

    private View.OnClickListener listener_item_del = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int index = lv_array.getCheckedItemPosition();

            if(index < 0)
            {
                Toast.makeText(ArrayListActivity.this, "삭제 대상을 선택하세요.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                itemList.remove(index);
                lv_array.clearChoices();
                arrayAdapter.notifyDataSetChanged();
            }
        }
    };

    private View.OnClickListener listener_back_click = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            finish();
        }
    };
}
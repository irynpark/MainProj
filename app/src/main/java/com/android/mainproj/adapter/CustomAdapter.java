package com.android.mainproj.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mainproj.R;
import com.android.mainproj.vo.CustomMemberVo;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter
{
    private Activity activity;

    private List<CustomMemberVo> memberList;

    private CustomHolder holder;

    public class CustomHolder
    {
        private ImageView iv_custom_profile;
        private TextView tv_custom_item_name;
        private TextView tv_custom_item_age;
    }

    public CustomAdapter(Activity activity, List<CustomMemberVo> memberList)
    {
        this.activity = activity;

        this.memberList = memberList;
    }

    @Override
    public int getCount()
    {
        return memberList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return memberList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_custom_list, parent, false);

            holder = new CustomHolder();

            holder.iv_custom_profile = convertView.findViewById(R.id.iv_custom_profile);
            holder.tv_custom_item_name = convertView.findViewById(R.id.tv_custom_item_name);
            holder.tv_custom_item_age = convertView.findViewById(R.id.tv_custom_item_age);

            convertView.setTag(holder);
        }
        else
        {
            holder = (CustomHolder) convertView.getTag();
        }

        holder.tv_custom_item_name.setText(memberList.get(position).getName());

        holder.tv_custom_item_age.setText(memberList.get(position).getAge());

        if (position % 2 == 1)
        {
            holder.iv_custom_profile.setImageResource(R.drawable.icon_woman_profile);
        }

        return convertView;
    }

    public void addItem(CustomMemberVo customMemberVo)
    {
        memberList.add(customMemberVo);
        notifyDataSetChanged();
    }
}

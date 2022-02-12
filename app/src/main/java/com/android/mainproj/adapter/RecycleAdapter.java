package com.android.mainproj.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mainproj.R;
import com.android.mainproj.vo.RecycleMemberVo;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.RecycleHolder>
{
    private Activity activity;

    private ArrayList<RecycleMemberVo> memberList;

    public RecycleAdapter(Activity activity, ArrayList<RecycleMemberVo> memberList)
    {
        this.activity = activity;

        this.memberList = memberList;
    }

    public class RecycleHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout layout_recycle_item;
        private ImageView iv_recycle_profile;
        private TextView tv_recycle_item_name;
        private TextView tv_recycle_item_age;

        public RecycleHolder(@NonNull View itemView)
        {
            super(itemView);

            layout_recycle_item = itemView.findViewById(R.id.layout_recycle_item);
            iv_recycle_profile = itemView.findViewById(R.id.iv_recycle_profile);
            tv_recycle_item_name = itemView.findViewById(R.id.tv_recycle_item_name);
            tv_recycle_item_age = itemView.findViewById(R.id.tv_recycle_item_age);
        }
    }

    @NonNull
    @Override
    public RecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_recycle, parent, false);

        RecycleHolder recycleHolder = new RecycleHolder(view);

        return recycleHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleHolder holder, int position)
    {
        holder.tv_recycle_item_name.setText(memberList.get(position).getName());

        holder.tv_recycle_item_age.setText(memberList.get(position).getAge());

        if(position % 2 == 1)
        {
            holder.iv_recycle_profile.setImageResource(R.drawable.icon_woman_profile);

        }

        holder.layout_recycle_item.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.down));

    }

    @Override
    public int getItemCount()
    {
        return memberList.size();
    }

    public void addItem(RecycleMemberVo recycleMemberVo)
    {
        memberList.add(recycleMemberVo);

        notifyDataSetChanged();
    }
}
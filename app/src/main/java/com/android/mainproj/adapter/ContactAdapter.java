package com.android.mainproj.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mainproj.R;
import com.android.mainproj.vo.ContactMemberVo;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder>
{
    private Activity activity;

    private List<ContactMemberVo> contactMemberList;

    private View.OnClickListener clickListener;

    public ContactAdapter(Activity activity, List<ContactMemberVo> contactMemberList, View.OnClickListener clickListener)
    {
        this.activity = activity;

        this.contactMemberList = contactMemberList;

        this.clickListener = clickListener;
    }

    public class ContactHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout layout_phone_name;

        private LinearLayout layout_phone_number;

        private TextView tv_phone_name;

        private TextView tv_phone_number;

        public ContactHolder(@NonNull View itemView)
        {
            super(itemView);

            layout_phone_name = itemView.findViewById(R.id.layout_phone_name);

            layout_phone_number = itemView.findViewById(R.id.layout_phone_number);

            tv_phone_name = itemView.findViewById(R.id.tv_phone_name);

            tv_phone_number = itemView.findViewById(R.id.tv_phone_number);
        }
    }

    @NonNull
    @Override
    public ContactAdapter.ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.item_contact_info, parent, false);

        ContactHolder holder = new ContactHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactHolder holder, int position)
    {
        holder.tv_phone_name.setText(contactMemberList.get(position).getName());

        holder.tv_phone_number.setText(contactMemberList.get(position).getNumber());

        holder.layout_phone_name.setOnClickListener(clickListener);

        holder.layout_phone_name.setTag(position);

        holder.layout_phone_number.setTag(false);
    }

    @Override
    public int getItemCount() {
        return contactMemberList.size();
    }
}
package com.android.mainproj.listener;

import android.app.Activity;
import android.widget.RadioGroup;

import com.android.mainproj.log.LogService;

public class RadioCheckedChangeListener implements RadioGroup.OnCheckedChangeListener
{
    private Activity activity;

    private RadioGroup radioGroup;

    private int beforeCheckedId;

    private OnCheckedChangeListener callBack;

    public interface OnCheckedChangeListener
    {
        public void onCheckedChanged(RadioGroup group, int checkedId, int beforeCheckedId);
    }

    public RadioCheckedChangeListener(Activity activity, RadioGroup radioGroup, OnCheckedChangeListener callBack)
    {
        this.activity = activity;
        this.radioGroup = radioGroup;
        this.beforeCheckedId = radioGroup.getCheckedRadioButtonId();
        this.callBack = callBack;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        this.onCheckedChanged(group, checkedId, beforeCheckedId);

        LogService.info(activity, "라디오 버튼 변경 확인");
        LogService.info(activity, "beforeCheckedId : " + beforeCheckedId);
        LogService.info(activity, "checkedId : " + checkedId);

        this.beforeCheckedId = checkedId;
    }

    public void onCheckedChanged(RadioGroup group, int checkedId, int beforeCheckedId)
    {
        this.callBack.onCheckedChanged(group, checkedId, beforeCheckedId);

        this.beforeCheckedId = checkedId;
    }
}

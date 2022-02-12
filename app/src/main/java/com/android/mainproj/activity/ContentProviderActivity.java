package com.android.mainproj.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.android.mainproj.R;
import com.android.mainproj.adapter.ContactAdapter;
import com.android.mainproj.dialog.PermissionDialog;
import com.android.mainproj.log.LogService;
import com.android.mainproj.projection.CustomProjection;
import com.android.mainproj.vo.ContactMemberVo;

import java.util.ArrayList;
import java.util.List;

/*
    콘텐트 프로바이더는 애플리케이션 간의 데이터를 공휴할 수 있게 해주는 인터페이스 컴포넌트이다.
    일반적으로 사용자가 직접 만든 앱은 콘텐트 프로바이더를 제공하고 있지 않지만
    안드로이드에 기본 탑재되어 있는 주소록, 브라우저, 통화기록, 미디어갤러리, 환경설정 등은 기본적으로
    콘텐트 프로바이더를 제공하고 있다.
    하여 이들을 URL 방식으로 접근하여 데이터를 수정하거나 조회할수 있다.
    사용자가 직접 콘텐트 프로바이더를 이용하는 이유는 다양한데 기본적으로는 같은 회사 내의 앱끼리의 데이터 공유와
    앱스토어에 올릴수 있는 앱의 최대 크기에 따른 문제 때문에 앱을 나누어서 올리는 경우가 있다.
*/
public class ContentProviderActivity extends AppCompatActivity implements View.OnClickListener
{
    private final int REQUEST_CONTACTS_PERMISSION = 1004;

    private final int REQUEST_CONTACTS_DATA = 1005;

    private Activity activity;

    private ImageButton btn_provider_back;

    private Button btn_get_contact, btn_get_custom_provider;

    private RecyclerView rv_contact;

    private ContactAdapter contactAdapter;

    private List<ContactMemberVo> contactMemberList;

    private Boolean permFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.activity_content_provider);

            init();

            setting();

            addListener();
        }
        catch (Exception ex)
        {
            LogService.error(this, ex.getMessage(), ex);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        getPermission();
    }

    private void init()
    {
        activity = this;

        btn_provider_back = findViewById(R.id.btn_provider_back);

        btn_get_contact = findViewById(R.id.btn_get_contact);

        btn_get_custom_provider = findViewById(R.id.btn_get_custom_provider);

        rv_contact = findViewById(R.id.rv_contact);

        contactMemberList = new ArrayList<>();

        contactAdapter = new ContactAdapter(activity, contactMemberList, this);
    }

    private void setting()
    {
        rv_contact.setAdapter(contactAdapter);

        GridLayoutManager gManager = new GridLayoutManager(activity, 1);

        rv_contact.setLayoutManager(gManager);
    }

    private void addListener()
    {
        btn_provider_back.setOnClickListener(listener_back_click);

        btn_get_contact.setOnClickListener(listener_get_address);

        btn_get_custom_provider.setOnClickListener(listener_get_custom_provider);
    }

    private void getPermission()
    {
        if (permFlag = false)
        {
            permFlag = true;

            if(checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            {
                String[] permissions =
                        {
                                Manifest.permission.READ_CONTACTS
                        };

                requestPermissions(permissions, REQUEST_CONTACTS_PERMISSION);
            }
        }

    }

    private View.OnClickListener listener_back_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener listener_get_address = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            getContact();
        }
    };

    private View.OnClickListener listener_get_custom_provider = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Cursor cursor = null;

            try
            {
                cursor = getContentResolver().query(CustomProjection.ContactData.CONTENT_URI, null, null, null, null);

                ContactMemberVo tmpContactMemberVo;

                contactMemberList.clear();

                while(cursor.moveToNext())
                {
                    tmpContactMemberVo = new ContactMemberVo();

                    int idIndex = cursor.getColumnIndex(CustomProjection.ContactData.CONTACT_ID);
                    int nameIndex = cursor.getColumnIndex(CustomProjection.ContactData.CONTACT_NAME);
                    int numberIndex = cursor.getColumnIndex(CustomProjection.ContactData.CONTACT_NUMBER);

                    String id = String.valueOf(cursor.getInt(idIndex));
                    String name = cursor.getString(nameIndex);
                    String number = cursor.getString(numberIndex);

                    tmpContactMemberVo.setId(id);
                    tmpContactMemberVo.setName(name);
                    tmpContactMemberVo.setNumber(number);

                    contactMemberList.add(tmpContactMemberVo);
                }
                contactAdapter.notifyDataSetChanged();
            }
            catch (Exception ex)
            {
                LogService.error(activity, ex.getMessage(), ex);
            }
            finally
            {
                if (cursor != null)
                {
                    cursor.close();
                }
            }
        }
    };

    private void getContact()
    {
        if(checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
        {
            String[] permissions =
                    {
                            Manifest.permission.READ_CONTACTS
                    };

            requestPermissions(permissions, REQUEST_CONTACTS_DATA);
        }
        else
        {
            setContactInfo();
        }
    }

    private void setContactInfo()
    {
        Cursor contacts = null;

        try
        {
            String[] projection =
                    {
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    };

            contacts = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, null);

            ContactMemberVo tmpContactInfo;

            contactMemberList.clear();

            while(contacts.moveToNext())
            {
                tmpContactInfo = new ContactMemberVo();

                int idIndex = contacts.getColumnIndex(projection[0]);
                int nameIndex = contacts.getColumnIndex(projection[1]);
                int numberIndex = contacts.getColumnIndex(projection[2]);

                String id = contacts.getString(idIndex);
                String name = contacts.getString(nameIndex);
                String number = contacts.getString(numberIndex);

                tmpContactInfo.setId(id);
                tmpContactInfo.setName(name);
                tmpContactInfo.setNumber(number);

                contactMemberList.add(tmpContactInfo);
            }

            contactAdapter.notifyDataSetChanged();
        }
        catch (SecurityException sEx)
        {
            LogService.error(activity, "앱 내 주소록 권한이 설정되어 있지 않습니다.", sEx);
            finish();
        }
        catch (Exception ex)
        {
            LogService.error(activity, ex.getMessage(), ex);
            finish();
        }
        finally
        {
            if(contacts != null)
            {
                contacts.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CONTACTS_PERMISSION ||requestCode == REQUEST_CONTACTS_DATA)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                if (requestCode == REQUEST_CONTACTS_DATA)
                {
                    setContactInfo();
                }

            }
            else
            {
                PermissionDialog dialog = new PermissionDialog(activity, "주소록");

                dialog.setDialogOnClickListener(new PermissionDialog.OnDialogClickListener()
                {
                    @Override
                    public void onYesClick()
                    {
                        Intent appDetail = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));

                        // 시스템 카테고리라고 지정
                        appDetail.addCategory(Intent.CATEGORY_DEFAULT);

                        // 현재 작업을 백그라운드로 이동시키고 새 작업 실행
                        appDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(appDetail);

                        permFlag = false;
                    }

                    @Override
                    public void onNoClick()
                    {
                        finish();
                    }
                });
                dialog.show();
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.layout_phone_name)
        {
            int position = (int) v.getTag();

            LinearLayout layout = (rv_contact.findViewHolderForLayoutPosition(position).itemView.findViewById(R.id.layout_phone_number));

            boolean status = (boolean) layout.getTag();

            if (status == false)
            {
                layout.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.down));
                layout.setVisibility(View.VISIBLE);
                layout.setTag(true);
            }
            else
            {
                layout.setVisibility(View.GONE);
                layout.setTag(false);
            }
        }
    }
}
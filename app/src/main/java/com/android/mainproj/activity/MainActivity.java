package com.android.mainproj.activity;

import static com.android.mainproj.config.RequestCodeConfig.REQ_CODE;
import static com.android.mainproj.config.RequestCodeConfig.REQ_MAIN_ACTIVITY;
import static com.android.mainproj.config.ResultCodeConfig.RESULT_CODE_ACTIVITY_RESULT_CANCELED;
import static com.android.mainproj.config.ResultCodeConfig.RESULT_CODE_ACTIVITY_RESULT_OK;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.mainproj.R;
import com.android.mainproj.log.LogService;

public class MainActivity
		extends AppCompatActivity
{
	private Activity activity;

	private ImageButton btn_main_back;

	private ListView lv_main;

	private ListAdapter listAdapter;

	private String[] items =
			{
					"탭 뷰 활용", "리스트 뷰 응용", "네비게이션 뷰 활용", "커스텀 리스트 뷰 활용",
					"리사이클 뷰 응용", "코드 화면 작성", "액티비티 결과 확인", "클릭 이벤트 응용",
					"외부 폰트 사용", "공유 설정 활용", "스크롤 뷰 활용", "핸들러 활용", "커스텀 다이얼로그 활용",
					"비동기작업 사용", "컨텐트 프로바이더 사용", "브로드캐스트 리시버 사용", "푸시 알림 사용",
					"서비스 이용"
			};

	private String loginId;

	private ActivityResultLauncher<Intent> resultLauncher;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		try
		{
			setContentView(R.layout.activity_main);

			init();

			setting();

			addListener();
		}
		catch(Exception ex)
		{
			LogService.error(this, ex.getMessage(), ex);
		}
	}

	private void init()
	{
		activity = this;

		lv_main = findViewById(R.id.lv_main);

		btn_main_back = findViewById(R.id.btn_main_back);

		listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
	}

	private void setting()
	{
		resultLauncher = registerForActivityResult
				(
						new ActivityResultContracts.StartActivityForResult(),
						activityResultCallback
				);

		Intent intent = getIntent();

		loginId = intent.getStringExtra("ID");

		lv_main.setAdapter(listAdapter);
	}

	private void addListener()
	{
		lv_main.setOnItemClickListener(listener_item_click);

		btn_main_back.setOnClickListener(listener_back_click);
	}

	private AdapterView.OnItemClickListener listener_item_click = new AdapterView.OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			Intent intent = null;

			String item = items[position];

			if(item.equals("탭 뷰 활용"))
			{
				intent = new Intent(activity, TabActivity.class);
			}
			else if(item.equals("리스트 뷰 응용"))
			{
				intent = new Intent(activity, ArrayListActivity.class);
			}
			else if(item.equals("네비게이션 뷰 활용"))
			{
				intent = new Intent(activity, NaviActivity.class);

				intent.putExtra("ID", loginId);
			}
			else if(item.equals("커스텀 리스트 뷰 활용"))
			{
				intent = new Intent(activity, CustomListActivity.class);
			}
			else if(item.equals("리사이클 뷰 응용"))
			{
				intent = new Intent(activity, RecycleActivity.class);
			}
			else if(item.equals("코드 화면 작성"))
			{
				intent = new Intent(activity, CodeActivity.class);
			}
			else if(item.equals("액티비티 결과 확인"))
			{
				intent = new Intent(activity, ResultCodeActivity.class);
				intent.putExtra(REQ_CODE, REQ_MAIN_ACTIVITY);
			}
			else if(item.equals("클릭 이벤트 응용"))
			{
				intent = new Intent(activity, ClickApplyActivity.class);
			}
			else if(item.equals("외부 폰트 사용"))
			{
				intent = new Intent(activity, FontActivity.class);
			}
			else if(item.equals("공유 설정 활용"))
			{
				intent = new Intent(activity, SharedPrefActivity.class);
			}
			else if(item.equals("스크롤 뷰 활용"))
			{
				intent = new Intent(activity, ScrollActivity.class);
			}
			else if(item.equals("핸들러 활용"))
			{
				intent = new Intent(activity, HandlerActivity.class);
			}
			else if(item.equals("커스텀 다이얼로그 활용"))
			{
				intent = new Intent(activity, CustomDialogActivity.class);
			}
			else if(item.equals("비동기작업 사용"))
			{
				intent = new Intent(activity, AsyncTaskActivity.class);
			}
			else if(item.equals("컨텐트 프로바이더 사용"))
			{
				intent = new Intent(activity, ContentProviderActivity.class);
			}
			else if(item.equals("브로드캐스트 리시버 사용"))
			{
				intent = new Intent(activity, BroadCastReceiverActivity.class);
			}
			else if(item.equals("푸시 알림 사용"))
			{
				intent = new Intent(activity, NotificationActivity.class);
			}
			else if(item.equals("서비스 이용"))
			{
				intent = new Intent(activity, ServiceActivity.class);
			}


			if(intent != null)
			{
				//startActivity(intent);
				resultLauncher.launch(intent);

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

	private ActivityResultCallback<ActivityResult> activityResultCallback = new ActivityResultCallback<ActivityResult>()
	{
		@Override
		public void onActivityResult(ActivityResult result)
		{
			int resultCode = result.getResultCode();

			Intent intent = result.getData();

			if(resultCode == RESULT_CODE_ACTIVITY_RESULT_OK)
			{
				LogService.info(activity, "ResultCodeActivity 화면 결과 성공 로직 처리");

				if(intent != null)
				{
					String data = intent.getStringExtra("DATA");
					LogService.info(activity, intent.getStringExtra("DATA"));

					if(data.equals("CODE_MOVE"))
					{
						Intent moveIntent = new Intent(activity, CodeActivity.class);
						resultLauncher.launch(moveIntent);
					}
					else if(data.equals("CODE_CUSTOM"))
					{
						Intent moveIntent = new Intent(activity, CustomListActivity.class);
						resultLauncher.launch(moveIntent);
					}
				}


			}
			else if(resultCode == RESULT_CODE_ACTIVITY_RESULT_CANCELED)
			{
				LogService.info(activity, "ResultCodeActivity 화면 결과 취소 로직 처리");
			}
		}
	};
}
package com.android.mainproj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.mainproj.R;
import com.android.mainproj.log.LogService;

public class UrlSchemaActivity
		extends AppCompatActivity
{
	private Activity activity;

	private ImageButton btn_schema_back;

	private EditText et_run_package, et_schema_send_id, et_schema_send_msg;

	private TextView tv_schema_res_msg;

	private Button btn_run_package, btn_url_schema;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		try
		{
			setContentView(R.layout.activity_url_schema);

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

		btn_url_schema = findViewById(R.id.btn_url_schema);

		btn_schema_back = findViewById(R.id.btn_schema_back);

		btn_run_package = findViewById(R.id.btn_run_package);

		et_run_package = findViewById(R.id.et_run_package);

		et_schema_send_id = findViewById(R.id.et_schema_send_id);

		et_schema_send_msg = findViewById(R.id.et_schema_send_msg);

		tv_schema_res_msg = findViewById(R.id.tv_schema_res_msg);

	}

	private void setting()
	{
		checkURLSchema();
	}

	private void addListener()
	{
		btn_schema_back.setOnClickListener(listener_back_click);

		btn_run_package.setOnClickListener(listener_run_package);

		btn_url_schema.setOnClickListener(listener_url_schema);
	}

	private void checkURLSchema()
	{
		Intent intent = getIntent();

		if(Intent.ACTION_VIEW.equals(intent.getAction()))
		{
			Uri uri = intent.getData();

			if(uri != null)
			{
				if(uri.getScheme().equals("mpp") && uri.getHost().equals("android"))
				{
					String message = uri.getQueryParameter("message");

					tv_schema_res_msg.setText(message);
				}
			}
		}
	}

	private View.OnClickListener listener_back_click = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			finish();
		}
	};

	private View.OnClickListener listener_run_package = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			String packageName = et_run_package.getText().toString();

			if(packageName.isEmpty() == false)
			{
				Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);

				if(intent == null)
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(activity);
					builder.setMessage("앱이 설치되지 않았습니다. 앱스토어에서 설치 진행하시겠슴까?");

					builder.setPositiveButton("예", new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							// ACTION_VIEW : 대상앱에게 데이터를 넘길수 있음
							Intent marketIntent = new Intent(Intent.ACTION_VIEW);
							marketIntent.setData(Uri.parse("market://detail?id" + packageName));
							startActivity(marketIntent);
						}
					});

					builder.setNegativeButton("아니오", new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							dialog.dismiss();
						}
					});

					builder.setCancelable(false);
					builder.show();
				}
				else
				{
					intent.addCategory(Intent.CATEGORY_DEFAULT);

					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

					startActivity(intent);
				}
			}
		}
	};

	private View.OnClickListener listener_url_schema = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			try
			{
				String id = et_schema_send_id.getText().toString();
				String msg = et_schema_send_msg.getText().toString();

				String urlSchema = "supp://android?id=" + id + "&message=" + msg;

				Intent intent = Intent.parseUri(urlSchema, Intent.URI_INTENT_SCHEME);

				startActivity(intent);
			}
			catch(Exception ex)
			{
				LogService.error(activity, ex.getMessage(), ex);
			}

		}
	};
}
package com.android.mainproj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.mainproj.R;
import com.android.mainproj.log.LogService;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*
자바에서 제공하는 쓰레드를 사용할 경우 안드로이드에서 신경써야 할 부분이 많다.
하여 핸들러를 사용하여 메인 스레드에 영향을 주지 않게 동작하게 하며
어싱크태스크를 사용하여 백그라운드에서 원격의 이미지 파일을 다운로드 하는 등의
소비적인 작업을 메인 스레드에 영향을 주지 않으면서 처리하였다.
그렇게 사용하던 AsyncTask 가 메모리 누수등의 문제로 인해
2019년 11월 8일 Deplecate 되었다.
하여 AsyncTask의 대체수단인 rxjava 에 대해서 알아보자.
*/
public class AsyncTaskActivity extends AppCompatActivity {

    private Activity activity;

    private Disposable backgroundtask;

    private ProgressDialog progress;

    private ImageButton btn_async_back;

    private TextView tv_server_conn_status;

    private Button btn_server_conn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.activity_async);

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

        btn_async_back = findViewById(R.id.btn_async_back);

        tv_server_conn_status = findViewById(R.id.tv_server_conn_status);

        btn_server_conn = findViewById(R.id.btn_server_conn);;
    }

    private void setting()
    {
        asyncTask();
    }

    private void addListener()
    {
        btn_async_back.setOnClickListener(listener_back_click);

        btn_server_conn.setOnClickListener(listener_server_conn);
    }

    private View.OnClickListener listener_back_click = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            finish();
        }
    };

    private View.OnClickListener listener_server_conn = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            asyncTask();
        }
    };

    private void disableUI()
    {
        btn_async_back.setEnabled(false);

        btn_server_conn.setEnabled(false);
    }

    private void enableUI()
    {
        btn_async_back.setEnabled(true);

        btn_server_conn.setEnabled(true);
    }

    private void asyncTask()
    {
        // onPreExecute (백그라운드 작업전의 코드를 이곳에 작성)
        disableUI();

        progress = new ProgressDialog(activity);
        progress.setMessage("서버 접속 시도중입니다...");
        progress.setCancelable(false);
        progress.show();

        backgroundtask = Observable.fromCallable(new Callable<Object>()
        {
            @Override
            public Object call() throws Exception
            {
                String data = "";

                // doInBackground (백그라운드 작업 코드를 이곳에 작성)
                try
                {
                    data = connectServer();
                }
                catch (Exception ex)
                {
                    LogService.error(activity, ex.getMessage(), ex);
                }

                return data;
            }
        })
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(Object o) throws Exception
                    {
                        LogService.info(activity, o.toString());

                        // onPostExecute (백그라운드 작업 후의 코드를 이곳에 작성
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                                tv_server_conn_status.setText(o.toString());

                                enableUI();
                            }
                        });

                        backgroundtask.dispose();

                        progress.dismiss();
                    }
                });
    }

    private String connectServer()
    {
        String addr = "http://172.30.1.16:8081/conn";

        String resBody = "";

        InputStream inputStream = null;

        InputStreamReader inputStreamReader = null;

        BufferedReader bufferedReader = null;

        StringBuffer sb = null;

        String resultMsg = "";

        try
        {
            URL url = new URL(addr);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 연결 최대 시간을 3초로 설정
            connection.setConnectTimeout(1000);

            connection.setReadTimeout(1000);

            connection.setRequestMethod("GET");

            connection.setRequestProperty("Accept", "application/json");

            connection.setRequestProperty("Content-Type", "application/json");

            // API 28 버전 이후부터는 http 접속을 사용하려면
            // AndroidManifest 파일에
            // <uses-permission android:name="android.permission.INTERNET /> 을 추가하여 주어야한다.
            // 또한 https 통신이 아닌 경우에는
            // android:usesCleartextTraffic="true" 옵션도 추가하여 주어야한다.
            connection.connect();

            int code = connection.getResponseCode();

            LogService.info(activity, "resCode : " + code);

            inputStream = connection.getInputStream();

            inputStreamReader = new InputStreamReader(inputStream);

            bufferedReader = new BufferedReader(inputStreamReader);

            sb = new StringBuffer();

            String line;

            while((line = bufferedReader.readLine()) != null)
            {
                sb.append(line);
            }

            resBody = sb.toString();

            connection.disconnect();

            LogService.info(activity, resBody);

            JSONObject jsonObject = new JSONObject(resBody);

            if(jsonObject.get("result").equals(true))
            {
                resultMsg = (String) jsonObject.get("msg");
            }
        }
        catch (ConnectException coEx)
        {
            resultMsg = "서버 연결에 실패하였습니다.";
            LogService.error(activity, resultMsg, coEx);
        }
        catch (SocketTimeoutException soEx)
        {
            resultMsg = "서버 연결 시간이 초과되어 접속이 중지되었습니다. 잠시후 재접속해주세요.";
            LogService.error(activity, resultMsg, soEx);
        }
        catch (Exception ex)
        {
            resultMsg = "서버 연결 중 알수 없는 에러가 발생하였습니다.";
            LogService.error(activity, resultMsg, ex);
        }
        finally
        {
            try
            {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }

                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }

                if (inputStream != null) {
                    inputStream.close();
                }
            }
            catch (IOException ioEx)
            {
                LogService.error(activity, ioEx.getMessage());
            }
        }

        return resultMsg;
    }
}
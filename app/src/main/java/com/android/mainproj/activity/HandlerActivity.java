package com.android.mainproj.activity;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mainproj.R;
import com.android.mainproj.listener.RadioCheckedChangeListener;
import com.android.mainproj.log.LogService;

import java.util.Timer;
import java.util.TimerTask;

public class HandlerActivity extends AppCompatActivity
{
    private Activity activity;

    private TextView tv_handler_time;

    private ImageButton btn_handler_back;

    private Button btn_not_handler_start, btn_handler_start, btn_handler_stop;

    private Button btn_handler_delay_start, btn_handler_delay_stop;

    private Button btn_timer_start, btn_timer_stop;

    private RadioGroup rg_timer;

    private Thread errorThread;

    private Thread handlerThread;

    private Handler handler;

    private Timer timer;

    private HandlerTask handlerTask;

    private int count = 0;

    private int taskMode = 1;

    private final int SEND_INFO = 0;

    public class HandlerTask extends TimerTask
    {
        private TextView tv_handler_time;

        private int taskMode;

        private int count;

        private boolean checkRun = false;

        public boolean hasStarted()
        {
            return checkRun;
        }

        public int getCount()
        {
            return count;
        }

        private HandlerTask(TextView tv_handler_time, int taskMode, int count)
        {
            this.tv_handler_time = tv_handler_time;

            this.taskMode = taskMode;

            this.count = count;
        }

        @Override
        public void run()
        {
            checkRun = true;

            count++;

            if(taskMode == 1)
            {
                LogService.info(activity, "위젯에 post 호출");

                tv_handler_time.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        tv_handler_time.setText(" - " + count + " - ");
                    }
                });
            }
            else if(taskMode == 2)
            {
                LogService.info(activity, "runOnUiThread 호출");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        tv_handler_time.setText(" - " + count + " - ");
                    }
                });
            }
            else if(taskMode == 3)
            {
                LogService.info(activity, "handler 사용 호출");

                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        tv_handler_time.setText(" - " + count + " - ");
                    }
                });
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.activity_handler);

            init();

            setting();

            addListener();
        }
        catch (Exception ex)
        {
            LogService.error(activity, ex.getMessage(), ex);
        }
    }

    private void init()
    {
        activity = this;

        timer = new Timer();

        tv_handler_time = findViewById(R.id.tv_handler_time);

        btn_handler_back = findViewById(R.id.btn_handler_back);

        btn_not_handler_start = findViewById(R.id.btn_not_handler_start);

        btn_handler_start = findViewById(R.id.btn_handler_start);

        btn_handler_stop = findViewById(R.id.btn_handler_stop);

        btn_handler_delay_start = findViewById(R.id.btn_handler_delay_start);

        btn_handler_delay_stop = findViewById(R.id.btn_handler_delay_stop);

        btn_timer_start = findViewById(R.id.btn_timer_start);

        btn_timer_stop = findViewById(R.id.btn_timer_stop);

        rg_timer = findViewById(R.id.rg_timer);
    }

    private void setting()
    {
        errorThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(true)
                {
                    count++;

                    try
                    {
                        tv_handler_time.setText(" - " + count + " - ");

                        sleep(1000);
                    }
                    catch (Exception ex)
                    {
                        LogService.error(activity, ex.getMessage(), ex);
                        break;
                    }
                }
            }
        });

        handlerThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(true)
                {
                    count++;

                    try
                    {
                        Message msg = handler.obtainMessage();
                        msg.what = SEND_INFO;
                        msg.obj = " - " + count + " - ";

                        handler.sendMessage(msg);

                        LogService.info(activity, "쓰레드 실행 확인");

                        sleep(1000);
                    }
                    catch (InterruptedException iex)
                    {
                        LogService.info(activity, "쓰레드 종료");
                        break;
                    }
                    catch (Exception ex)
                    {
                        LogService.error(activity, ex.getMessage(), ex);
                        break;
                    }
                }
            }
        });

        handler = new Handler(Looper.getMainLooper())
        {
            @Override
            public void handleMessage(@NonNull Message msg)
            {
                super.handleMessage(msg);

                if(msg.what == SEND_INFO)
                {
                    String data = (String) msg.obj;
                    tv_handler_time.setText(data);
                }
            }
        };

        handlerTask = new HandlerTask(tv_handler_time, taskMode, count);
    }

    private void addListener()
    {
        btn_handler_back.setOnClickListener(listener_back_click);

        btn_not_handler_start.setOnClickListener(listener_not_handler_start);

        btn_handler_start.setOnClickListener(listener_handle_start);

        btn_handler_stop.setOnClickListener(listener_handle_stop);

        btn_handler_delay_start.setOnClickListener(listener_handler_delay_start);

        btn_handler_delay_stop.setOnClickListener(listener_handler_delay_stop);

        btn_timer_start.setOnClickListener(listener_timer_start);

        btn_timer_stop.setOnClickListener(listener_timer_stop);

        rg_timer.setOnCheckedChangeListener(new RadioCheckedChangeListener(activity, rg_timer, listener_timer_mode_change));
    }

    private View.OnClickListener listener_back_click = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            finish();
        }
    };

    private View.OnClickListener listener_timer_start = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if(handlerTask.hasStarted() == false)
            {
                timer.schedule(handlerTask, 0, 1000);
            }
        }
    };

    private View.OnClickListener listener_timer_stop = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            handlerTask.cancel();
            count = handlerTask.getCount();
            handlerTask = new HandlerTask(tv_handler_time, taskMode, count);
        }
    };

    private View.OnClickListener listener_not_handler_start = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            errorThread.start();
        }
    };

    private View.OnClickListener listener_handle_start = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            LogService.info(activity, handlerThread.getState().name());

            if
            (
                    handlerThread.getState() == Thread.State.NEW ||
                            handlerThread.getState() == Thread.State.TERMINATED
            )
            {
                handlerThread.start();
            }
        }
    };

    private View.OnClickListener listener_handle_stop = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            handlerThread.interrupt();

            LogService.info(activity, "핸들러 종료");
        }
    };

    private View.OnClickListener listener_handler_delay_start = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    Toast.makeText(activity, "핸들러 지연 실행" , Toast.LENGTH_SHORT).show();
                }
            }, 3000);
        }
    };

    private View.OnClickListener listener_handler_delay_stop = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            handler.removeCallbacksAndMessages(null);
        }
    };

    private RadioCheckedChangeListener.OnCheckedChangeListener listener_timer_mode_change = new RadioCheckedChangeListener.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId, int beforeCheckedId)
        {
            if(checkedId == R.id.rb_timer_wiget)
            {
                taskMode = 1;
            }
            else if(checkedId == R.id.rb_timer_runonui)
            {
                taskMode = 2;
            }
            else if(checkedId == R.id.rb_timer_handler)
            {
                taskMode = 3;
            }

            if(handlerTask.hasStarted() == true)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("타이머가 이미 실행중입니다.\n현재 실행중인 타이머를 중지하고 변경하시겠습니까?");

                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        listener_timer_stop.onClick(btn_timer_stop);
                        handlerTask = new HandlerTask(tv_handler_time, taskMode, count);
                    }
                });

                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        rg_timer.setOnCheckedChangeListener(null);

                        if(beforeCheckedId == R.id.rb_timer_wiget)
                        {
                            taskMode = 1;

                            ((RadioButton)rg_timer.getChildAt(0)).setChecked(true);
                        }
                        else if(beforeCheckedId == R.id.rb_timer_runonui)
                        {
                            taskMode = 2;

                            ((RadioButton)rg_timer.getChildAt(1)).setChecked(true);
                        }
                        else if(beforeCheckedId == R.id.rb_timer_handler)
                        {
                            taskMode = 3;

                            ((RadioButton)rg_timer.getChildAt(2)).setChecked(true);
                        }

                        rg_timer.setOnCheckedChangeListener(new RadioCheckedChangeListener(activity, rg_timer, listener_timer_mode_change));
                    }
                });

                builder.setCancelable(false);
                builder.show();
            }
            else
            {
                handlerTask = new HandlerTask(tv_handler_time, taskMode, count);
            }
        }
    };

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        handlerThread.interrupt();

        if(handlerTask.hasStarted() == true)
        {
            handlerTask.cancel();
        }

        handlerTask = null;

        LogService.info(activity, "화면 종료");
    }
}
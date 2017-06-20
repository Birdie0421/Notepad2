package edu.fjnu.birdie.notepad2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.fjnu.birdie.notepad2.Utils.NotePad;
import edu.fjnu.birdie.notepad2.Utils.NotesDB;

public class MemoActivity extends AppCompatActivity {

    private Button btnSetClock;
    private Button btnCloseClock;
    private AlarmManager alarmManager;
    private PendingIntent pi;
    private int Pid ;
    private int cid;
    private NotesDB DB;
    private String sql;
    private SQLiteDatabase dbread;
    private String clockTime = null;
    private int isSet = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
//        btnSetClock = (Button) findViewById(R.id.btnSetClock);
//        btnCloseClock = (Button) findViewById(R.id.btnCloseClock);

        Bundle myBundle = this.getIntent().getExtras();
        Pid = myBundle.getInt("pid");
        cid = Pid;
        Log.d("Pid_M",Pid+"");
        DB = new NotesDB(this);
        dbread = DB.getReadableDatabase();

        // ①获取AlarmManager对象:
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        // 指定要启动的是Activity组件,通过PendingIntent调用getActivity来设置
        Intent intent = new Intent(MemoActivity.this, ClockActivity.class);
        //intent.putExtra("cid",cid);
        pi = PendingIntent.getActivity(MemoActivity.this, Pid, intent, 0);

        Calendar currentTime = Calendar.getInstance();
        // 弹出一个时间设置的对话框,供用户选择时间
        new TimePickerDialog(MemoActivity.this, 0,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view,
                                          int hourOfDay, int minute) {
                        //设置当前时间
                        Calendar c = Calendar.getInstance();
                        c.setTimeInMillis(System.currentTimeMillis());
                        // 根据用户选择的时间来设置Calendar对象
                        c.set(Calendar.HOUR, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        Date date = new Date(c.getTimeInMillis());
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String s = String.valueOf(c.getTimeInMillis());
                        sql = "update note set memotime ='" + s + "' where _id=" + Pid;
                        dbread.execSQL(sql);
                        clockTime = format.format(date);
                        Log.d("clock",clockTime);
                        // ②设置AlarmManager在Calendar对应的时间启动Activity
                        alarmManager.set(AlarmManager.RTC_WAKEUP,
                                c.getTimeInMillis(), pi);
                        // 提示闹钟设置完毕:
                        Toast.makeText(MemoActivity.this, "将于"+clockTime+"提醒您",
                                Toast.LENGTH_SHORT).show();
                    }
                }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime
                .get(Calendar.MINUTE), false).show();

//        if(isSet == 1){
//            MemoActivity.this.finish();
//        }

//        btnSetClock.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar currentTime = Calendar.getInstance();
//                // 弹出一个时间设置的对话框,供用户选择时间
//                new TimePickerDialog(MemoActivity.this, 0,
//                        new TimePickerDialog.OnTimeSetListener() {
//                            @Override
//                            public void onTimeSet(TimePicker view,
//                                                  int hourOfDay, int minute) {
//                                //设置当前时间
//                                Calendar c = Calendar.getInstance();
//                                c.setTimeInMillis(System.currentTimeMillis());
//                                // 根据用户选择的时间来设置Calendar对象
//                                c.set(Calendar.HOUR, hourOfDay);
//                                c.set(Calendar.MINUTE, minute);
//                                Date date = new Date(c.getTimeInMillis());
//                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                                String s = String.valueOf(c.getTimeInMillis());
//                                sql = "update note set memotime ='" + s + "' where _id=" + Pid;
//                                dbread.execSQL(sql);
//                                clockTime = format.format(date);
//                                Log.d("clock",clockTime);
//                                // ②设置AlarmManager在Calendar对应的时间启动Activity
//                                alarmManager.set(AlarmManager.RTC_WAKEUP,
//                                        c.getTimeInMillis(), pi);
//                                // 提示闹钟设置完毕:
//                                Toast.makeText(MemoActivity.this, "将于"+clockTime+"提醒您",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime
//                        .get(Calendar.MINUTE), false).show();
//                btnCloseClock.setVisibility(View.VISIBLE);
//
//            }
//        });
//
//        btnCloseClock.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alarmManager.cancel(pi);
//                btnCloseClock.setVisibility(View.GONE);
//                Toast.makeText(MemoActivity.this, "闹钟已取消", Toast.LENGTH_SHORT)
//                        .show();
//            }
//        });
    }


}

package edu.fjnu.birdie.notepad2;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import edu.fjnu.birdie.notepad2.Utils.NotePad;
import edu.fjnu.birdie.notepad2.Utils.NotesDB;

public class ClockActivity extends Activity {

    static final int NOTIFICATION_ID = 0x123;
    private MediaPlayer mediaPlayer;
    private NotesDB DB;
    private String sql;
    private SQLiteDatabase dbread;
    private int Pid ;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        NotificationManager info=(NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        Log.d("111","222");
        Intent intent =new Intent(ClockActivity.this,MainActivity.class);
        PendingIntent pi= PendingIntent.getActivity(ClockActivity.this,0,intent,0);
        Notification notify=new Notification.Builder(this)
                // 设置打开该通知，该通知自动消失
                .setAutoCancel(true)
                // 设置显示在状态栏的通知提示信息
                .setTicker("有新消息")
                // 设置通知的图标
                .setSmallIcon(R.drawable.notepad_ic)
                // 设置通知内容的标题
                .setContentTitle("一条新通知")
                // 设置通知内容
                .setContentText("闹钟")
//                // 设置使用系统默认的声音、默认LED灯
                .setDefaults(Notification.DEFAULT_SOUND
                        |Notification.DEFAULT_LIGHTS)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pi)
                .build();
        info.notify(NOTIFICATION_ID,notify);


        Bundle myBundle = this.getIntent().getExtras();
        Pid = myBundle.getInt("cid");
        Log.d("Pid_C",Pid+"");
        DB = new NotesDB(this);
        dbread = DB.getReadableDatabase();

        sql = "select * from note where _id=" + Pid;
        Cursor content = dbread.rawQuery(sql,null);
        content.moveToNext();
        title = content.getString(content.getColumnIndex("title"));
        content.close();



        mediaPlayer = MediaPlayer.create(this, R.raw.rang);
        //mediaPlayer.setLooping(true);
        mediaPlayer.start();

        //创建一个闹钟提醒的对话框,点击确定关闭铃声与页面
        new AlertDialog.Builder(ClockActivity.this).setTitle("备忘提示").setMessage( title+" 时间到了")
        .setPositiveButton("知道了", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mediaPlayer.stop();
                ClockActivity.this.finish();
            }
        }).show();

    }
}
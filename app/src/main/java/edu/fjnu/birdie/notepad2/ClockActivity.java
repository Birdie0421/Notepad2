package edu.fjnu.birdie.notepad2;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import edu.fjnu.birdie.notepad2.Utils.NotePad;
import edu.fjnu.birdie.notepad2.Utils.NotesDB;

public class ClockActivity extends Activity {

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

//        Bundle myBundle = this.getIntent().getExtras();
//        Pid = myBundle.getInt("cid");
//        Log.d("Pid_C",Pid+"");
//        DB = new NotesDB(this);
//        dbread = DB.getReadableDatabase();

//        sql = "select * from note where _id=" + Pid;
//        Cursor content = dbread.rawQuery(sql,null);
//        title = content.getString(content.getColumnIndex("title"));
//        content.close();



        mediaPlayer = MediaPlayer.create(this, R.raw.rang);
        //mediaPlayer.setLooping(true);
        mediaPlayer.start();

        //创建一个闹钟提醒的对话框,点击确定关闭铃声与页面
        new AlertDialog.Builder(ClockActivity.this).setTitle("备忘提示").setMessage( "备忘时间到了")
        .setPositiveButton("知道了", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mediaPlayer.stop();
                ClockActivity.this.finish();
            }
        }).show();

    }
}
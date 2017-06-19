package edu.fjnu.birdie.notepad2;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import edu.fjnu.birdie.notepad2.Utils.NotePad;
import edu.fjnu.birdie.notepad2.Utils.NotesDB;
import edu.fjnu.birdie.notepad2.view.GestureLockViewGroup;

public class AddNotePwdActivity extends AppCompatActivity {

        private GestureLockViewGroup mGestureLockViewGroup;
        private int Pid ;
        private EditText notePwd,confirmNotePwd;
        private NotesDB DB;
        private String sql;
        private String pwd = null;
        private SQLiteDatabase dbread;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_note_pwd);

            Bundle myBundle = this.getIntent().getExtras();
            Pid = myBundle.getInt("pid");
            Log.d("Pid",Pid+"");
            DB = new NotesDB(this);
            dbread = DB.getReadableDatabase();


            notePwd = (EditText) findViewById(R.id.notePwd);
            confirmNotePwd = (EditText) findViewById(R.id.confirmNotePwd);
            Button addNotePassword = (Button) findViewById(R.id.bt_addNotePwd);
            addNotePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(notePwd.getText().length()<6){
                        Toast.makeText(AddNotePwdActivity.this, "密码需要大于6位", Toast.LENGTH_SHORT).show();
                    }else if(!notePwd.getText().toString().equals(confirmNotePwd.getText().toString())){
                        Toast.makeText(AddNotePwdActivity.this, "两次密码输入不相等", Toast.LENGTH_SHORT).show();
                    }else{
                        pwd = notePwd.getText().toString();
                        Toast.makeText(AddNotePwdActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                        sql  = "update note set password ='" + pwd + "' where _id=" + Pid;
                        dbread.execSQL(sql);
                        finish();
                    }

                }
            });



        }
}

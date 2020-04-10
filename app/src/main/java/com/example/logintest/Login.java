package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    TextView textView;
    Button button,musicPayer,videoPlayer;
    TextView textView1;
    EditText editText;
    MyDBHelper myDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textView=(TextView)findViewById(R.id.textView5);
        button=(Button)findViewById(R.id.find) ;
        textView1=(TextView)findViewById(R.id.textView6);
        editText=(EditText)findViewById(R.id.editText7);
        musicPayer=(Button)findViewById(R.id.music) ;
        videoPlayer=(Button)findViewById(R.id.video) ;
        myDBHelper=new MyDBHelper(this,"UserData",null,1);
        Bundle bundle=new Bundle();
        bundle=getIntent().getExtras();
        String name =bundle.getString("name");
        textView.setText(name+",登陆成功");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getName=editText.getText().toString().trim();
                   Inquire(getName);

            }
        }); //查询响应监听
        musicPayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.setClass(Login.this,MusicPlayer.class);
                startActivity(intent);
            }
        });
        videoPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(Login.this,VideoActivity.class);
                startActivity(intent);
            }
        });

    }

    public void Inquire(String name)
    {
        SQLiteDatabase db=myDBHelper.getWritableDatabase();
        String query= "select * from Users where name like ?";
        Cursor cursor=db.rawQuery(query,new String[]{name+'%'});
        if(cursor.getCount()>0) {
            int i = cursor.getCount();
            String ct = "姓" + name + "的用户有" + i + "个\n";
            while (cursor.moveToNext()) {
                ct = ct + "\n" + cursor.getString(cursor.getColumnIndex("name"));
                textView1.setText(ct);
            }
        }
        else
        {
            textView1.setText("没有姓"+name+"的用户");
        }
        cursor.close();
    }
}//查询数据库中的人数

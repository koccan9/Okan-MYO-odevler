package com.example.uygulama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import DAO.Finals;

public class Login extends AppCompatActivity {
    private static final String pass_key="pas";
    private void setBar(){
        setTitle("giriş");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBar();
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onBackPressed() {//Uygulamadan çık Android'e geri dön
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    public void login(View view){
        SharedPreferences sharedPreferences=getSharedPreferences(Finals.prefs,MODE_PRIVATE);
        EditText et=findViewById(R.id.pass);
        String str=et.getText().toString().trim();
        if(str.equals("")){
            Toast.makeText(this,"şifre boş geçilemez",Toast.LENGTH_SHORT).show();
            et.setText("");
            return;
        }
        if(sharedPreferences.getString(pass_key,null)==null){
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(pass_key,str);
            editor.commit();
            Intent it=new Intent(this,MainActivity.class);
            startActivity(it);
            Toast.makeText(this,"şifre yaratıldı",Toast.LENGTH_SHORT).show();
        }
        else{
            if(!sharedPreferences.getString(pass_key,null).equals(str)){
                Toast.makeText(this,"yanlış şifre",Toast.LENGTH_SHORT).show();
                et.setText("");
            }
            else{
                Intent it=new Intent(this,MainActivity.class);
                startActivity(it);
                Toast.makeText(this,"şifre doğru girildi",Toast.LENGTH_SHORT).show();
            }
        }
    }
}

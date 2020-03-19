package com.example.uygulama;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import DAO.DB;

public class MainActivity extends AppCompatActivity {

    private void setBar(){
        String str=getString(R.string.anaEkranTitle);
        setTitle(str);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBar();
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {//Şifre ekranına geri dön
        Intent i=new Intent(this,Login.class);
        startActivity(i);
    }
    public void KategoriGecis(View v){
        Intent it=new Intent(this,KategoriEkle.class);
        startActivity(it);
    }
    public void DokumanGecis(View v){
        Intent it=new Intent(this,Dokumanlar.class);
        startActivity(it);
    }
    public void KatlistGecis(View v){
        Intent it=new Intent(this,KategoriListesi.class);
        startActivity(it);
    }
    public void ResetAppDB(View v){//YANLIŞLIKLA TIKLANAMAYACAK KADAR TEHKLİKELİ BİR İŞLEM.BÜTÜN UYGULAMANIN VERİTABANINI SIFIRLIYOR.
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setMessage("BUTUN UYGULAMAYI SIFIRLAMAK İSTEDİGİNDEN EMİNMİSİN?");
        b.setCancelable(true);
        b.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DB.truncateAllTables(MainActivity.this);
            }
        });
        b.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                
            }
        });
        b.show();
    }
}

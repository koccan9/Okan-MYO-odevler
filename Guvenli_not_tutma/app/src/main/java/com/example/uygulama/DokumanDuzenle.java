package com.example.uygulama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import DAO.Dokuman;
import DAO.Singletons;

public class DokumanDuzenle extends AppCompatActivity {
    public static final String dokuman_id_key="dokuman id";
    public static final String dokuman_kat_id_key="dokuman kat id";
    public static final String dokuman_header_key="header key";
    public static final String dokuman_content_key="content key";
    private static final String header_key="header temp";
    private static final String content_key="content temp";
    private int dokuman_id, dokuman_kat_id;
    private String header, content;
    private EditText baslik_edit, icerik_edit;
    private void setBar(){
        String str=getString(R.string.dokumanDuzenleTitle);
        setTitle(str);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokuman_duzenle);
        setBar();
        baslik_edit =findViewById(R.id.baslikedit);
        icerik_edit =findViewById(R.id.icerikedit);
        Intent it=getIntent();
        dokuman_id =it.getIntExtra(dokuman_id_key,0);
        dokuman_kat_id =it.getIntExtra(dokuman_kat_id_key,0);
        header=it.getStringExtra(dokuman_header_key);
        content=it.getStringExtra(dokuman_content_key);
        baslik_edit.setText(header);
        icerik_edit.setText(content);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(header_key, baslik_edit.getText().toString());
        outState.putString(content_key, icerik_edit.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        baslik_edit.setText(savedInstanceState.getString(header_key));
        icerik_edit.setText(savedInstanceState.getString(content_key));
    }
    public void silAction(View v){
        final View temp_v=v;
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setMessage("silmek istediginden eminmisin?");
        b.setCancelable(true);
        b.setPositiveButton("evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Dokuman temp=new Dokuman();
                temp.id=dokuman_id;
                Singletons.getApplicationDatabase(DokumanDuzenle.this).getDAO().deleteDokuman(temp);
                gerigit(temp_v);
            }
        });
        b.setNegativeButton("hayir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                
            }
        });
        b.show();
    }
    public void kaydetAction(View v){
        if(baslik_edit.getText().toString().trim().equals("")|| icerik_edit.getText().toString().trim().equals("")){
            Toast.makeText(this, "bazi degerler bos gecildi", Toast.LENGTH_LONG).show();
        }
        else{
            Dokuman d=new Dokuman();
            d.id= dokuman_id;
            d.kat_id= dokuman_kat_id;
            d.content= icerik_edit.getText().toString();
            d.header= baslik_edit.getText().toString();
            Singletons.getApplicationDatabase(this).getDAO().updateDokuman(d);
            Toast.makeText(this, "degisiklikler kaydedildi", Toast.LENGTH_LONG).show();
        }
    }
    public void gerigit(View v){
        Intent it=new Intent(this,DokumanByKategori.class);
        it.putExtra(DokumanByKategori.kat_id_key, dokuman_kat_id);
        startActivity(it);
    }

    @Override
    public void onBackPressed() {
        gerigit(null);
    }
    public void anaekran(View v){
        Intent it=new Intent(this,MainActivity.class);
        startActivity(it);
    }
}

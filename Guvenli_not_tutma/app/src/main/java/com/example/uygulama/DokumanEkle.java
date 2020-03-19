package com.example.uygulama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import DAO.DB;
import DAO.Dokuman;
import DAO.Singletons;

public class DokumanEkle extends AppCompatActivity {
    private EditText baslik;
    private EditText icerik;
    private static final String baslikKey="yeni baslik";
    private static final String icerikKey="yeni icerik";
    public static final String kat_id_key="kategori id";
    private int kat_id;
    private class DokumanEkleAsync extends AsyncTask<Void,Void,Void>{
        private Dokuman doc;
        private boolean errorOnInsert=false;

        public DokumanEkleAsync(Dokuman doc) {
            this.doc = doc;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            if(errorOnInsert){
                Toast.makeText(DokumanEkle.this,"tekrarli giris tespit edildi",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                DB appdb=Singletons.getApplicationDatabase(DokumanEkle.this);
                appdb.getDAO().insertDokuman(doc);
            }catch (SQLiteConstraintException ex){
                errorOnInsert=true;
            }
            return null;
        }
    }
    private void setBar(){
        String str=getString(R.string.dokumanEkleTitle,Singletons.getApplicationDatabase(this).getDAO().getKategoriNameById(kat_id));
        setTitle(str);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokuman_ekle);
        kat_id=getIntent().getIntExtra(kat_id_key,0);
        baslik=findViewById(R.id.baslikEDT);
        icerik=findViewById(R.id.contentEDT);
        setBar();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(baslikKey,baslik.getText().toString());
        outState.putString(icerikKey,icerik.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        baslik.setText(savedInstanceState.getString(baslikKey));
        icerik.setText(savedInstanceState.getString(icerikKey));
    }

    public void anaEkran(View v){
        Intent it=new Intent(this,MainActivity.class);
        startActivity(it);
    }
    public void geri(View v){
        Intent it=new Intent(this, DokumanByKategori.class);
        it.putExtra(DokumanByKategori.kat_id_key,kat_id);
        startActivity(it);
    }

    @Override
    public void onBackPressed() {
        geri(null);
    }

    public void YeniDokumanEkle(View v){
        if(baslik.getText().toString().trim().equals("")||icerik.getText().toString().trim().equals("")){
            Toast.makeText(this,"hatali girisler var!!",Toast.LENGTH_LONG).show();
            icerik.setText("");
            baslik.setText("");
            return;
        }
        Dokuman dy=new Dokuman();
        dy.kat_id=kat_id;
        dy.header=baslik.getText().toString();
        dy.content=icerik.getText().toString();
        new DokumanEkleAsync(dy).execute();
        icerik.setText("");
        baslik.setText("");
    }
}

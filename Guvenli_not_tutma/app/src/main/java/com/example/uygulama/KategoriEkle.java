package com.example.uygulama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import DAO.AppDAO;
import DAO.DB;
import DAO.Kategori;
import DAO.Singletons;

public class KategoriEkle extends AppCompatActivity {
    private EditText et;
    private final static String kat_name_key="kategori isim";
    private class KategoriEkleAsync extends AsyncTask<Void,Void,Void>{
        private Kategori kt;
        private boolean errorOnInsert=false;
        public KategoriEkleAsync(Kategori kt) {
            this.kt = kt;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(errorOnInsert){
                Toast.makeText(KategoriEkle.this,"tekrarli giris tespit edildi",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                DB d=Singletons.getApplicationDatabase(KategoriEkle.this);
                AppDAO ad=d.getDAO();
                ad.insertKategori(kt);
            }catch (SQLiteConstraintException ex){//tekrarlı ekleme hatası olduğunda çalışacak catch bloğu çünkü kategori isimleri unique.
                errorOnInsert=true;
            }
            return null;
        }
    }
    private void setBar(){
        String str=getString(R.string.katEkleTitle);
        setTitle(str);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_ekle);
        setBar();
        et=findViewById(R.id.kategori_isim);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(kat_name_key,et.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        et.setText(savedInstanceState.getString(kat_name_key));
    }

    public void ekle(View v){
        if(et.getText().toString().trim().equals("")){
            Toast.makeText(this,"kategori ismi bos olamaz",Toast.LENGTH_LONG).show();
            et.setText("");
            return;
        }
        Kategori kt=new Kategori();
        kt.name=et.getText().toString();
        KategoriEkleAsync t=new KategoriEkleAsync(kt);
        t.execute();
        et.setText("");
    }
    public void donus(View v){
        Intent it=new Intent(this,MainActivity.class);
        startActivity(it);
    }
    @Override
    public void onBackPressed() {
        donus(null);
    }
}

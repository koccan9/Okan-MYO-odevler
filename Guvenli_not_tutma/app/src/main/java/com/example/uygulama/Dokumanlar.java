package com.example.uygulama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import DAO.AppDAO;
import DAO.DB;
import DAO.Kategori;
import DAO.Singletons;

public class Dokumanlar extends AppCompatActivity {
    private ListView lv;
    private class fillTsk extends AsyncTask<Void,Void,List<Kategori>>{//kategorileri listeleyen thread.
        @Override
        protected void onPostExecute(List<Kategori> aVoid) {
            ArrayAdapter<Kategori> ktg=new ArrayAdapter<Kategori>(Dokumanlar.this,android.R.layout.simple_list_item_1,aVoid);
            lv.setAdapter(ktg);
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected List<Kategori> doInBackground(Void... voids) {
            DB d= Singletons.getApplicationDatabase(Dokumanlar.this);
            AppDAO at=d.getDAO();
            List<Kategori> ktt=at.getKategoris();
            return ktt;
        }
    }
    private void setBar(){
        String str=getString(R.string.dokumanByKategoriTitle);
        setTitle(str);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokumanlar);
        setBar();
        lv=findViewById(R.id.liste);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ArrayAdapter<Kategori> arl=(ArrayAdapter<Kategori>)adapterView.getAdapter();
                Intent it=new Intent(Dokumanlar.this,DokumanByKategori.class);
                it.putExtra(DokumanByKategori.kat_id_key,arl.getItem(position).id);
                startActivity(it);
            }
        });
        fillLV();
    }
    private void fillLV(){
        new fillTsk().execute();
    }
    public void geridonus(View v){
        Intent it=new Intent(this,MainActivity.class);
        startActivity(it);
    }
    @Override
    public void onBackPressed() {
        geridonus(null);
    }
}

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

public class KategoriListesi extends AppCompatActivity {
    private ListView lv;
    private class KatlistAsync extends AsyncTask<Void,Void, List<Kategori>> {//kategorileri listeleyen thread.
        @Override
        protected void onPostExecute(List<Kategori> param) {
            ArrayAdapter<Kategori> ktg=new ArrayAdapter<Kategori>(KategoriListesi.this,android.R.layout.simple_list_item_1,param);
            lv.setAdapter(ktg);
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected List<Kategori> doInBackground(Void... voids) {
            DB d= Singletons.getApplicationDatabase(KategoriListesi.this);
            AppDAO at=d.getDAO();
            List<Kategori> ktt=at.getKategoris();
            return ktt;
        }
    }
    private void setBar(){
        String str=getString(R.string.kategoriListesiTitle);
        setTitle(str);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_listesi);
        setBar();
        lv=findViewById(R.id.katlist);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                final ArrayAdapter<Kategori> tempkat=(ArrayAdapter<Kategori>)adapterView.getAdapter();
                final Kategori temp=tempkat.getItem(index);
                Intent it=new Intent(KategoriListesi.this,KategoriSilGuncelle.class);
                it.putExtra(KategoriSilGuncelle.kat_id_key,temp.id);
                it.putExtra(KategoriSilGuncelle.kat_name_key,temp.name);
                startActivity(it);
            }
        });
        fillLV();
    }
    public void geridon(View v){
        Intent it=new Intent(this,MainActivity.class);
        startActivity(it);
    }
    @Override
    public void onBackPressed() {
        geridon(null);
    }

    private void fillLV(){
        new KatlistAsync().execute();
    }
}

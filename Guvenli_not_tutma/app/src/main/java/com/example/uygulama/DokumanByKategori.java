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

import DAO.Dokuman;
import DAO.Singletons;

public class DokumanByKategori extends AppCompatActivity {
    private int kat_id;
    private ListView lv;
    public static final String kat_id_key="kategori id";
    private class DokumanListele extends AsyncTask<Void,Void, List<Dokuman>>{
        @Override
        protected void onPostExecute(List<Dokuman> dd) {
            ArrayAdapter<Dokuman> arlm=new ArrayAdapter<Dokuman>(DokumanByKategori.this,android.R.layout.simple_list_item_1,dd);
            lv.setAdapter(arlm);
        }

        @Override
        protected List<Dokuman> doInBackground(Void... voids) {
            return Singletons.getApplicationDatabase(DokumanByKategori.this).getDAO().getDokumanByKategori(kat_id);
        }
    }
    private void setBar(){
        String str=getString(R.string.dokumanKatNameTitle,Singletons.getApplicationDatabase(this).getDAO().getKategoriNameById(kat_id));
        setTitle(str);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokuman_by_kategori);
        kat_id=getIntent().getIntExtra(kat_id_key,0);
        setBar();
        lv=findViewById(R.id.eklenmisDokumanlar);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pozisyon, long l) {
                ArrayAdapter<Dokuman> darl=(ArrayAdapter<Dokuman>)adapterView.getAdapter();
                Dokuman tempdoc=darl.getItem(pozisyon);
                Intent it=new Intent(DokumanByKategori.this,DokumanDuzenle.class);
                it.putExtra(DokumanDuzenle.dokuman_id_key,tempdoc.id);
                it.putExtra(DokumanDuzenle.dokuman_kat_id_key,tempdoc.kat_id);
                it.putExtra(DokumanDuzenle.dokuman_header_key,tempdoc.header);
                it.putExtra(DokumanDuzenle.dokuman_content_key,tempdoc.content);
                startActivity(it);
            }
        });
        fillLV();
    }
    private void fillLV(){
        new DokumanListele().execute();
    }
    public void AnaEkranaGit(View v){
        Intent it=new Intent(this,MainActivity.class);
        startActivity(it);
    }
    public void DokumanlaraGit(View v){
        Intent it=new Intent(this,Dokumanlar.class);
        startActivity(it);
    }
    public void DokumanEkleyeGit(View v){
        Intent it=new Intent(this,DokumanEkle.class);
        it.putExtra(DokumanEkle.kat_id_key,kat_id);
        startActivity(it);
    }
    @Override
    public void onBackPressed() {
        DokumanlaraGit(null);
    }
}

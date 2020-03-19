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

import DAO.Kategori;
import DAO.Singletons;

public class KategoriSilGuncelle extends AppCompatActivity {
    public static final String kat_id_key="kategori id";
    public static final String kat_name_key="kategori name";
    private static final String yedek_key ="isim";
    private int kat_id;
    private String katname;
    private EditText edit;
    private void setBar(){
        String str=getString(R.string.katSilGuncelleTitle);
        setTitle(str);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_sil_guncelle);
        setBar();
        Intent it=getIntent();
        kat_id =it.getIntExtra(kat_id_key,0);
        katname=it.getStringExtra(kat_name_key);
        edit=findViewById(R.id.kattextedit);
        edit.setText(katname);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(yedek_key,edit.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        edit.setText(savedInstanceState.getString(yedek_key));
    }
    public void geridon(View v){
        Intent it=new Intent(this,MainActivity.class);
        startActivity(it);
    }
    public void Sil(View v){
        final View temp_v=v;
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setMessage("silmek istediginden eminmisin?");
        b.setCancelable(true);
        b.setPositiveButton("evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Kategori kt=new Kategori();
                kt.id= kat_id;
                Singletons.getApplicationDatabase(KategoriSilGuncelle.this).getDAO().deleteKategori(kt);
                listeyegit(temp_v);
            }
        });
        b.setNegativeButton("hayir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        b.show();
        
    }
    public void listeyegit(View v){
        Intent it=new Intent(this,KategoriListesi.class);
        startActivity(it);
    }

    @Override
    public void onBackPressed() {
        listeyegit(null);
    }

    public void guncelle(View v){
        if(edit.getText().toString().equals("")){
            Toast.makeText(this,"bos birakilan deger olamaz",Toast.LENGTH_SHORT).show();
        }
        else{
            try {
                Kategori kt=new Kategori();
                kt.id= kat_id;
                kt.name=edit.getText().toString();
                Singletons.getApplicationDatabase(this).getDAO().updateKategori(kt);
                Toast.makeText(this,"guncellendi",Toast.LENGTH_SHORT).show();
            }catch(Exception ex){
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
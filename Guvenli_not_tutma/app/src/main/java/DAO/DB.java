package DAO;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.uygulama.MainActivity;

@Database(entities = {Kategori.class,Dokuman.class},version = 2)
public abstract class DB extends RoomDatabase {//Room database derleme esnasında bu sınıftan türetme yapar.
    private static class Resetter extends AsyncTask<Void,Void,Void>{
        private MainActivity mt;

        public Resetter(MainActivity mt) {
            this.mt = mt;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(mt,"butun uygulama verileri sifirlandi",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Singletons.getApplicationDatabase(mt).getDAO().deleteAllDokuman();
            Singletons.getApplicationDatabase(mt).getDAO().resetDokuman();
            Singletons.getApplicationDatabase(mt).getDAO().deleteAllKategori();
            Singletons.getApplicationDatabase(mt).getDAO().resetKategori();
            return null;
        }
    }
    public abstract AppDAO getDAO();
    public static void truncateAllTables(MainActivity mt){
        new Resetter(mt).execute();
    }
}

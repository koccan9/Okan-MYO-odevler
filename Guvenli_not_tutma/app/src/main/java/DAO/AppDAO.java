package DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppDAO {//Room database derleme esnasında bu interface'i implement eden bir sınıf yazar
    @Query("SELECT * FROM Kategori ORDER BY name")
    List<Kategori> getKategoris();
    @Query("SELECT name FROM Kategori WHERE id=:id")
    String getKategoriNameById(int id);
    @Query("SELECT * FROM Dokuman where kat_id=:kategori_id ORDER BY header")
    List<Dokuman> getDokumanByKategori(int kategori_id);
    @Query("SELECT * FROM Dokuman ORDER BY header")
    List<Dokuman> getDokuman();
    @Insert
    void insertKategori(Kategori ...params);
    @Insert
    void insertDokuman(Dokuman...params);
    @Delete
    void deleteKategori(Kategori ...params);
    @Delete
    void deleteDokuman(Dokuman...params);
    @Query("DELETE FROM DOKUMAN WHERE kat_id=:kat_id")
    void deleteDokumanByKategori(int kat_id);//gelecekte kullanılabilir.
    @Update
    void updateKategori(Kategori ...params);
    @Update
    void updateDokuman(Dokuman ...params);
    @Query("DELETE FROM Dokuman")
    void deleteAllDokuman();
    @Query("DELETE FROM SQLITE_SEQUENCE WHERE name = 'Dokuman'")
    void resetDokuman();
    @Query("DELETE FROM Kategori")
    void deleteAllKategori();
    @Query("DELETE FROM SQLITE_SEQUENCE WHERE name = 'Kategori'")
    void resetKategori();
}

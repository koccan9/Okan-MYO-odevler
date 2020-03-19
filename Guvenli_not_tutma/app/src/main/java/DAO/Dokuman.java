package DAO;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Kategori.class,
        parentColumns = "id",
        childColumns = "kat_id",
        onDelete = ForeignKey.CASCADE))
public final class Dokuman {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;
    public String header;//not başlığı
    public String content;//not içeriği
    @NonNull
    public int kat_id;
    @NonNull
    @Override
    public String toString() {
        return header;
    }
}

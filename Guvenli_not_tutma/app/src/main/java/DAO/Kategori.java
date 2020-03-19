package DAO;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(indices = {@Index(value = "name",unique = true)})
public final class Kategori {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;
    @NonNull
    public String name;
    @Override
    public String toString() {
        return name;
    }
}

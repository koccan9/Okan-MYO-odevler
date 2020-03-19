package DAO;

import android.content.Context;

import androidx.room.Room;

public final class Singletons {
    private Singletons(){//Singletons türünden nesne yaratmayı engellemek için.
        
    }
    private static DB m_db;
    public static DB getApplicationDatabase(Context ct){//DB nesnesi uygulama çapında sadece bir kez yaratılıyor.
        if(m_db==null){
            m_db= Room.databaseBuilder(ct.getApplicationContext(),DB.class,Finals.AppDatabaseName).allowMainThreadQueries().
                    fallbackToDestructiveMigration().build();
        }
        return m_db;
    }
}
//final class'lar türetmeye kapalıdır.
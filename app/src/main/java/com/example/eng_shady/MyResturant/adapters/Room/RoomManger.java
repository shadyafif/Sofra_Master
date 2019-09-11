package com.example.eng_shady.MyResturant.adapters.Room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.eng_shady.MyResturant.models.General.ResturantItem.ItemFoodData;


@Database(entities = {ItemFoodData.class}, version = 1, exportSchema = false)
@TypeConverters({DataTypeConverter.class})
public abstract class RoomManger extends RoomDatabase {

    private static RoomManger roomManger;

    public abstract RoomDao roomDao();

    public static synchronized RoomManger getInstance(Context context) {
        if (roomManger == null) {
            roomManger = Room.databaseBuilder(context.getApplicationContext(), RoomManger.class, "sofra_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return roomManger;
    }

}

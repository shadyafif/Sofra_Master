package com.example.eng_shady.MyResturant.adapters.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.eng_shady.MyResturant.models.General.ResturantItem.ItemFoodData;

import java.util.List;

@Dao
public interface RoomDao {

    @Insert
    void insertItemToCar(ItemFoodData... foodItem);

    @Update
    void updateItemToCar(ItemFoodData ... foodItem);

    @Delete
    void deleteItemToCar(ItemFoodData ... foodItem);

    @Query("Delete from ItemFoodData")
    void deleteAllItemToCar();

    @Query("Select * from ItemFoodData")
    List<ItemFoodData> getAllItem();
}


package com.example.eng_shady.MyResturant.models.General.ResturantItem;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class ItemFoodData {

    @PrimaryKey(autoGenerate = true)
    private int item_Id;

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("preparing_time")
    @Expose
    private String preparingTime;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("photo_url")
    @Expose
    private String photoUrl;

    private int counter;


    public ItemFoodData()
    {

    }
    public ItemFoodData(int id, String createdAt, String updatedAt, String name, String description, String price, String preparingTime, String photo, String restaurantId, String photoUrl, int counter) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.description = description;
        this.price = price;
        this.preparingTime = preparingTime;
        this.photo = photo;
        this.restaurantId = restaurantId;
        this.photoUrl = photoUrl;
        this.counter = counter;
    }


    public int getItem_Id() {
        return item_Id;
    }

    public int getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getPreparingTime() {
        return preparingTime;
    }

    public String getPhoto() {
        return photo;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public int getCounter() {
        return counter;
    }

    public void setItem_Id(int item_Id) {
        this.item_Id = item_Id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPreparingTime(String preparingTime) {
        this.preparingTime = preparingTime;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}

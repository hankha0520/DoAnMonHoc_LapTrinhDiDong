package com.example.appfood.history;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class History {
    int id;
    int idf;
    String name;
    int time;
    String material;
    String making;
    String image;
    String description;
    int favourite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMaking() {
        return making;
    }

    public void setMaking(String making) {
        this.making = making;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    public int getIdf() {
        return idf;
    }



    public void setIdf(int idf) {
        this.idf = idf;
    }

    public History() {
    }

    public History(int id, int idf, String name, int time, String material, String making, String image, String description, int favourite) {
        this.id = id;
        this.idf = idf;
        this.name = name;
        this.time = time;
        this.material = material;
        this.making = making;
        this.image = image;
        this.description = description;
        this.favourite = favourite;
    }

    public static Bitmap convertStringToBitmapFromAccess(Context context, String filename){
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open(filename);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

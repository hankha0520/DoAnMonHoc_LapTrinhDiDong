package com.example.appfood.category;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.appfood.subject.Subject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Category {
    int id;
    String name;
    String image;
    List<Subject> subjectList;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }


    public Category(int id, String name, String image, List<Subject> subjectList) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.subjectList = subjectList;
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

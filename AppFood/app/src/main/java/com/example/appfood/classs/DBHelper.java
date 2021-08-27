package com.example.appfood.classs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appfood.category.Category;
import com.example.appfood.food.Food;
import com.example.appfood.history.History;
import com.example.appfood.subject.Subject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {
    static int DB_VERSION = 1;
    private String DB_PATH = "data/data/com.example.appfood/databases/";
    SQLiteDatabase database = null;

    private static final String DB_NAME = "FoodAppDB.db";

    private Context context;

    public DBHelper(Context context) {
        this.context = context;
    }

    public SQLiteDatabase openDatabase() {
        File dbFile = context.getDatabasePath(DB_NAME);
        if (!dbFile.exists()) {
            try {
                copyDatabase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void copyDatabase(File dbFile) throws IOException {
        InputStream is = context.getAssets().open(DB_NAME);
        OutputStream os = new FileOutputStream(dbFile);

        byte[] buffer = new byte[1024];
        while (is.read(buffer) > 0) {
            os.write(buffer);
        }

        os.flush();
        os.close();
        is.close();
    }

    private void closeDB(SQLiteDatabase db) {
        db.close();
    }

    public ArrayList<Category> loadCategory() {
        SQLiteDatabase db = openDatabase();
        ArrayList<Category> arr = new ArrayList<>();
        ArrayList<Subject> arrayList = new ArrayList<>();
        String sql = "select * from tblCategory";
        Cursor csr = db.rawQuery(sql, null);
        if (csr != null) {
            if (csr.moveToFirst()) {
                do {
                    int id = csr.getInt(0);
                    String name = csr.getString(1);
                    String image = csr.getString(2);
                    arrayList = loadSubjectByCateID(id);
                    arr.add(new Category(id, name, image, arrayList));
                } while (csr.moveToNext());
            }
        }
        closeDB(db);
        return arr;
    }

    public List<String> loadSubjectName() {
        SQLiteDatabase db = openDatabase();
        List<String> arr = new ArrayList<>();
        String sql = "select Name from tblSubject";
        Cursor csr = db.rawQuery(sql, null);
        if (csr != null) {
            if (csr.moveToFirst()) {
                do {
                    String name = csr.getString(0);
                    arr.add(name);
                } while (csr.moveToNext());
            }
        }
        closeDB(db);
        return arr;
    }

    public ArrayList<Subject> loadSubjectByCateID(int cateID) {
        SQLiteDatabase db = openDatabase();
        ArrayList<Subject> arr = new ArrayList<>();
        Cursor csr = db.rawQuery("select * from tblSubject where CateID = ?", new String[]{cateID + ""});
        if (csr != null) {
            if (csr.moveToFirst()) {
                do {
                    int id = csr.getInt(0);
                    String name = csr.getString(1);
                    String descrip = csr.getString(2);
                    String image = csr.getString(3);
                    arr.add(new Subject(id, name, descrip, image));
                } while (csr.moveToNext());
            }
        }
        closeDB(db);
        return arr;
    }

    public ArrayList<Food> loadFoodBySubID(int subID) {
        SQLiteDatabase db = openDatabase();
        ArrayList<Food> arrr = new ArrayList<>();
        Cursor csr = db.rawQuery("select * from tblFood where SubjectID = ?", new String[]{subID + ""});
        if (csr != null) {
            if (csr.moveToFirst()) {
                do {
                    int id = csr.getInt(0);
                    String name = csr.getString(2);
                    int time = csr.getInt(3);
                    String material = csr.getString(4);
                    String making = csr.getString(5);
                    String image = csr.getString(6);
                    String desciption = csr.getString(7);
                    int fa = csr.getInt(8);
                    arrr.add(new Food(id, name, time, material, making, image, desciption, fa));
                } while (csr.moveToNext());
            }
        }
        closeDB(db);
        return arrr;
    }

    public ArrayList<Food> loadFavorite(int fa) {
        SQLiteDatabase db = openDatabase();
        ArrayList<Food> arrayList = new ArrayList<>();
        Cursor csr = db.rawQuery("select * from tblFood where Favourite = ?", new String[]{fa + ""});
        if (csr != null) {
            if (csr.moveToFirst()) {
                do {
                    int id = csr.getInt(0);
                    String name = csr.getString(2);
                    int time = csr.getInt(3);
                    String material = csr.getString(4);
                    String making = csr.getString(5);
                    String image = csr.getString(6);
                    String desciption = csr.getString(7);
                    int favourite = csr.getInt(8);
                    arrayList.add(new Food(id, name, time, material, making, image, desciption, favourite));
                } while (csr.moveToNext());
            }
        }
        closeDB(db);
        return arrayList;
    }

    public int checkFavourite(int foodid) {
        int kq = 0;
        SQLiteDatabase db = openDatabase();
        Cursor csr = db.rawQuery("select Favourite from tblFood where FoodID = ?", new String[]{foodid + ""});
        if (csr != null) {
            if (csr.moveToFirst()) {
                do {
                    int favourite = csr.getInt(8);
                    kq = favourite;
                } while (csr.moveToNext());
            }
        }
        closeDB(db);
        return kq;
    }

    public Category findByCatetgoryID(int id) {
        ArrayList<Category> arr = loadCategory();
        for (Category ca : arr) {
            if (ca.getId() == id) {
                return ca;
            }
        }
        return null;
    }

    public int CategoryID(int id) {
        Category category = findByCatetgoryID(id);
        return category.getId();
    }

    public ArrayList<Food> searchFood(String text) {
        SQLiteDatabase db = openDatabase();
        ArrayList<Food> arr = new ArrayList<>();
        Cursor csr = db.rawQuery("select * from tblFood where Name like ?", new String[]{"%"+text+"%"});
        if (csr != null) {
            if (csr.moveToFirst()) {
                do {
                    int id = csr.getInt(0);
                    String name = csr.getString(2);
                    int time = csr.getInt(3);
                    String material = csr.getString(4);
                    String making = csr.getString(5);
                    String image = csr.getString(6);
                    String desciption = csr.getString(7);
                    int fa = csr.getInt(8);
                    arr.add(new Food(id, name, time, material, making, image, desciption, fa));
                } while (csr.moveToNext());
            }
        }
        closeDB(db);
        return arr;
    }

    public void updateFavoutite(int foodid, int favourite) {
        SQLiteDatabase db = openDatabase();
        ContentValues values = new ContentValues();
        if (favourite == 1){
            values.put("Favourite", 0);
            db.update("tblFood", values, "FoodID = ?", new String[]{String.valueOf(foodid)});
            closeDB(db);
        }
        else{
            values.put("Favourite", 1);
            db.update("tblFood", values, "FoodID = ?", new String[]{String.valueOf(foodid)});
            closeDB(db);
        }
    }

    public void insertHistory(History c)	{
        SQLiteDatabase db =	openDatabase();
        ContentValues cv	=	new	ContentValues();
        cv.put("foodID", c.getIdf());
        cv.put("name",	 c.getName());
        cv.put("time",	 c.getTime());
        cv.put("material",	 c.getMaterial());
        cv.put("making",	 c.getMaking());
        cv.put("image",	 c.getImage());
        cv.put("description",	 c.getDescription());
        cv.put("favourite", c.getFavourite());
        db.insert("tblHistory",	 null,	cv);
        closeDB(db);
    }

    public ArrayList<History> loadHistory() {
        SQLiteDatabase db = openDatabase();
        ArrayList<History> arrayList = new ArrayList<>();
        Cursor csr = db.rawQuery("select * from tblHistory", null );
        if (csr != null) {
            if (csr.moveToFirst()) {
                do {
                    int id = csr.getInt(0);
                    int idf = csr.getInt(1);
                    String name = csr.getString(2);
                    int time = csr.getInt(3);
                    String material = csr.getString(4);
                    String making = csr.getString(5);
                    String image = csr.getString(6);
                    String desciption = csr.getString(7);
                    int favourite = csr.getInt(8);
                    arrayList.add(new History(id, idf, name, time, material, making, image, desciption, favourite));
                } while (csr.moveToNext());
            }
        }
        closeDB(db);
        return arrayList;
    }

    public boolean searchHistory(int idfood) {
        SQLiteDatabase db = openDatabase();
        Cursor csr = db.rawQuery("select * from tblHistory where foodID = ?", new String[]{String.valueOf(idfood)});
        if (csr != null) {
            if (csr.moveToFirst()) {
                do {
                    return true;
                } while (csr.moveToNext());
            }
        }
        closeDB(db);
        return false;
    }
    public void delete()
    {
        SQLiteDatabase	db	=	openDatabase();
        db.delete("tblHistory", null, null);
        closeDB(db);
    }
}

package com.example.appfood.classs;

import android.content.Context;

public class DataLocalManger {
    private static final String PREF_FIRST_INSTALL = "PREF_FIRST_INSTALL";
    private static final String PREF_SP = "PREF_SP";
    private static final String PREF_LIST = "PREF_LIST";
    private static DataLocalManger instance;
    private MySharedPreferences mySharedPreferences;

    public static void init(Context context){
        instance = new DataLocalManger();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }

    public static DataLocalManger getInstance(){
        if(instance == null){
            instance = new DataLocalManger();
        }
        return instance;
    }

    public static void setFirstInstall(boolean isFirst){
        DataLocalManger.getInstance().mySharedPreferences.putBooleanValue(PREF_FIRST_INSTALL,isFirst);
    }

    public static boolean getFirstInstall(){
        return DataLocalManger.getInstance().mySharedPreferences.getBooleanValue(PREF_FIRST_INSTALL);
    }
}

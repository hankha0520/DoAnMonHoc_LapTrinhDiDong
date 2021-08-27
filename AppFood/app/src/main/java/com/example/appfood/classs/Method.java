package com.example.appfood.classs;

import com.example.appfood.R;

public class Method {
    public void setColorTheme(){

        switch (Constant.color){
            case 0xffF44336:
                Constant.theme = R.style.Theme_AppFood;
                break;
            case 0xffE91E63:
                Constant.theme = R.style.Theme_AppFood_Pink;
                break;
            case 0xff9C27B0:
                Constant.theme = R.style.Theme_AppFood_Purple;
                break;
            case 0xff673AB7:
                Constant.theme = R.style.Theme_AppFood_DeepPurple;
                break;
            case 0xff3F51B5:
                Constant.theme = R.style.Theme_AppFood_Indigo;
                break;
            case 0xff2196F3:
                Constant.theme = R.style.Theme_AppFood_Blue;
                break;
            case 0xff03A9F4:
                Constant.theme = R.style.Theme_AppFood_LightBlue;
                break;
            case 0xff00BCD4:
                Constant.theme = R.style.Theme_AppFood_Cyan;
                break;
            case 0xff009688:
                Constant.theme = R.style.Theme_AppFood_Teal;
                break;
            case 0xff4CAF50:
                Constant.theme = R.style.Theme_AppFood_Green;
                break;
            case 0xff8BC34A:
                Constant.theme = R.style.Theme_AppFood_LightGreen;
                break;
            case 0xffCDDC39:
                Constant.theme = R.style.Theme_AppFood_Lime;
                break;
            case 0xffFFEB3B:
                Constant.theme = R.style.Theme_AppFood_Yellow;
                break;
            case 0xffFFC107:
                Constant.theme = R.style.Theme_AppFood_Amber;
                break;
            case 0xffFF9800:
                Constant.theme = R.style.Theme_AppFood_Orange;
                break;
            case 0xffFF5722:
                Constant.theme = R.style.Theme_AppFood_DeepOrange;
                break;
            case 0xff795548:
                Constant.theme = R.style.Theme_AppFood_Brown;
                break;
            case 0xff9E9E9E:
                Constant.theme = R.style.Theme_AppFood_Grey;
                break;
            case 0xff607D8B:
                Constant.theme = R.style.Theme_AppFood_BlueGrey;
                break;
            default:
                Constant.theme = R.style.Theme_AppFood;
                break;
        }
    }
}

package com.example.appfood.onboarding;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPaperAdapter extends FragmentStatePagerAdapter {
    public ViewPaperAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new OnboaringFragment1();
            case 1:
                return new OnboaringFragment2();
            case 2:
                return new OnboaringFragment3();
            default:
                return new OnboaringFragment1();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}

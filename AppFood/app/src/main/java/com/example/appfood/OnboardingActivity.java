package com.example.appfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.appfood.onboarding.ViewPaperAdapter;

import me.relex.circleindicator.CircleIndicator;

public class OnboardingActivity extends AppCompatActivity {

    TextView skip;
    ViewPager viewPager;
    RelativeLayout layoutbottom;
    CircleIndicator indicator;
    TextView next;
    ViewPaperAdapter viewPaperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        skip = findViewById(R.id.btn_skip);
        next = findViewById(R.id.btn_next);
        viewPager = findViewById(R.id.onboarding_view_paper);
        layoutbottom = findViewById(R.id.onboarding_relative);
        indicator = findViewById(R.id.onboarding_indicator);

        viewPaperAdapter = new ViewPaperAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPaperAdapter);
        indicator.setViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2){
                    skip.setVisibility(View.GONE);
                    layoutbottom.setVisibility(View.GONE);
                }
                else {
                    skip.setVisibility(View.VISIBLE);
                    layoutbottom.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);//Trang cuối
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() < 2){
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        });
    }
}
package com.eyasumulugeta.etafenta;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.eyasumulugeta.etafenta.Adapters.SliderAdapter;

public class Walkthrough extends AppCompatActivity implements OnClickListener {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    private SliderAdapter sliderAdapter;

    private Button mNextButton;
    private Button mBackButton;
    private Button mGetStartedButton;

    private int mCurrentPage;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);

        //used to customize the status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        intent = new Intent(this, Horoscopes.class);

        mDotLayout = findViewById(R.id.dotsLayout);
        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);

        mNextButton = findViewById(R.id.nextBtn);
        mNextButton.setOnClickListener(this);
        mBackButton = findViewById(R.id.prevBtn);
        mBackButton.setOnClickListener(this);
        mGetStartedButton = findViewById(R.id.getStartedBtn);
        mGetStartedButton.setOnClickListener(this);

        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);
    }

    public void addDotsIndicator(int position) {
        mDots = new TextView[3];
        mDotLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            mDotLayout.addView(mDots[i]);
        }
        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            mCurrentPage = i;
            if (i == 0) {
                mNextButton.setEnabled(true);
                mBackButton.setEnabled(false);
                mBackButton.setVisibility(View.INVISIBLE);
                mGetStartedButton.setVisibility(View.INVISIBLE);
                mNextButton.setText("ቀጣይ");
                mBackButton.setText("");
            } else if (i == mDots.length - 1) {
                mBackButton.setVisibility(View.VISIBLE);
                mNextButton.setVisibility(View.INVISIBLE);
                mGetStartedButton.setVisibility(View.VISIBLE);
            } else {
                mNextButton.setEnabled(true);
                mBackButton.setEnabled(true);
                mBackButton.setVisibility(View.VISIBLE);
                mNextButton.setVisibility(View.VISIBLE);
                mGetStartedButton.setVisibility(View.INVISIBLE);
                mNextButton.setText("ቀጣይ");
                mBackButton.setText("ይመለሱ");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextBtn:
                mSlideViewPager.setCurrentItem(mCurrentPage + 1);
                break;
            case R.id.prevBtn:
                mSlideViewPager.setCurrentItem(mCurrentPage - 1);
                break;
            case R.id.getStartedBtn:
                Intent intent = new Intent(this, Horoscopes.class);
                startActivity(intent);
                break;
        }
    }
}

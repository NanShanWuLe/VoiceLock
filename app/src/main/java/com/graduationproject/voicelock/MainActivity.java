package com.graduationproject.voicelock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.graduationproject.voicelock.Adapter.VPAdapter;
import com.graduationproject.voicelock.Fragment.OneFragment;
import com.graduationproject.voicelock.Fragment.ThreeFragment;
import com.graduationproject.voicelock.Fragment.TwoFragment;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    ViewPager2 viewPager2;
    RadioGroup rgvp;
    VPAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changStatusIconCollor(true);
        setId();
        rgvp.setOnCheckedChangeListener(this);
        adapter = new VPAdapter(this);
        viewPager2.setAdapter(adapter);
        adapter.addFragment(new OneFragment());
        adapter.addFragment(new TwoFragment());
        adapter.addFragment(new ThreeFragment());
        viewPager2.setUserInputEnabled(false);
        viewPager2.setCurrentItem(0);
    }
    
    private void setId(){
        viewPager2 = findViewById(R.id.vp2);
        rgvp = findViewById(R.id.tab_vp);
        
    }
    
    private void changStatusIconCollor(boolean setDark) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            View decorView = window.getDecorView();
            if(decorView != null){
                int vis = decorView.getSystemUiVisibility();
                if(setDark){
                    window.setStatusBarColor(getResources().getColor(R.color.white));
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else{
                    window.setStatusBarColor(getResources().getColor(R.color.black));
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_home:
                viewPager2.setCurrentItem(0);
                break;
            case R.id.rb_msg:
                viewPager2.setCurrentItem(1);
                break;
            case R.id.rg_my:
                viewPager2.setCurrentItem(2);
                break;
        }
    }
}
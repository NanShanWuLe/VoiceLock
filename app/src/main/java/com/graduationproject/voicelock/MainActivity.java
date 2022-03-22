package com.graduationproject.voicelock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
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
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.graduationproject.voicelock.Adapter.VPAdapter;
import com.graduationproject.voicelock.Fragment.OneFragment;
import com.graduationproject.voicelock.Fragment.ThreeFragment;
import com.graduationproject.voicelock.Fragment.TwoFragment;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    ViewPager2 viewPager2;
    BottomNavigationView btmNav;
    VPAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changStatusIconCollor(true);
        verifyPermissions(this);
        setId();
        BottomNavigationItem homeItem = new BottomNavigationItem("Home", ContextCompat.getColor(this, R.color.eggblue), R.drawable.home);
        BottomNavigationItem msgItem = new BottomNavigationItem("Msg", ContextCompat.getColor(this, R.color.origin), R.drawable.msg);
        BottomNavigationItem myItem = new BottomNavigationItem("My", ContextCompat.getColor(this, R.color.green), R.drawable.setting);
        btmNav.addTab(homeItem);
        btmNav.addTab(msgItem);
        btmNav.addTab(myItem);
        btmNav.setItemActiveColorWithoutColoredBackground(R.color.white);
        adapter = new VPAdapter(this);
        viewPager2.setAdapter(adapter);
        adapter.addFragment(new OneFragment());
        adapter.addFragment(new TwoFragment());
        adapter.addFragment(new ThreeFragment());
        viewPager2.setUserInputEnabled(false);
        viewPager2.setCurrentItem(0);
        btmNav.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {
                viewPager2.setCurrentItem(index);
            }
        });
    }
    //申请录音权限

    private static final int GET_RECODE_AUDIO = 1;

    private static String[] PERMISSION_ALL = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    /** 申请录音权限*/
    public static void verifyPermissions(Activity activity) {
        boolean permission = (ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
                || (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED);
        if (permission) {
            ActivityCompat.requestPermissions(activity, PERMISSION_ALL,
                    GET_RECODE_AUDIO);
        }
    }
    
    private void setId(){
        viewPager2 = findViewById(R.id.vp2);
        btmNav = findViewById(R.id.bottomNavigation);
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
        }
    }
}
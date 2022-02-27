package com.graduationproject.voicelock.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.graduationproject.voicelock.R;

public class OneFragment extends Fragment implements View.OnClickListener{
    
    private ImageButton Btn_start;
    private ImageButton Btn_stop;
    private Chronometer chronometer;
    private View view;
    private long mRecordTime = 0;
    private boolean Suspended = true;
    public OneFragment() {
        // Required empty public constructor
    }
    
    // TODO: Rename and change types and number of parameters
    public static OneFragment newInstance(String param1, String param2) {
        OneFragment fragment = new OneFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null){
            view = inflater.inflate(R.layout.fragment_one, container, false);
        }
        setId();
        chronometer.setBase(SystemClock.elapsedRealtime());
        return view;
    }
    
    private void setId(){
        Btn_start = view.findViewById(R.id.record_start);
        Btn_stop = view.findViewById(R.id.record_stop);
        chronometer = view.findViewById(R.id.tx_timer);
        Btn_start.setOnClickListener(this);
        Btn_stop.setOnClickListener(this);
    }

    @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables"})
    @Override
    public void onClick(View v) {
        TextView tx = (TextView) view.findViewById(R.id.tx_start);
        switch (v.getId()){
            case R.id.record_start:
                if (Suspended){
                    /*
                    * 开始录制
                    * */
                    Suspended = false;
                    Toast.makeText(getContext(), "开始录制", Toast.LENGTH_SHORT).show();
                    Btn_start.setBackground(getResources().getDrawable(R.drawable.suspend));
                    tx.setText("暂停");
                    if (mRecordTime != 0) {
                        chronometer.setBase(chronometer.getBase() + (SystemClock.elapsedRealtime() - mRecordTime));
                    } else {
                        chronometer.setBase(SystemClock.elapsedRealtime());
                    }
                    chronometer.start();
                }else {
                    /*
                     * 暂停录制
                     * */
                    Suspended = true;
                    Toast.makeText(getContext(), "暂停录制", Toast.LENGTH_SHORT).show();
                    Btn_start.setBackground(getResources().getDrawable(R.drawable.start));
                    tx.setText("开始");
                    chronometer.stop();
                    mRecordTime = SystemClock.elapsedRealtime();
                }
                break;
            case R.id.record_stop:
                /*
                 * 结束录制
                 * */
                Toast.makeText(getContext(), "结束录制", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
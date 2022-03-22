package com.graduationproject.voicelock.Fragment;

import android.os.Bundle;

import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.graduationproject.voicelock.R;
import com.graduationproject.voicelock.ToolUtils.HttpUtil;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThreeFragment extends Fragment {

    private Button uploadBtn;
    private TextView responseText;
    private View view;
    private HttpUtil httpUtil;
    public ThreeFragment() {
        // Required empty public constructor
    }
    
    // TODO: Rename and change types and number of parameters
    public static ThreeFragment newInstance(String param1, String param2) {
        ThreeFragment fragment = new ThreeFragment();
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
        view = inflater.inflate(R.layout.fragment_three, container, false);
        uploadBtn = (Button) view.findViewById(R.id.upload);
        responseText = (TextView) view.findViewById(R.id.response);
        httpUtil = new HttpUtil();
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String response;
                        try {
                            response = httpUtil.GetHello(httpUtil.GETHELLO);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    responseText.setText(response);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        return view;
    }
}
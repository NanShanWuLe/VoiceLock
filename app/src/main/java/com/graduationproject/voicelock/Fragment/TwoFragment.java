package com.graduationproject.voicelock.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.graduationproject.voicelock.Adapter.FileListAdapter;
import com.graduationproject.voicelock.R;
import com.graduationproject.voicelock.ToolUtils.FileUtil;
import com.graduationproject.voicelock.ToolUtils.HttpUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwoFragment extends Fragment {

    ListView listView;
    Button btnRefresh;
    List<File> list = new ArrayList<>();
    FileListAdapter adapter;
    View view;
    HttpUtil httpUtil;
    public TwoFragment() {
        // Required empty public constructor
    }
    
    // TODO: Rename and change types and number of parameters
    public static TwoFragment newInstance(String param1, String param2) {
        TwoFragment fragment = new TwoFragment();
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
        view = inflater.inflate(R.layout.fragment_two, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        list = FileUtil.getWavFiles();
        adapter = new FileListAdapter(getContext(), list);
        listView.setAdapter(adapter);
        btnRefresh = (Button) view.findViewById(R.id.refresh);
        httpUtil = new HttpUtil();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int position = i;
                new AlertDialog.Builder(getContext())
                        .setMessage("是否删除文件")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TextView itemView = listView.getAdapter().getView(position,null, listView).findViewById(R.id.adapter_file_list_name);
                                deleteFile(itemView.getText().toString());
                                adapter.deleteItem(position);
                            }
                        })
                        .setNegativeButton("上传", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String response;
                                        TextView itemView = listView.getAdapter().getView(position,null, listView).findViewById(R.id.adapter_file_list_name);
                                        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pauseRecordDemo/wav/" + itemView.getText().toString();
                                        Log.d("--File--", "run: " + path);
                                        try {
                                            response = httpUtil.PostWavFile(httpUtil.POSTFILE, path, itemView.getText().toString());
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    new AlertDialog.Builder(getContext())
                                                            .setMessage(response)
                                                            .show();
                                                }
                                            });
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }).start();
                            }
                        })
                        .show();
                return false;
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), FileUtil.getWavFiles().size() + " ", Toast.LENGTH_SHORT).show();
                list = FileUtil.getWavFiles();
                adapter.refreshList(list);
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    private boolean deleteFile(String filepathName){
        filepathName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pauseRecordDemo/wav/" + filepathName;
        File file = new File(filepathName);
        if (file.exists() && file.isFile()){
            if (file.delete()){
                Log.e("--File--", "deleteFile: " + filepathName);
                return true;
            }else{
                Toast.makeText(getContext(), "删除文件" + filepathName + "失败！", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{
            Toast.makeText(getContext(), "文件" + filepathName + "不存在", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onResume() {
        Log.d("AudioRecorder", "onResume: ");
        super.onResume();
    }

    @Override
    public void onStart() {
        Log.d("AudioRecorder", "onStart: ");
        super.onStart();
    }
}
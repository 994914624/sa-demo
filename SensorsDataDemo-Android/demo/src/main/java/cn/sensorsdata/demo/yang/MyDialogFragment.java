package cn.sensorsdata.demo.yang;


import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import cn.sensorsdata.demo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_my_dialog, container);
        view.findViewById(R.id.btn_dialogF).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity()," 点击",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AppTheme);
//        builder.setTitle("对话框");
//        View view = inflater.inflate(R.layout.fragment_dialog, container);
//        builder.setView(view);
//        builder.setPositiveButton("确定", null);
//        builder.setNegativeButton("取消", null);
//        return builder.create();
//    }
}

package cn.sensorsdata.demo.fragment;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import cn.sensorsdata.demo.R;

/**
 * A simple {@link Fragment} subclass.
 */
@TargetApi(11)
public class Myapp2Fragment extends Fragment {


    public Myapp2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_myapp2, container, false);
        view.findViewById(R.id.tv_app_frag2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"2",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}

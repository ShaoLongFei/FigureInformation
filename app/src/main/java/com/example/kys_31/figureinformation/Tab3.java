package com.example.kys_31.figureinformation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 13717 on 2017/8/17.
 */

public class Tab3 extends Fragment {
    private TextView tv_go;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab3,null);
        tv_go=(TextView)view.findViewById(R.id.tv_go);
        tv_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),SplashActivity.class));
                getActivity().finish();
            }
        });


        return view;
    }

}
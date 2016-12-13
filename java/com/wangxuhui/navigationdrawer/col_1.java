package com.wangxuhui.navigationdrawer;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class col_1 extends Fragment {


    public col_1() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//         Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_col_1, container, false);
    }



    @Override
    public void onResume() {
        super.onResume();
    }
}

package com.wangxuhui.navigationdrawer;

import java.util.Comparator;

public class Comparator_By_States implements Comparator<Data_legislator> {
    @Override
    public int compare(Data_legislator o1, Data_legislator o2) {
        return o1.state.compareTo(o2.state);
    }

}

package com.wangxuhui.navigationdrawer;

import java.util.Comparator;

public class Comparator_By_Last_Name implements Comparator<Data_legislator> {
    @Override
    public int compare(Data_legislator o1, Data_legislator o2) {
        return o1.last_name.compareTo(o2.last_name);
    }

}

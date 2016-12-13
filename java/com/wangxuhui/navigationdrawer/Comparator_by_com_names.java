package com.wangxuhui.navigationdrawer;

import java.util.Comparator;

/**
 * Created by bhhg2 on 11/30/2016.
 */

public class Comparator_by_com_names implements Comparator<Data_committee> {
    @Override
    public int compare(Data_committee o1, Data_committee o2) {
        return o1.name.compareTo(o2.name);
    }
}

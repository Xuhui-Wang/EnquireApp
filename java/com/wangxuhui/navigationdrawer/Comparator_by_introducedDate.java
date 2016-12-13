package com.wangxuhui.navigationdrawer;

import java.util.Comparator;

/**
 * Created by bhhg2 on 11/30/2016.
 */

public class Comparator_by_introducedDate implements Comparator<Data_bill> {
    @Override
    public int compare(Data_bill o1, Data_bill o2) {
        return -(o1.introduced_on.compareTo(o2.introduced_on));
    }
}

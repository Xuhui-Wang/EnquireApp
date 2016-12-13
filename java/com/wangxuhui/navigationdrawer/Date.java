package com.wangxuhui.navigationdrawer;

/**
 * Created by bhhg2 on 11/30/2016.
 */

public class Date {
    int year;
    int month;
    int day;

    public Date(String input) {
        String[] array = input.split("-");
        this.year = Integer.parseInt(array[0]);
        this.month = Integer.parseInt(array[1]);
        this.day = Integer.parseInt(array[2]);
    }
    public int dateValue() {
        int value = this.day + this.month * 30 + this.year * 30 * 12;
        return value;
    }
    public String toString() {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        if (this.day < 10)
            return months[this.month - 1] + " 0" + this.day + ", " + this.year;
        else
            return months[this.month - 1] + " " + this.day + ", " + this.year;
    }

}

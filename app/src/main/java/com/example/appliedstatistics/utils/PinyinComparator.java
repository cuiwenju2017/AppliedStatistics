package com.example.appliedstatistics.utils;

import com.example.appliedstatistics.bean.AppInfo;

import java.util.Comparator;

public class PinyinComparator implements Comparator<AppInfo> {
    @Override
    public int compare(AppInfo o1, AppInfo o2) {
        if (o1.getLetters().equals("@") || o2.getLetters().equals("#")) {
            return 1;
        } else if (o1.getLetters().equals("#") || o2.getLetters().equals("@")) {
            return -1;
        } else {
            return o1.getLetters().compareTo(o2.getLetters());
        }
    }
}

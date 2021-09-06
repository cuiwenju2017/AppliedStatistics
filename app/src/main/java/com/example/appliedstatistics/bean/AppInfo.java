package com.example.appliedstatistics.bean;

import android.graphics.drawable.Drawable;

public class AppInfo {

    //序列号
    private static final long serialVersionUID = -6660233212727684115L;
    //名称
    public String name;
    //路径
    public String path;
    //图标
    public Drawable icon;
    //包名
    public String packageName;
    //显示拼音的首字母
    private String letters;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }
}

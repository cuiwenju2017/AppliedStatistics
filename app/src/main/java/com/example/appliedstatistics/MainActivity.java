package com.example.appliedstatistics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.appliedstatistics.adapter.SortAdapter;
import com.example.appliedstatistics.bean.AppInfo;
import com.example.appliedstatistics.databinding.ActivityMainBinding;
import com.example.appliedstatistics.utils.PinyinComparator;
import com.example.appliedstatistics.utils.PinyinUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SortAdapter adapter;
    /**
     * 根据拼音来排列RecyclerView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    private LinearLayoutManager manager;
    private ArrayList<AppInfo> infos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pinyinComparator = new PinyinComparator();
        getAppInfo();
        // 根据a-z进行排序源数据
        Collections.sort(infos, pinyinComparator);
        manager = new GridLayoutManager(this, 4);//列数设置
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(manager);
        adapter = new SortAdapter(this, infos);
        binding.recyclerView.setAdapter(adapter);

        binding.sideBar.setTextView(binding.dialog);
        //设置右侧SideBar触摸监听
        binding.sideBar.setOnTouchingLetterChangedListener(s -> {
            //该字母首次出现的位置
            int position = adapter.getPositionForSection(s.charAt(0));
            if (position != -1) {
                manager.scrollToPositionWithOffset(position, 0);
            }
        });
    }

    /**
     * 获取已安装应用信息
     */
    @SuppressLint("SetTextI18n")
    private void getAppInfo() {
        @SuppressLint("WrongConstant") List<ApplicationInfo> apps = getPackageManager().getInstalledApplications(PackageManager.GET_SIGNATURES);
        infos = new ArrayList<>();
        for (ApplicationInfo info : apps) {
            if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                // 非系统应用
                AppInfo appInfo = new AppInfo();
                appInfo.name = info.loadLabel(getPackageManager()).toString();
                appInfo.icon = info.loadIcon(getPackageManager());
                appInfo.path = info.sourceDir;
                appInfo.packageName = info.packageName;

                //汉字转换成拼音
                String pinyin = PinyinUtils.getPingYin(info.loadLabel(getPackageManager()).toString());
                String sortString = pinyin.substring(0, 1).toUpperCase();
                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]")) {
                    appInfo.setLetters(sortString.toUpperCase());
                } else {
                    appInfo.setLetters("#");
                }
                infos.add(appInfo);
            } else {
                //全部系统应用
            }
        }

        binding.tvNum.setText("您一共安装了" + infos.size() + "个应用程序");
    }
}
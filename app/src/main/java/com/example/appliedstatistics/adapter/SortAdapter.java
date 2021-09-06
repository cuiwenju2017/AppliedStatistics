package com.example.appliedstatistics.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appliedstatistics.bean.AppInfo;
import com.example.appliedstatistics.databinding.ItemAppInfoBinding;

import java.util.List;

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<AppInfo> mData;
    private Context mContext;

    public SortAdapter(Context context, List<AppInfo> data) {
        mInflater = LayoutInflater.from(context);
        mData = data;
        this.mContext = context;
    }

    @Override
    public SortAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemAppInfoBinding binding = ItemAppInfoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SortAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final SortAdapter.ViewHolder holder, final int position) {
        ItemAppInfoBinding bind = ItemAppInfoBinding.bind(holder.itemView);

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> mOnItemClickListener.onItemClick(holder.itemView, position));
        }

        Glide.with(mContext).load(mData.get(position).icon).into(bind.ivIcon);
        bind.tvName.setText(mData.get(position).name);

        bind.ll.setOnClickListener(v -> {
            //通过包名打开应用
            PackageManager packageManager = mContext.getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage(mData.get(position).packageName);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    //**********************itemClick************************
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    //**************************************************************

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(ItemAppInfoBinding itemView) {
            super(itemView.getRoot());
        }
    }

    /**
     * 提供给Activity刷新数据
     *
     * @param list
     */
    public void updateList(List<AppInfo> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    public Object getItem(int position) {
        return mData.get(position);
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return mData.get(position).getLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mData.get(i).getLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }
}

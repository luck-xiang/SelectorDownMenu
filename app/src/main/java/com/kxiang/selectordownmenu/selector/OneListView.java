package com.kxiang.selectordownmenu.selector;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:UserDefinedView
 * 创建人:kexiang
 * 创建时间:2016/9/5 14:42
 */
public class OneListView extends LinearLayout {

    private List<String> list;

    private RecyclerView oneList;

    private OneListRecycleAdapter adapter;

    public OneListView(Context context, List<String> list) {
        super(context);
        this.list = list;
        init();
    }

    public OneListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OneListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setRecycleBackgroud(int res) {
        oneList.setBackgroundResource(res);
    }

    public void init() {

        if (list == null) {
            list = new ArrayList<>();
        }

        int mItemSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 300,
                getContext().getResources().getDisplayMetrics());
        Log.e("mItemSize", "mItemSize" + mItemSize);
        oneList = new RecyclerView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                //ViewGroup.LayoutParams.WRAP_CONTENT
                mItemSize
        );
       // layoutParams.setMargins(30, 0, 30, 0);

        oneList.setLayoutParams(layoutParams);
        oneList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new OneListRecycleAdapter(getContext(), list);
        oneList.setAdapter(adapter);
        addView(oneList);
        setLayoutParams(layoutParams);
    }

    public void notifyDataChange() {
        adapter.notifyDataChange();
    }

    public void setOnRecycleItemSelectListener(OnRecycleItemSelectListener onRecycleItemSelectListener) {
        adapter.setOnRecycleItemSelectListener(onRecycleItemSelectListener);
    }

}

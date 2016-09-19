package com.kxiang.selectordownmenu.selector;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:UserDefinedView
 * 创建人:kexiang
 * 创建时间:2016/9/5 14:42
 */
public class TwoListView extends LinearLayout {

    private List<String> listOne;
    private RecyclerView viewOne;
    private TwoListRecycleAdapter adapterOne;

    private List<String> listTwo;
    private RecyclerView viewTwo;
    private OneListRecycleAdapter adapterTwo;

    private List<TwoBean> twoBeanList;

    private int selectOne;
    private LinearLayout addLinear;

    public TwoListView(Context context, List<TwoBean> twoBeanList) {
        super(context);
        this.twoBeanList = twoBeanList;
        init();
    }

    public TwoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TwoListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {

        if (listOne == null) {
            listOne = new ArrayList<>();
            for (int i = 0; i < twoBeanList.size(); i++) {
                listOne.add(twoBeanList.get(i).getOne());
            }
        }
        if (listTwo == null) {
            listTwo = new ArrayList<>();
        }
        int mItemSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 300,
                getContext().getResources().getDisplayMetrics());

        addLinear = new LinearLayout(getContext());
        LinearLayout.LayoutParams addParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                // ViewGroup.LayoutParams.WRAP_CONTENT
                mItemSize
        );
        addLinear.setLayoutParams(addParams);
        addLinear.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f
        );

        viewOne = new RecyclerView(getContext());
        viewOne.setLayoutParams(layoutParams);
        viewOne.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterOne = new TwoListRecycleAdapter(getContext(), listOne);
        viewOne.setAdapter(adapterOne);
        adapterOne.setOnRecycleItemSelectListener(new OnRecycleItemSelectListener() {
            @Override
            public void OnRecycleItemSelect(View view, int position) {
                selectOne = position;
                listTwo.clear();
                listTwo.addAll(twoBeanList.get(position).getTwo());
                adapterTwo.notifyDataChange();
            }
        });
        addLinear.addView(viewOne);


        viewTwo = new RecyclerView(getContext());
        viewTwo.setLayoutParams(layoutParams);
        viewTwo.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterTwo = new OneListRecycleAdapter(getContext(), listTwo);
        viewTwo.setAdapter(adapterTwo);
        adapterTwo.setOnRecycleItemSelectListener(new OnRecycleItemSelectListener() {
            @Override
            public void OnRecycleItemSelect(View view, int position) {

                if (onTwoItemSelectListenr != null) {

                    onTwoItemSelectListenr.onTwoItemSelect(new int[]{selectOne, position});
                }
            }
        });
        addLinear.addView(viewTwo);

        addView(addLinear);
        setOrientation(LinearLayout.VERTICAL);


    }
    public void setLinearBackgroud(int res) {
        addLinear.setBackgroundResource(res);
    }

    public void notifyDataChange() {
        adapterOne.notifyDataChange();
    }


    public interface OnTwoItemSelectListenr {
        void onTwoItemSelect(int[] item);
    }

    private OnTwoItemSelectListenr onTwoItemSelectListenr;

    public void setOnTwoItemSelectListenr(OnTwoItemSelectListenr onTwoItemSelectListenr) {
        this.onTwoItemSelectListenr = onTwoItemSelectListenr;
    }
}

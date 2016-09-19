package com.kxiang.selectordownmenu.selector;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.kxiang.selectordownmenu.R;

import java.util.ArrayList;

/**
 * 项目名称:UserDefinedView
 * 创建人:kexiang
 * 创建时间:2016/9/6 9:43
 */
public class SelectorDownMenu1 extends LinearLayout {

    private int displayWidth;
    private ArrayList<CheckBox> mCheckBox = new ArrayList<>();
    private ArrayList<View> viewArray;
    private int selectPosition = -1;
    private PopupWindow popupWindow;

    public SelectorDownMenu1(Context context) {
        super(context);
        init();
    }

    public SelectorDownMenu1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SelectorDownMenu1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        displayWidth = ((Activity) getContext()).getWindowManager().getDefaultDisplay().getWidth();
        setOrientation(LinearLayout.HORIZONTAL);
    }


    /**
     * 设置有多少个view，
     */
    public void setValueViews(ArrayList<View> viewArray) {
        int size = viewArray.size();
        this.viewArray = viewArray;
        LayoutInflater inflater = (LayoutInflater) getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < size; i++) {
            CheckBox checkBox = (CheckBox) inflater.inflate(R.layout.selector_button_one,
                    this, false);
            addView(checkBox);
            mCheckBox.add(checkBox);
            checkBox.setTag(i);
            checkBox.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox checkBox = (CheckBox) view;
                    int tag = (int) checkBox.getTag();
                    if (selectPosition != -1 && selectPosition != tag) {
                        mCheckBox.get(selectPosition).setChecked(false);
                    }
                    selectPosition = tag;
                    showPopup();
                }
            });

            viewArray.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        }
    }


    private void showPopup() {

        if (popupWindow == null) {
            popupWindow = new PopupWindow(viewArray.get(selectPosition),
                    displayWidth,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
            //实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            //设置RedactPop弹出窗体的背景
            popupWindow.setBackgroundDrawable(dw);
            popupWindow.setFocusable(false);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mCheckBox.get(selectPosition).setChecked(false);
                }
            });

        }

        if (mCheckBox.get(selectPosition).isChecked()) {
            if (!popupWindow.isShowing()) {
                popupWindow.setContentView(viewArray.get(selectPosition));
                popupWindow.showAsDropDown(this, 0, 0);
            }
            else {

                popupWindow.dismiss();

            }
        }
        else {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();

            }
        }
    }


    public void setItemText(int position, String itemText) {
        mCheckBox.get(position).setText(itemText);
    }

    public void setItemTextDismiss(String itemText) {
        mCheckBox.get(selectPosition).setText(itemText);
        mCheckBox.get(selectPosition).setChecked(false);
        popupWindow.dismiss();
    }

    public void setItemTextRefresh(int position, String itemText) {
        mCheckBox.get(position).setText(itemText);

    }

    public boolean getPopShow() {
        return popupWindow.isShowing();
    }

    public void popDismiss() {
        popupWindow.dismiss();
    }
}

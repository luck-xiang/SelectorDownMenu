package com.kxiang.selectordownmenu.selector;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.kxiang.selectordownmenu.R;

import java.util.ArrayList;

/**
 * 项目名称:UserDefinedView
 * 创建人:kexiang
 * 创建时间:2016/9/6 9:43
 */
public class SelectorDownMenu extends LinearLayout {

    //  private int displayWidth;
    private ArrayList<Button> mButton = new ArrayList<>();
    private ArrayList<View> viewArray;
    private int selectPosition = -1;
    private PopupWindow popupWindow;

    public SelectorDownMenu(Context context) {
        super(context);
        init();
    }

    public SelectorDownMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SelectorDownMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        displayWidth = ((Activity) getContext()).getWindowManager().getDefaultDisplay().getWidth();
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
            Button button = (Button) inflater.inflate(R.layout.selector_button_one,
                    this, false);
            addView(button);
            mButton.add(button);
            button.setTag(i);

            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSelectTitleListener != null) {
                        onSelectTitleListener.onSelectTitle();
                    }
                    Button button = (Button) v;

                    showSelect(button);

                }
            });

            viewArray.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    mButton.get(selectPosition).setSelected(false);
                }
            });
        }
    }


    public ArrayList<Button> getmButton() {
        return mButton;
    }


    public void showSelect(Button button){
        int tag = (int) button.getTag();
        if (selectPosition == -1) {
            selectPosition = tag;
            button.setSelected(true);
            showPopup(button, button, tag);


        }
        else {
            selectButton(
                    mButton.get(selectPosition),
                    mButton.get(tag)
            );
            showPopup(
                    mButton.get(selectPosition),
                    mButton.get(tag),
                    tag
            );
            selectPosition = tag;
        }
    }

    private void selectButton(Button from, Button to) {

        if (from == to) {
            to.setSelected(!to.isSelected());
        }
        else {
            from.setSelected(false);
            to.setSelected(true);
        }
    }


    private void showPopup(Button from, Button to, int tag) {

        if (popupWindow == null) {
            popupWindow = new PopupWindow(viewArray.get(selectPosition),
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
            //实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            //设置RedactPop弹出窗体的背景
            popupWindow.setBackgroundDrawable(dw);
            popupWindow.setFocusable(false);
            popupWindow.setOutsideTouchable(false);

        }

        if (from == to) {

            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
            else {
                popupWindow.setContentView(viewArray.get(tag));
                popupWindow.showAsDropDown(this, 0, 0);
            }
        }
        else {
            popupWindow.dismiss();
            popupWindow.setContentView(viewArray.get(tag));
            popupWindow.showAsDropDown(this, 0, 0);
        }


    }


    public void setItemText(int position, String itemText) {
        mButton.get(position).setText(itemText);
    }

    public void setItemTextDismiss(String itemText) {
        mButton.get(selectPosition).setText(itemText);
        mButton.get(selectPosition).setSelected(false);
        popupWindow.dismiss();

    }

    public void setItemTextRefresh(int position, String itemText) {
        mButton.get(position).setText(itemText);

    }

    public boolean getPopShow() {
        if (popupWindow != null)
            return popupWindow.isShowing();
        return false;
    }

    public void popDismiss() {
        if (popupWindow != null)
            popupWindow.dismiss();

        mButton.get(selectPosition).setSelected(false);
    }

    public interface OnSelectTitleListener {
        void onSelectTitle();
    }

    private OnSelectTitleListener onSelectTitleListener;

    public void setOnSelectTitleListener(OnSelectTitleListener onSelectTitleListener) {
        this.onSelectTitleListener = onSelectTitleListener;
    }
}

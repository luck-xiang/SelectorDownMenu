package com.kxiang.selectordownmenu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * 项目名称:UserDefinedView
 * 创建人:kexiang
 * 创建时间:2016/8/30 16:42
 */
public class SimpleMyAdapter extends RecyclerView.Adapter {

    private LayoutInflater inflater;

    private List<String> list;

    private String[] listTitle = new String[3];

    private int residueHeight = 0;

    public String[] getListTitle() {
        return listTitle;
    }

    public void notifyLostItemAndList() {
        notifyDataSetChanged();
        notifyItemChanged(list.size() + 2);
    }

    public void setResidueHeight(int residueHeight) {
        this.residueHeight = residueHeight - this.residueHeight;
        notifyItemChanged(list.size() + 2);
    }

    public SimpleMyAdapter(Context context, List<String> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
//        DisplayMetrics dm = context.getResources().getDisplayMetrics();
//        float density = dm.density; // 屏幕密度（像素比例：./././.）
//        int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：///）
//        float xdpi = dm.xdpi;
//        float ydpi = dm.ydpi;
//         int screenHeight = dm.heightPixels; // 屏幕高（像素，如：px）
//        int screenWidth = dm.widthPixels; // 屏幕宽（像素，如：px）
//            Log.e("screenWidth", "" + screenHeight);
//           residueHeight=screenHeight;
        residueHeight = DensityUtil.dipTopx(context, 50)
                + DensityUtil.dipTopx(context, 50) + 1;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == title) {
            return new Title(inflater.inflate(R.layout.item_title, parent, false));
        }
        else if (viewType == pop) {
            return new ThreeButton(inflater.inflate(R.layout.selector_button_tree, parent, false));
        }
        else if (viewType == back) {
            return new Back(inflater.inflate(R.layout.item_fill, parent, false));
        }
        else {
            return new OneText(inflater.inflate(R.layout.item_one_text, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case title:
                break;
            case pop:
                ThreeButton threeButton = (ThreeButton) holder;
                threeButton.btn_select_1.setText(listTitle[0]);
                threeButton.btn_select_2.setText(listTitle[1]);
                threeButton.btn_select_3.setText(listTitle[2]);
                threeButton.btn_select_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onSelectItemListener != null) {
                            onSelectItemListener.onSelectPopItem(0);
                        }
                    }
                });
                threeButton.btn_select_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onSelectItemListener != null) {
                            onSelectItemListener.onSelectPopItem(1);
                        }
                    }
                });
                threeButton.btn_select_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onSelectItemListener != null) {
                            onSelectItemListener.onSelectPopItem(2);
                        }
                    }
                });
                break;
            case back:

                Back back = (Back) holder;
                int tempResidueHeight = residueHeight
                        - (DensityUtil.dipTopx(back.ll_all.getContext(), 100) + 1) * list.size();
                Log.e("Back", "" + tempResidueHeight);
                Log.e("Back", "" + DensityUtil.dipTopx(back.ll_all.getContext(), 100));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, tempResidueHeight);
                back.ll_all.setLayoutParams(params);

                break;
            case other:
                int posItem = position - 2;
                OneText oneText = (OneText) holder;
                oneText.tv_one_text_id.setText(list.get(posItem));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return list.size() + 3;
    }


    private class OneText extends RecyclerView.ViewHolder {

        public TextView tv_one_text_id;

        public OneText(View itemView) {
            super(itemView);
            tv_one_text_id = (TextView) itemView.findViewById(R.id.tv_one_text_id);
        }
    }

    private class Title extends RecyclerView.ViewHolder {

        public Title(View itemView) {
            super(itemView);
        }
    }

    private class Back extends RecyclerView.ViewHolder {

        public LinearLayout ll_all;

        public Back(View itemView) {
            super(itemView);
            ll_all = (LinearLayout) itemView.findViewById(R.id.ll_all);
        }
    }


    private class ThreeButton extends RecyclerView.ViewHolder {

        public Button btn_select_1;
        public Button btn_select_2;
        public Button btn_select_3;

        public ThreeButton(View itemView) {
            super(itemView);
            btn_select_1 = (Button) itemView.findViewById(R.id.btn_select_1);
            btn_select_2 = (Button) itemView.findViewById(R.id.btn_select_2);
            btn_select_3 = (Button) itemView.findViewById(R.id.btn_select_3);
        }
    }

    private final int title = 1;
    private final int pop = 2;
    private final int back = 3;
    private final int other = 4;


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return title;
        }
        else if (position == 1) {
            return pop;
        }
        else if (position == list.size() + 2) {
            return back;
        }
        return other;
    }

    public interface OnSelectPopItemListener {
        void onSelectPopItem(int position);
    }

    private OnSelectPopItemListener onSelectItemListener;

    public void setOnSelectItemListener(OnSelectPopItemListener onSelectItemListener) {
        this.onSelectItemListener = onSelectItemListener;
    }
}

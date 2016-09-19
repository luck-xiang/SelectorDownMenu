package com.kxiang.selectordownmenu.selector;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kxiang.selectordownmenu.R;

import java.util.List;

/**
 * 项目名称:UserDefinedView
 * 创建人:kexiang
 * 创建时间:2016/9/6 9:46
 */
public class TwoListRecycleAdapter extends BaseRecycleViewAdapter {


    private int selectPosition = -1;

    public TwoListRecycleAdapter(Context context, List list) {
        super(context, list);
    }

    public void notifyDataChange() {
        selectPosition = -1;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OneList(inflater.inflate(R.layout.item_two_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final OneList oneList = (OneList) holder;

        if (selectPosition == position) {
            oneList.ll_all.setBackgroundResource(R.drawable.choose_item_selected);
        }
        else {
            oneList.ll_all.setBackgroundResource(0);
        }
        oneList.tv_content.setText(list.get(position) + "");
        oneList.ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectPosition != position) {
                    notifyItemChanged(selectPosition);
                }
                oneList.ll_all.setBackgroundResource(R.drawable.choose_item_selected);
                selectPosition = position;

                if (onRecycleItemSelectListener != null)
                    onRecycleItemSelectListener.OnRecycleItemSelect(v, selectPosition);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OneList extends RecyclerView.ViewHolder {

        public LinearLayout ll_all;
        public TextView tv_content;


        public OneList(View itemView) {
            super(itemView);
            ll_all = (LinearLayout) itemView.findViewById(R.id.ll_all);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);

        }
    }

}

package com.kxiang.selectordownmenu;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.kxiang.selectordownmenu.selector.OnRecycleItemSelectListener;
import com.kxiang.selectordownmenu.selector.OneListView;
import com.kxiang.selectordownmenu.selector.SelectorDownMenu;
import com.kxiang.selectordownmenu.selector.TwoBean;
import com.kxiang.selectordownmenu.selector.TwoListView;
import com.ybao.pullrefreshview.layout.BaseFooterView;
import com.ybao.pullrefreshview.layout.BaseHeaderView;
import com.ybao.pullrefreshview.simple.view.NormalFooterView;
import com.ybao.pullrefreshview.simple.view.NormalHeaderView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private RecyclerView rlv_hovering;
    // private LinearLayout sd_selector;
    private SimpleMyAdapter adapter;
    private LinearLayoutManager manager;

    private List<String> list;
//    private String[] listTitle;
//    private int hoveringHeightFinal;
//    private int hoveringT;
//    private int hoveringB;
//    private Handler handler = new Handler();
//    private FrameLayout.LayoutParams params;

    private SelectorDownMenu sd_selector;
    private List<String> the_class;
    private List<TwoBean> twoBeanList;
    private ArrayList<View> views;
    private List<String> name;
    private OneListView left;
    private OneListView middle;
    private TwoListView TwoListView;

    private BaseHeaderView pl_header;
    private BaseFooterView pl_footer;

    private LinearLayout ll_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sd_selector = (SelectorDownMenu) findViewById(R.id.sd_selector);
        ll_all = (LinearLayout) findViewById(R.id.ll_all);
        inintHovering();
        initDownMenu();
        connectPop();
        refreshData();

//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int width = dm.widthPixels;  //屏幕宽
//        int height = dm.heightPixels;  //屏幕高
//        Rect frame = new Rect();
//        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//        int statusBarHeight = frame.top;  //状态栏高
//        int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
//        //标题栏高DisplayMetrics dm = new DisplayMetrics();
//        int titleBarHeight = contentTop - statusBarHeight;
//        Log.e("DisplayMetrics", "width:" + width);
//        Log.e("DisplayMetrics", "statusBarHeight:" + statusBarHeight);
//        Log.e("DisplayMetrics", "contentTop:" + contentTop);
//        Log.e("DisplayMetrics", "frame.height():" + frame.height());
//        // 获取屏幕宽高（方法1）
//        int screenWidth = getWindowManager()
//                .getDefaultDisplay().getWidth(); // 屏幕宽（像素，如：480px）
//        int screenHeight = getWindowManager()
//                .getDefaultDisplay().getHeight(); // 屏幕高（像素，如：800p）
//        // 获取屏幕密度（方法2）
//        Log.e(" getDefaultDisplay",
//                "screenWidth=" + screenWidth + "; screenHeight=" + screenHeight);
    }

    /**
     * 初始化RecyclerView内容
     */
    private void inintHovering() {
//        listTitle = new String[3];

        list = new ArrayList();
        for (int i = 0; i < 0; i++) {
            list.add("" + i);
        }

        rlv_hovering = (RecyclerView) findViewById(R.id.rlv_hovering);
        manager = new LinearLayoutManager(this);
        adapter = new SimpleMyAdapter(this, list);
        adapter.getListTitle()[0] = "姓名";
        adapter.getListTitle()[1] = "班级";
        adapter.getListTitle()[2] = "学号";

        rlv_hovering.setAdapter(adapter);
        rlv_hovering.setLayoutManager(manager);
        rlv_hovering.addItemDecoration(new RecycleItemDivider(this,
                LinearLayoutManager.VERTICAL,
                getResources().getColor(R.color.color_b4b4b4)));


    }

    /**
     * 数据刷新
     */
    private void refreshData() {
        pl_header = (NormalHeaderView) findViewById(R.id.pl_header);
        pl_header.setOnRefreshListener(new BaseHeaderView.OnRefreshListener() {
            @Override
            public void onRefresh(BaseHeaderView baseHeaderView) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        pl_header.stopRefresh();
                        adapter.notifyLostItemAndList();
                    }
                }, 3000);
            }
        });
        pl_footer = (NormalFooterView) findViewById(R.id.pl_footer);
        pl_footer.setOnLoadListener(new BaseFooterView.OnLoadListener() {
            @Override
            public void onLoad(BaseFooterView baseFooterView) {
                pl_footer.stopLoad();
                list.add("1");
                adapter.notifyLostItemAndList();
            }
        });


    }

    /**
     * 初始化悬浮框内容
     */
    private void initDownMenu() {

        name = new ArrayList<>();
        name.add("姓名1");
        name.add("姓名2");
        name.add("姓名3");
        name.add("姓名4");
        left = new OneListView(this, name);
        left.setRecycleBackgroud(R.drawable.choosearea_bg_left);
        left.setOnRecycleItemSelectListener(new OnRecycleItemSelectListener() {
            @Override
            public void OnRecycleItemSelect(View view, int position) {
                sd_selector.setItemTextDismiss(name.get(position));
//                listTitle[0] = name.get(position);
                adapter.getListTitle()[0] = name.get(position);
                adapter.notifyItemChanged(1);
            }
        });


        the_class = new ArrayList<>();
        the_class.add("班级1");
        the_class.add("班级2");
        the_class.add("班级3");
        the_class.add("班级4");
        middle = new OneListView(this, the_class);
        middle.setRecycleBackgroud(R.drawable.choosearea_bg_mid);
        middle.setOnRecycleItemSelectListener(new OnRecycleItemSelectListener() {
            @Override
            public void OnRecycleItemSelect(View view, int position) {
                sd_selector.setItemTextDismiss(the_class.get(position));
//                listTitle[1] = the_class.get(position);
                adapter.getListTitle()[1] = the_class.get(position);
                adapter.notifyItemChanged(1);
            }
        });

        twoBeanList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TwoBean twoBean = new TwoBean();
            twoBean.setOne("第一：" + i);
            List<String> strings = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                strings.add("第" + i + ":" + j);
                strings.add("第" + i + ":" + j);
            }
            twoBean.setTwo(strings);
            twoBeanList.add(twoBean);
        }
        TwoListView = new TwoListView(this, twoBeanList);
        TwoListView.setLinearBackgroud(R.drawable.choosearea_bg_right);
        TwoListView.setOnTwoItemSelectListenr(new TwoListView.OnTwoItemSelectListenr() {
            @Override
            public void onTwoItemSelect(int[] item) {
                sd_selector.setItemTextDismiss(twoBeanList.get(item[0]).getTwo().get(item[1]));
//                listTitle[2] = twoBeanList.get(item[0]).getTwo().get(item[1]);
                adapter.getListTitle()[2] = twoBeanList.get(item[0]).getTwo().get(item[1]);
                adapter.notifyItemChanged(1);
            }
        });

        // left.setBackgroundResource(R.drawable.choosearea_bg_left);
        // middle.setBackgroundResource(R.drawable.choosearea_bg_mid);
        //TwoListView.setBackgroundResource(R.drawable.choosearea_bg_right);

        views = new ArrayList<>();
        views.add(left);
        views.add(middle);
        views.add(TwoListView);
        sd_selector.setValueViews(views);
        sd_selector.setItemText(0, "姓名");
        sd_selector.setItemText(1, "班级");
        sd_selector.setItemText(2, "学号");


        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.add("姓名5");
                name.add("姓名6");
                name.add("姓名7");
                name.add("姓名8");
                left.notifyDataChange();
                sd_selector.setItemTextRefresh(0, "姓名");
            }
        });

    }

    /**
     * RecyclerView和悬浮框关联
     */
    private void connectPop() {

        adapter.setOnSelectItemListener(new SimpleMyAdapter.OnSelectPopItemListener() {
            @Override
            public void onSelectPopItem(int position) {

                Log.e("ll_all", "" + ll_all.getHeight());
//                list.clear();
//                adapter.notifyLostItemAndList();

                selectBack();
                sd_selector.showSelect(sd_selector.getmButton().get(position));
            }
        });

        rlv_hovering.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                switch (manager.findFirstCompletelyVisibleItemPosition()) {

                    case 0:
                        sd_selector.setVisibility(View.GONE);
//                        sd_selector.layout(0, hoveringHeightFinal,
//                               sd_selector.getWidth(), 2 * hoveringHeightFinal);

                        break;
                    case 1:
                        sd_selector.setVisibility(View.GONE);
//                        sd_selector.layout(0, hoveringT,
//                                sd_selector.getWidth(), hoveringB);
                        break;
                    default:
                        sd_selector.setVisibility(View.VISIBLE);
//                        sd_selector.layout(0, 0, sd_selector.getWidth(), hoveringHeightFinal);
                        break;
                }

                sd_selector.setOnSelectTitleListener(new SelectorDownMenu.OnSelectTitleListener() {
                    @Override
                    public void onSelectTitle() {
                        selectBack();
                    }
                });

//                Log.e("getChildAt", "" + (manager.getChildAt(0).getTop() - 20));
//                Log.e("getChildAt", "findFirstVisibleItemPosition:" + manager.findFirstVisibleItemPosition());
//                Log.e("getChildAt", "findFirstCompletelyVisibleItemPosition:"
//                        + manager.findFirstCompletelyVisibleItemPosition());
//                hoveringT = hoveringT - dy;
//                hoveringB = hoveringB - dy;


            }
        });


//        hoveringT = hoveringHeightFinal = DensityUtil.dipTopx(this, 110);
//        hoveringB = 2 * hoveringHeightFinal;

//        int top = DensityUtil.dipTopx(this, 50);
//        params = new FrameLayout.LayoutParams
//                (LinearLayout.LayoutParams.MATCH_PARENT, top);
//        // params.setMargins(0, top, 0, 0);
//        sd_selector.setLayoutParams(params);
//


    }

    private void selectBack() {
//        params.setMargins(0, 0, 0, 0);
//        Toast.makeText(MainActivity.this, "" +
//                rlv_hovering.getScrollState(), Toast.LENGTH_SHORT).show();

        if (rlv_hovering.getScrollState() != 2) {
            manager.scrollToPositionWithOffset(1, -1);
//            manager.scrollToPosition(1);
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    hoveringT = 0;
//                    hoveringB = hoveringHeightFinal;
//                    //  sd_selector.layout(0, hoveringT, sd_selector.getWidth(), hoveringB);
//                }
//            }, 10);
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Log.e("ll_all", "ll_all:" + ll_all.getHeight());
            adapter.setResidueHeight(ll_all.getHeight());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (sd_selector.getPopShow()) {
            sd_selector.popDismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

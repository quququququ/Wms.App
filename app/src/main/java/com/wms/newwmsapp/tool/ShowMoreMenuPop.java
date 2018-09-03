package com.wms.newwmsapp.tool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.activity.DownToOtherActivity;
import com.wms.newwmsapp.activity.StockCheckTaskActivity;
import com.wms.newwmsapp.activity.StockInSearchActivity;
import com.wms.newwmsapp.activity.UpToOtherActivity;

/**
 * Created by cheng on 2018/7/4.
 */

public class ShowMoreMenuPop extends PopupWindow {
    private View mMenuView;
    private Context context;
    private int popupWidth;
    private int popupHeight;
    private Window window;
    private LinearLayout llSearchIn, llPointTask, llToDownOther, llUpToOther;

    public ShowMoreMenuPop(final Context context, Window window) {
        super(context);
        this.context = context;
        this.window = window;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_show_more_menu, null);

        llSearchIn = (LinearLayout) mMenuView.findViewById(R.id.StockInSearch_lin);
        llPointTask = (LinearLayout) mMenuView.findViewById(R.id.StockCheckTaskByUserCode_lin);
        llToDownOther = (LinearLayout) mMenuView.findViewById(R.id.ll_down_to_other);
        llUpToOther = (LinearLayout) mMenuView.findViewById(R.id.ll_up_to_other);

        //获取自身的长宽高
//        mMenuView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//        popupHeight = mMenuView.getMeasuredHeight();
//        popupWidth = mMenuView.getMeasuredWidth();

        //库内查询
        llSearchIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StockInSearchActivity.class);
                context.startActivity(intent);
            }
        });

        //盘点任务
        llPointTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StockCheckTaskActivity.class);
                context.startActivity(intent);
            }
        });

        //移库上架
        llUpToOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpToOtherActivity.class);
                context.startActivity(intent);
            }
        });

        //移库下架
        llToDownOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DownToOtherActivity.class);
                context.startActivity(intent);
            }
        });

        this.setBackgroundDrawable(new BitmapDrawable());

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.Animation_AppCompat_Dialog);
        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable();
        //设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(dw);
//        backgroundAlpha(1f);

        darkenBackground(0.5f);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
            }
        });

    }

    private void darkenBackground(Float bgcolor){
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = bgcolor;

        ((Activity)context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity)context).getWindow().setAttributes(lp);

    }

    public void showUp() {
//        //获取需要在其上方显示的控件的位置信息
//        int[] location = new int[2];
//        v.getLocationOnScreen(location);
//        //在控件上方显示
//        showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);
        showAtLocation(((Activity)context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);

    }


    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) context).getWindow().setAttributes(lp);
    }



}

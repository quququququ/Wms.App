package com.wms.newwmsapp.tool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.activity.BatchPickupConfirmActivity;
import com.wms.newwmsapp.activity.BatchPickupDetailActivity;

/**
 * Created by cheng on 2018/9/5.
 */

public class PrintCodePop extends PopupWindow {
    private View mMenuView;
    private Context context;
    private int popupWidth;
    private int popupHeight;
    private Window window;
    private Bitmap bitmap;
    private ImageView ivCode;
    private String needTo;

    public PrintCodePop(final Context context, Window window, Bitmap bitmap, final String needTo) {
        super(context);
        this.context = context;
        this.window = window;
        this.bitmap = bitmap;
        this.needTo = needTo;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_show_code, null);
        ivCode = (ImageView) mMenuView.findViewById(R.id.iv_show_code);

        ivCode.setImageBitmap(ImageUtil.adjustPhotoRotation(bitmap,90));
        //获取自身的长宽高
//        mMenuView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//        popupHeight = mMenuView.getMeasuredHeight();
//        popupWidth = mMenuView.getMeasuredWidth();

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
                if(needTo.equals("1")){
                    Intent intent = new Intent(context, BatchPickupConfirmActivity.class);
                    context.startActivity(intent);
                    BatchPickupDetailActivity.pickupDetailActivity.finish();
                }else if(needTo.equals("2")){
                    Intent intent = new Intent(context, BatchPickupConfirmActivity.class);
                    context.startActivity(intent);
                    BatchPickupDetailActivity.pickupDetailActivity.finish();
                }
                setBackgroundAlpha(1.0f);
            }
        });

    }
    public void showUp() {
//        //获取需要在其上方显示的控件的位置信息
//        int[] location = new int[2];
//        v.getLocationOnScreen(location);
//        //在控件上方显示
//        showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);
        showAtLocation(((Activity)context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);

    }

    private void darkenBackground(Float bgcolor){
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = bgcolor;

        ((Activity)context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity)context).getWindow().setAttributes(lp);

    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) context).getWindow().setAttributes(lp);
    }
}

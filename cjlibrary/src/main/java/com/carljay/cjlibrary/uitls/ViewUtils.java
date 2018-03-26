package com.carljay.cjlibrary.uitls;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by 豪杰 on 2016/3/1.
 */
public class ViewUtils {


    /**
     * 去掉listview 拉到顶部或底部时出现的黑色阴影
     * @param listView
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void removeListViewShadow(AbsListView listView){
        if (Integer.parseInt(Build.VERSION.SDK) >= 9) {
            listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    /**
     * 去掉ScrollView 拉到顶部或底部时出现的黑色阴影
     * @param scrollView
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void removeScrollViewShadow(ScrollView scrollView){
        if (Integer.parseInt(Build.VERSION.SDK) >= 9) {
            scrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }
    public static void textViewSetDrawable(Context context,@DrawableRes int res, TextView view, int position){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), res);
        BitmapDrawable bd = new BitmapDrawable(context.getResources(),bitmap);
        bd.setBounds(0,0,bitmap.getWidth(),bitmap.getHeight());
        switch (position){
            case 0:
                view.setCompoundDrawables(bd,null,null,null);
                break;
            case 1:
                view.setCompoundDrawables(null,bd,null,null);
                break;
            case 2:
                view.setCompoundDrawables(null,null,bd,null);
                break;
            case 3:
                view.setCompoundDrawables(null,null,null,bd);
                break;
        }
    }
}

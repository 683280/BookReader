package com.carljay.cjlibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/12/30.
 */

public class RadioViewGroup extends ViewGroup {

    private float ratioHeight = -1;

    public RadioViewGroup(Context context) {
        this(context,null);
    }

    public RadioViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float width = MeasureSpec.getSize(widthMeasureSpec);
        float height = MeasureSpec.getSize(heightMeasureSpec);
        if (ratioHeight == 0) {
//            Drawable drawable = getBackground();
//            if (drawable instanceof BitmapDrawable) {
//                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
//                height = getHeightSize(bitmap);
//            } else if (drawable instanceof GlideBitmapDrawable) {
//                Bitmap bitmap = ((GlideBitmapDrawable) drawable).getBitmap();
//                height = getHeightSize(bitmap);
//            }
        }else {
            if (ratioHeight == -1){
                height = MeasureSpec.getSize(widthMeasureSpec);
            }else{
                height = width / ratioHeight;
            }
        }
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec((int) height,MeasureSpec.EXACTLY));
    }
    public void setRatioHeight(float ratioHeight) {
        this.ratioHeight = ratioHeight;
        postInvalidate();
    }
    public float getRatioHeight() {
        return ratioHeight;
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0 ; i < count ; i++){
            View view = getChildAt(i);
            view.layout(l,t,r,b);
        }
    }
}

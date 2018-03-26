package com.carljay.cjlibrary.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/12/23.
 */

public class RatioImageView extends ImageView {

    private float ratio = -1;
    private float ratioHeight = -1;
    public RatioImageView(Context context) {
        this(context,null);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
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
    private int getHeightSize(Bitmap bitmap){
        if (ratio == -1)
            return (int) (bitmap.getWidth() * ratio);
        else{
            return (int) (bitmap.getWidth() / 1.0f / bitmap.getHeight() * ratio);
        }
    }

    public float getRatioHeight() {
        return ratioHeight;
    }

    public void setRatioHeight(float ratioHeight) {
        this.ratioHeight = ratioHeight;
        postInvalidate();
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
        postInvalidate();
    }
}

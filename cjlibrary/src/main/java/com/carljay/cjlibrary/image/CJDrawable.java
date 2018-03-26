package com.carljay.cjlibrary.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;

import com.carljay.cjlibrary.helper.UiHelper;

/**
 * Created by 豪杰 on 2016/12/24.
 */

public class CJDrawable extends Drawable {

    private BitmapState state;

    private int width,height;

    private final Rect destRect = new Rect();
    private boolean applyGravity;

    public CJDrawable(Bitmap bitmap){
        this(new BitmapState(bitmap));
    }

    CJDrawable(BitmapState state) {
        this.state = state;

        int density = UiHelper.INSTANCE.getResources().getDisplayMetrics().densityDpi;
        int targetDensity = density == 0 ? DisplayMetrics.DENSITY_DEFAULT : density;
        state.targetDensity = targetDensity;
        width = state.bitmap.getScaledWidth(targetDensity);
        height = state.bitmap.getScaledHeight(targetDensity);
    }
    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        applyGravity = true;
    }
    @Override
    public void draw(Canvas canvas) {
        if (applyGravity) {
            Gravity.apply(Gravity.FILL, width, height, getBounds(), destRect);
            applyGravity = false;
        }
        canvas.drawBitmap(state.bitmap, null, destRect, state.paint);
    }

    @Override
    public void setAlpha(int alpha) {
        int currentAlpha = state.paint.getAlpha();
        if (currentAlpha != alpha) {
            state.setAlpha(alpha);
            invalidateSelf();
        }
    }

    @Override
    public int getIntrinsicWidth() {
        return width;
    }

    @Override
    public int getIntrinsicHeight() {
        return height;
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        state.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Nullable
    @Override
    public ConstantState getConstantState() {
        return state;
    }

    static class BitmapState extends ConstantState {
        private static final int DEFAULT_PAINT_FLAGS = Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG;
        final Bitmap bitmap;
        int targetDensity;
        Paint paint = new Paint(DEFAULT_PAINT_FLAGS);

        public BitmapState(Bitmap bitmap){
            this.bitmap = bitmap;
        }

        @NonNull
        @Override
        public Drawable newDrawable() {
            return new CJDrawable(this);
        }
        @Override
        public Drawable newDrawable(Resources res) {
            return new CJDrawable(this);
        }
        @Override
        public int getChangingConfigurations() {
            return 0;
        }

        public void setAlpha(int alpha) {
            paint.setAlpha(alpha);
        }

        public void setColorFilter(ColorFilter colorFilter) {
            paint.setColorFilter(colorFilter);
        }
    }

}

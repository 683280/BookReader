package com.carljay.cjlibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;


/**
 * Created by carljay on 2017/2/13.
 */

public class CjVideoView extends SurfaceView implements SurfaceHolder.Callback {

    private IjkMediaPlayer player = new IjkMediaPlayer();

    public CjVideoView(Context context) {
        this(context,null);
    }

    public CjVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        player.setSurface(holder.getSurface());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        player.setSurface(holder.getSurface());
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        player.setSurface(null);
    }

    public IjkMediaPlayer getPlayer(){
        return player;
    }
}

package com.carljay.cjlibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.carljay.cjlibrary.R;
import com.carljay.cjlibrary.helper.UiHelper;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;


/**
 * Created by Administrator on 2016/12/30.
 */

public class SingleVideoView extends RadioViewGroup implements View.OnClickListener, SurfaceHolder.Callback, IMediaPlayer.OnPreparedListener, IMediaPlayer.OnErrorListener, IMediaPlayer.OnInfoListener {

    private static IjkMediaPlayer player = new IjkMediaPlayer();

    private ImageView playBtn;

    private ImageView videoImg;

    private SurfaceView surfaceView;

    private String TAG = "SingleVideoView";

    private int playSize = (int) UiHelper.INSTANCE.dpToPx(25);

    private int position;
    private String videoUrl;
    private String imgUrl;

    public SingleVideoView(Context context) {
        this(context,null);
    }

    public SingleVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        playBtn = new ImageView(context);
        playBtn.setOnClickListener(this);
        playBtn.setImageResource(R.drawable.play_video);

        videoImg = new ImageView(context);

        surfaceView = new SurfaceView(context);
        surfaceView.getHolder().addCallback(this);

        player.setOnPreparedListener(this);
        player.setOnErrorListener(this);
        player.setOnInfoListener(this);

        addView(surfaceView);
        addView(videoImg);
        addView(playBtn);
    }
    public void bindVideo(int position,String imgUrl,String videoUrl){
        this.videoUrl = videoUrl;
        this.imgUrl = imgUrl;
        this.position = position;

    }

    public void start(){
        try {
            player.setDataSource(videoUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.prepareAsync();
//        player.reset();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        surfaceView.layout(l, t, r, b);
        videoImg.layout(l, t, r, b);
        playBtn.layout(r / 2 - playSize, b / 2 - playSize, r / 2 + playSize, b / 2 + playSize);
    }

    @Override
    public void onClick(View v) {
        start();
    }

    public String getImageUrl() {
        return imgUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        player.setSurface(holder.getSurface());
        Log.e("surfaceCreated","surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        player.setSurface(holder.getSurface());
        Log.e("surfaceChanged","surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        player.setSurface(null);
        Log.e("surfaceDestroyed","surfaceDestroyed");
    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        Log.e("onPrepared","onPrepared");
        iMediaPlayer.start();
    }

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
        Log.e("onError","onError i = " + i + "  i1 =" + i1);
        return false;
    }

    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
        Log.e("onInfo","onInfo i = " + i + "  i1 =" + i1);
        switch (i){
            case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                videoImg.setVisibility(GONE);
                playBtn.setVisibility(GONE);
                break;
        }
        return false;
    }
}

package com.carljay.cjlibrary.view

import android.content.Context
import android.media.MediaPlayer
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

import com.carljay.cjlibrary.R
import com.carljay.cjlibrary.client.CJClient
import com.carljay.cjlibrary.helper.ImageHepler
import com.carljay.cjlibrary.helper.UiHelper

import java.io.IOException

/**
 * Created by Administrator on 2016/12/25.
 */

class RatioVideoView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : RadioViewGroup(context, attrs), MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, View.OnClickListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback {


    private val imageView: ImageView

    private val playView: ImageView

    private val surfaceView: SurfaceView

    private val mediaPlayer: MediaPlayer

    private val holder: SurfaceHolder

    private val loading: ImageView

    private val loadingSize = UiHelper.dpToPx(30).toInt()

    private val loadingPadding = UiHelper.dpToPx(10).toInt()

    private val playSize = UiHelper.dpToPx(25).toInt()

    private val loadingAnim: Animation
    var videoUrl: String? = null
        set(url) {
            field = url
            val proxyUrl = CJClient.proxy!!.getProxyUrl(url)
            try {
                mediaPlayer.reset()
                mediaPlayer.setDataSource(proxyUrl)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    var imageUrl: String? = null
        set(url) {
            field = url
            ImageHepler.load(url!!).to(imageView)
        }

    init {

        loadingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.loading_anim_small)
        loadingAnim.repeatCount = Animation.INFINITE

        loading = ImageView(context)
        loading.setImageResource(R.drawable.video_loading)
        loading.visibility = View.GONE

        playView = ImageView(context)
        playView.setImageResource(R.drawable.play_video)
        playView.setOnClickListener(this)

        imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        //        imageView.setOnClickListener(this);

        surfaceView = SurfaceView(context)

        holder = surfaceView.holder
        holder.addCallback(this)

        mediaPlayer = MediaPlayer()
        mediaPlayer.setOnCompletionListener(this)
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnErrorListener(this)
        mediaPlayer.setOnInfoListener(this)
        mediaPlayer.setOnSeekCompleteListener(this)
        mediaPlayer.setOnVideoSizeChangedListener(this)

        addView(surfaceView)
        addView(imageView)
        addView(loading)
        addView(playView)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        imageView.layout(l, t, r, b)
        surfaceView.layout(l, t, r, b)
        loading.layout(r - loadingSize - loadingPadding, b - loadingSize - loadingPadding, r - loadingPadding, b - loadingPadding)
        playView.layout(r / 2 - playSize, b / 2 - playSize, r / 2 + playSize, b / 2 + playSize)

    }

    fun start() {
        if (mediaPlayer.isPlaying)
            return
        try {
            mediaPlayer.prepareAsync()
            loading.visibility = View.VISIBLE
            loading.startAnimation(loadingAnim)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onCompletion(mp: MediaPlayer) {
        Log.e("TAG", "onPrepared")
        imageView.visibility = View.VISIBLE
        playView.visibility = View.VISIBLE
    }

    override fun onPrepared(mp: MediaPlayer) {
        Log.e("TAG", "onPrepared")
        //        mediaPlayer.start();
        mp.start()
    }

    override fun onClick(v: View) {
        start()
    }

    override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        Log.e("TAG", "onError   what = $what  extra = $extra")
        imageView.visibility = View.VISIBLE
        playView.visibility = View.VISIBLE
        when (what) {
            MediaPlayer.MEDIA_ERROR_SERVER_DIED -> {
            }
            MediaPlayer.MEDIA_ERROR_UNKNOWN -> {
            }
            else -> {
            }
        }
        return false
    }

    override fun onInfo(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        Log.e("TAG", "onInfo   what = $what  extra = $extra")
        // 当一些特定信息出现或者警告时触发
        when (what) {
            MediaPlayer.MEDIA_INFO_BUFFERING_START, MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> {
                imageView.visibility = View.GONE
                playView.visibility = View.GONE
                loading.visibility = View.GONE
                loadingAnim.cancel()
            }
            MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING -> {
            }
            MediaPlayer.MEDIA_INFO_METADATA_UPDATE -> {
            }
            703 -> {
            }
            MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING -> {
                loading.visibility = View.VISIBLE
                loading.startAnimation(loadingAnim)
            }
            MediaPlayer.MEDIA_INFO_NOT_SEEKABLE -> {
            }
        }
        return false
    }

    override fun onSeekComplete(mp: MediaPlayer) {
        Log.e("TAG", "onSeekComplete")
    }

    override fun onVideoSizeChanged(mp: MediaPlayer, width: Int, height: Int) {
        //        setRatio(height / 1.0f / width);
        Log.e("TAG", "onVideoSizeChanged")
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.e("TAG", "surfaceCreated")
        imageView.visibility = View.VISIBLE
        playView.visibility = View.VISIBLE
        mediaPlayer.setDisplay(holder)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        //        setRatio(height / 1.0f / width);
        Log.e("TAG", "surfaceChanged")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.e("TAG", "surfaceDestroyed")
    }
}

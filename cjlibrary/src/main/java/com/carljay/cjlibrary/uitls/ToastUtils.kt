package com.carljay.cjlibrary.uitls

import android.widget.Toast
import com.carljay.cjlibrary.client.CJClient

/**
 * Created by carljay on 2017/5/25.
 */

object ToastUtils{
    fun show(msg : String,duration :Int = Toast.LENGTH_SHORT){
        Toast.makeText(CJClient.mContext,msg,duration).show()
    }
}
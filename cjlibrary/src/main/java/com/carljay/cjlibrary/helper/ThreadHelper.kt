package com.carljay.cjlibrary.helper

import java.util.concurrent.Executors

object ThreadHelper {

    private val cachedThreadPool = Executors.newCachedThreadPool()
    fun execute(runnable: () -> Unit) {
        cachedThreadPool.execute(runnable)
    }
    fun execute(runnable: Runnable) {
        cachedThreadPool.execute(runnable)
    }

    fun runOnThread(runnable: Runnable) {
        UiHelper.postThreadOnMain(runnable)
    }
    fun runOnThread(runnable: () -> Unit) {
        UiHelper.postThreadOnMain(runnable)
    }
}

package com.tck.jni.audiorecord

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


/**
 *<p>description:</p>
 *<p>created on: 2020/12/4 11:20</p>
 * @author tck
 * @version v3.7.5
 *
 */
class MyExecutors private constructor() {

    companion object {
        val instance: MyExecutors by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { MyExecutors() }

        private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
        private val CORE_POOL_SIZE = 2.coerceAtLeast((CPU_COUNT - 1).coerceAtMost(4))
        private val MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1
        private const val KEEP_ALIVE_SECONDS = 30
        private val sPoolWorkQueue: BlockingQueue<Runnable> = LinkedBlockingQueue(5)
        private var THREAD_POOL_EXECUTOR: ThreadPoolExecutor

        init {
            val threadPoolExecutor = ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS.toLong(), TimeUnit.SECONDS,
                sPoolWorkQueue
            )
            threadPoolExecutor.allowCoreThreadTimeOut(true)
            THREAD_POOL_EXECUTOR = threadPoolExecutor
        }
    }

    fun execute(runnable: Runnable) {
        THREAD_POOL_EXECUTOR.execute(runnable)
    }
}
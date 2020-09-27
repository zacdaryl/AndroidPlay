package com.jzm.anp.thread

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import kotlin.random.Random

object ThreadRunner {
    private val fixedThreadPoolService:ExecutorService = Executors.newFixedThreadPool(5)
    private val cachedThreadPoolService:ExecutorService = Executors.newCachedThreadPool()
    private val scheduledThreadPoolService:ScheduledExecutorService = Executors.newScheduledThreadPool(5)
    private val singleThreadExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    fun runInFixedThreadPool() {
        val set = HashSet<Long>()

        for (i in 0..10) {
            fixedThreadPoolService.execute {
                set.add(Thread.currentThread().id)
                val millis = Random.nextLong(6000)
                println("task #$i, thread-${Thread.currentThread().id}, sleep ${millis / 1000}s")
                Thread.sleep(millis)
                println("task #$i, ----> continue run task in thread-${Thread.currentThread().id}")
            }
        }

        fixedThreadPoolService.shutdown()

        //查看set中线程数量可知线程池中线程数目固定，后续来的任务等待前边线程执行完毕后再继续执行
        while (true) {
            if (fixedThreadPoolService.isTerminated) {
                println("all threads in the thread pool is shut down")
                println("count of threads: ${set.size} threads id: $set")
                break
            }

            Thread.sleep(500)
        }
    }

    fun runInCachedThreadPool() {
        val set = HashSet<Long>()

        for (i in 0..20) {
            cachedThreadPoolService.execute {
                set.add(Thread.currentThread().id)

                val millis = Random.nextLong(10000)
                println("task #$i, thread-${Thread.currentThread().id}, sleep $millis millis")
                Thread.sleep(2000)
                println("task #$i, ----> continue run task in thread-${Thread.currentThread().id}")
            }
        }

        //停留3s，等待上述线程执行完成。后续再次向线程池中加任务，最后看最终创建的线程总数
        //停留后，上述线程执行完成，于是后续会重用已经创建的线程
        Thread.sleep(3000)

        for (i in 0..10) {
            cachedThreadPoolService.execute {
                set.add(Thread.currentThread().id)

                val millis = Random.nextLong(10000)
                println("task #$i, thread-${Thread.currentThread().id}, sleep $millis millis")
                Thread.sleep(millis)
                println("task #$i, ----> continue run task in thread-${Thread.currentThread().id}")
            }
        }

        cachedThreadPoolService.shutdown()

        while (true) {
            if (cachedThreadPoolService.isTerminated) {
                println("all threads in the thread pool is shut down")
                println("count of threads: ${set.size} threads id: $set")
                break
            }

            Thread.sleep(500)
        }
    }

    fun runInScheduledThreadPool() {
        println("run in scheduled thread pool start")
        for (i in 0..10) {
            scheduledThreadPoolService.schedule({
                println("run in thread-${Thread.currentThread().id}")
            }, 10L, TimeUnit.SECONDS)
        }

        val scheduledFuture = scheduledThreadPoolService.scheduleAtFixedRate({
            println("fixed rate run in thread-${Thread.currentThread().id}")
        },15L, 3L, TimeUnit.SECONDS)

        fixedThreadPoolService.execute{
            println("before cancel sleep 20s")
            Thread.sleep(20000)
            println("start cancel")
            scheduledFuture.cancel(false)
        }

    }

    fun runInSingleThreadExecutor() {
        for (i in 0..9) {
            singleThreadExecutor.execute{
                Thread.sleep(2000)
                println("$i run in single thread executor-${Thread.currentThread().id}")
            }
        }
    }
}
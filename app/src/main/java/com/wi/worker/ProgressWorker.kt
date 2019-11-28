package com.wi.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class ProgressWorker(context: Context, parameters: WorkerParameters) : Worker(context, parameters) {

    companion object {
        const val ProgressKey = "Progress"
        private const val delayDuration = 50L
        private const val NOTIFICATION_ID=12
    }


    override fun doWork(): Result {

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel("winds", "gaames", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        val notification = NotificationCompat.Builder(applicationContext, "winds")
            .setContentTitle("Picture Download")
            .setContentText("Download in progress")
            .setSmallIcon(android.R.drawable.stat_sys_upload)

        for (i in 0..100) {
            setProgressAsync(Data.Builder().putInt(ProgressKey, i).build())
            notification.setProgress(100, i, false)
            notificationManager.notify(NOTIFICATION_ID, notification.build());
            try {
                Thread.sleep(delayDuration)
            } catch (e: Exception) {
            }
        }
        notificationManager.cancel(NOTIFICATION_ID)
        notification.setContentText("Download complete")
            .setProgress(0, 0, false);
        notificationManager.notify(1, notification.build());

        return Result.success()
    }


}
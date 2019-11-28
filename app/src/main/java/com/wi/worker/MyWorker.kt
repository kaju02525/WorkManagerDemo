package com.wi.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {

        val mesGetData = inputData.getString("data") ?: ""

        displayNotification("My Worker", mesGetData)

        val sendDataActivity = Data.Builder()
            .putString("ok", "This is O Data").build()



        return Result.success(sendDataActivity)
    }


    private fun displayNotification(title: String, desc: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel("winds", "gaames", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        val notification = NotificationCompat.Builder(applicationContext, "winds")
            .setContentTitle(title)
            .setContentText(desc)
            .setSmallIcon(android.R.drawable.stat_sys_upload)
            .setProgress(100, 0, false)
            .setAutoCancel(false);
        notificationManager.notify(1, notification.build());
    }

}
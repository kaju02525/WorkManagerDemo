package com.wi.worker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivityPeriodicWorkRequest :AppCompatActivity(){

   // https://www.simplifiedcoding.net/android-workmanager-tutorial/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val periodicWrokRequest=PeriodicWorkRequest.Builder(ProgressWorker::class.java,2,TimeUnit.SECONDS).build()
        val instance=WorkManager.getInstance(this)

        btnSend.setOnClickListener {
            instance.enqueue(periodicWrokRequest)
        }


        instance.getWorkInfoByIdLiveData(periodicWrokRequest.id).observe(
            this, Observer {
                if(it !=null) {
                    val progress=it.progress.getInt(ProgressWorker.ProgressKey,0)
                    tvStatus.text= progress.toString()
                    progressbar.progress=progress
                }
                tvStatus.append(it.state.name + "\n")
            }
        )

    }


}
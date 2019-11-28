package com.wi.worker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.wi.worker.ProgressWorker.Companion.ProgressKey
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val data = Data.Builder()
            .putString("data", "Live Data Updated")
            .build()

        val instance = WorkManager.getInstance(this)

        val workResult = OneTimeWorkRequest.Builder(ProgressWorker::class.java)
            .setInputData(data)
            .build()

        btnSend.setOnClickListener {
            instance.enqueue(workResult)
        }

        instance.getWorkInfoByIdLiveData(workResult.id)
                .observe(this, Observer {
              if(it !=null) {
                  val progress=it.progress.getInt(ProgressKey,0)
                  tvStatus.text= progress.toString()
                  progressbar.progress=progress
              }
            tvStatus.append(it.state.name + "\n")
        })

        btnSTop.setOnClickListener {
            instance.cancelWorkById(workResult.id)
        }
    }
}
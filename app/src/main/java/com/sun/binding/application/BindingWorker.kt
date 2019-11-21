package com.sun.binding.application

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class BindingWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val isOkay = this.inputData.getBoolean("key_accept_bg_work", false)
        return if (isOkay) {
            Thread.sleep(5000)

            val pullResult = startPull()
            val output = workDataOf("key_pulled_result" to pullResult)
            Result.success(output)
        } else {
            Result.failure()
        }
    }

    private fun startPull(): String {
        return "szx [worker] pull messages from backend date = ${System.currentTimeMillis()}"
    }
}
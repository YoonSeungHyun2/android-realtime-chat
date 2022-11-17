package com.Permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.Constants.AppConstants

class AppPermission {


    fun isStorageOk(context:Context): Boolean{
        return ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
    }

    fun requestStoragePermission(activity:Activity){
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            AppConstants.STORAGE_PERMISSION
        )
    }
}
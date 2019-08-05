package com.assignment.gorgeouslyfab.core.extension

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

/**
 * Created by danieh on 05/08/2019.
 */
fun Context.openAppSystemSettings() = startActivity(Intent().apply {
    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    data = Uri.fromParts("package", packageName, null)
})

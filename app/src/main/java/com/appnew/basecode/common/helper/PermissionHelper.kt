package com.appnew.basecode.common.helper

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionHelper {

    fun checkSinglePermission(
        context: Activity,
        permission: String,
        onHasPermission: () -> Unit = {},
        onNoPermission: (Boolean) -> Unit
    ) {
        when {
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PERMISSION_GRANTED -> {
                onHasPermission()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                context, permission
            ) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected, and what
                // features are disabled if it's declined. In this UI, include a
                // "cancel" or "no thanks" button that lets the user continue
                // using your app without granting the permission.
                onNoPermission(true)
            }

            else -> {
                // You can directly ask for the permission.
                onNoPermission(false)
            }
        }
    }

    fun checkSinglePermission(
        context: Activity,
        permission: String
    ): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PERMISSION_GRANTED
    }

    fun hasPermissions(context: Activity, permissions: List<String>): Boolean = permissions.all {
        ContextCompat.checkSelfPermission(
            context,
            it
        ) == PERMISSION_GRANTED
    }

    fun hasMicrophonePermission(context: Activity): Boolean =
        hasPermissions(context, listOf(Manifest.permission.RECORD_AUDIO))
}
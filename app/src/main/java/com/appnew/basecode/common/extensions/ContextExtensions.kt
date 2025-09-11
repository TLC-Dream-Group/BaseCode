@file:Suppress("UNUSED")

package com.appnew.basecode.common.extensions

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.EXTRA_SUBJECT
import android.content.Intent.EXTRA_TEXT
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.content.res.Configuration
import android.net.MailTo
import android.net.Uri
import android.os.Build
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.annotation.Px
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.net.toUri
import com.appnew.basecode.R
import com.appnew.basecode.utils.VersionUtils
import java.util.Locale

fun Context.isLandscape(): Boolean {
    return this.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}

fun Context.isPortrait(): Boolean {
    return this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
}

fun Context.isTablet(): Boolean {
    return this.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
}


inline fun <reified T : Activity> Context.intentFor(): Intent {
    return Intent(this, T::class.java)
}

fun Context.showToast(toast: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, toast, duration).show()
}

fun Context.showToast(@StringRes stringId: Int, duration: Int = Toast.LENGTH_SHORT) {
    showToast(getString(stringId), duration)
}

fun Context.copyToClipboard(text: String) {
    val clipboard: ClipboardManager = getSystemService() ?: return
    val clip = ClipData.newPlainText("label", text)
    clipboard.setPrimaryClip(clip)
}

fun Context.shareText(text: String) {
    val intent = Intent()
    intent.action = ACTION_SEND
    intent.type = "text/plain"

    intent.putExtra(
        EXTRA_TEXT,
        text
    )
    startActivity(
        Intent.createChooser(
            intent,
            getString(R.string.share)
        )
    )
}

fun Context.openStore() {
    val developerId = "0"
    val playStoreIntent = Intent(
        Intent.ACTION_VIEW,
        "market://dev?id=$developerId".toUri()
    ).setPackage("com.android.vending")

    val webIntent = Intent(
        Intent.ACTION_VIEW,
        "https://play.google.com/store/apps/dev?id=$developerId".toUri()
    )

    try {
        startActivity(playStoreIntent) // Try to open in Play Store app
    } catch (e: Exception) {
        startActivity(webIntent) // Fallback to web browser if Play Store app is not found
    }

}


fun Context.openBrowser(url: String) {
    val finalUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
        "http://$url"
    } else {
        url
    }

    val browserIntent = Intent(Intent.ACTION_VIEW, finalUrl.toUri())
    startActivity(browserIntent)
}

fun Context.goToCHPlay() {
    val appPackageName: String = packageName // getPackageName() from Context or Activity object
    var intent: Intent

    try {
        intent = Intent(
            Intent.ACTION_VIEW,
            "market://details?id=$appPackageName".toUri()
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        intent = Intent(
            Intent.ACTION_VIEW,
            "https://play.google.com/store/apps/details?id=$appPackageName".toUri()
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}


@Px
fun Context.statusBarHeightPx(): Int {
    val displayMetrics = resources.displayMetrics

    val mediumDpiStatusBarHeight = if (VersionUtils.hasMarshmallow()) {
        MEDIUM_DPI_STATUS_BAR_HEIGHT_M
    } else {
        MEDIUM_DPI_STATUS_BAR_HEIGHT
    }
    val statusBarHeightDp = when (displayMetrics.densityDpi) {
        DisplayMetrics.DENSITY_HIGH -> HIGH_DPI_STATUS_BAR_HEIGHT
        DisplayMetrics.DENSITY_MEDIUM -> mediumDpiStatusBarHeight
        DisplayMetrics.DENSITY_LOW -> LOW_DPI_STATUS_BAR_HEIGHT
        else -> MEDIUM_DPI_STATUS_BAR_HEIGHT
    }
    return (statusBarHeightDp * displayMetrics.density).toInt()
}


private const val LOW_DPI_STATUS_BAR_HEIGHT = 19

private const val MEDIUM_DPI_STATUS_BAR_HEIGHT = 25

private const val MEDIUM_DPI_STATUS_BAR_HEIGHT_M = 24

private const val HIGH_DPI_STATUS_BAR_HEIGHT = 38

fun Context.hasMediaAccessPermissions(): Boolean {
    if (
        VersionUtils.hasT() &&
        (
                ContextCompat.checkSelfPermission(
                    this,
                    READ_MEDIA_IMAGES
                ) == PERMISSION_GRANTED)
    ) {
        return true
    }
    if (
        VersionUtils.hasU() &&
        ContextCompat.checkSelfPermission(
            this,
            READ_MEDIA_VISUAL_USER_SELECTED
        ) == PERMISSION_GRANTED
    ) {
        return true
    }
    if (ContextCompat.checkSelfPermission(
            this,
            READ_EXTERNAL_STORAGE
        ) == PERMISSION_GRANTED
    ) {
        return true
    }
    return false

}

fun Context.deviceRatio(): Float {
    val displayMetrics = resources.displayMetrics
    val deviceWidth = displayMetrics.widthPixels
    val deviceHeight = displayMetrics.heightPixels
    return deviceWidth.toFloat() / deviceHeight.toFloat()
}


fun Context.getLocalizedString(resId: Int, locale: Locale = Locale.getDefault()): String {
    val config = Configuration(resources.configuration)

    if (VersionUtils.hasNougat()) {
        config.setLocale(locale)
    } else {
        @Suppress("DEPRECATION")
        config.locale = locale
    }

    val localizedContext = createConfigurationContext(config)
    return localizedContext.resources.getString(resId)
}

fun Context.sendFeedback(subject: String, pictures: ArrayList<Uri>) {
    try {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = MailTo.MAILTO_SCHEME.toUri()
        }

        val intent2 = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf("getString(com.lutech.ads.R.string.email_feedback)"))
            putExtra(EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_STREAM, pictures)
        }

        intent2.selector = intent
        startActivity(Intent.createChooser(intent2, "Send Feedback"))

    } catch (e: ActivityNotFoundException) {
        showToast("getString(com.lutech.ads.R.string.email_feedback)")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.createAndSendFeedback(message: String = "", images: List<Uri> = emptyList()) {

    val packageInfo = packageManager.getPackageInfo(packageName, 0)
    val version = packageInfo.versionName ?: "Unknown"
    val os = Build.VERSION.SDK_INT.toString()
    val device = "${Build.MANUFACTURER} ${Build.MODEL} ${Build.DEVICE}"

    val emailBody = """
            $message
            Version: $version
            OS: $os
            Device: $device

        """.trimIndent()

    val shareIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:".toUri()
        putExtra(
            EXTRA_SUBJECT, String.format(
                "getString(com.lutech.ads.R.string.txt_help_to_improve_us_email_subject)",
                getString(
                    R.string.app_name
                )
            )
        )

        putParcelableArrayListExtra(
            Intent.EXTRA_STREAM,
            ArrayList(images)
        ) // Add all image URIs
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    val contentEmail = String.format(
        "getString(com.lutech.ads.R.string.txt_help_to_improve_us_email_subject)",
        getString(
            R.string.app_name
        )
    )

    val emailIntent = Intent(Intent.ACTION_SEND_MULTIPLE)
    emailIntent.putExtra(
        Intent.EXTRA_EMAIL,
        arrayOf("getString(com.lutech.ads.R.string.email_feedback)")
    )
    emailIntent.putExtra(EXTRA_SUBJECT, contentEmail)
    emailIntent.putExtra(EXTRA_TEXT, emailBody)
    emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(images))
    emailIntent.selector = shareIntent
    try {
        startActivity(Intent.createChooser(emailIntent, "Send Feedback"))
    } catch (ex: ActivityNotFoundException) {
        showToast(getString(R.string.txt_no_mail_found), Toast.LENGTH_SHORT)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}



package com.talaloan.android.util

import android.content.Intent
import com.talaloan.android.BuildConfig

object IntentUtil {
    fun createInviteIntent(): Intent {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Invite your fiend on Tala to get amazing rewards: https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
            type = "text/plain"
        }
        return Intent.createChooser(sendIntent, "Share with:")
    }
}
package com.talaloan.android.ui

import android.content.Context
import android.text.Html
import com.talaloan.android.R
import com.talaloan.android.data.LoanInfo
import com.talaloan.android.data.Locale
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class CurrentLoan(private val context: Context, private val locale: Locale, private val loanInfo: LoanInfo) {
    fun getLoanStatusImageResId(): Int {
        return when (loanInfo.loan?.status) {
            "approved" -> {
                R.drawable.img_loan_status_approved
            }
            "paid" -> {
                R.drawable.img_loan_status_paidoff
            }
            else -> {
                R.drawable.img_loan_status_apply
            }
        }
    }

    fun getLoanTitleTextResId(): Int {
        return when (loanInfo.loan?.status) {
            "due" -> {
                R.string.loan_status_on_track
            }
            "approved" -> {
                R.string.loan_status_approved
            }
            "paid" -> {
                R.string.loan_status_paid
            }
            else -> {
                R.string.loan_status_new
            }
        }
    }

    fun getLoanMessageText(): String {
        return when (loanInfo.loan?.status) {
            "due" -> {
                String.format(context.getString(R.string.loan_status_due_message), locale.currency, locale.loanLimit)
            }
            "approved" -> {
                String.format(context.getString(R.string.loan_status_approved_message), locale.currency, loanInfo.loan.approved)
            }
            "paid" -> {
                context.getString(R.string.loan_status_paid_message)
            }
            else -> {
                String.format(context.getString(R.string.loan_status_new_message), locale.currency, locale.loanLimit)
            }
        }
    }

    fun getButtonTextResId(): Int {
        return when (loanInfo.loan?.status) {
            "due" -> {
                R.string.make_a_payment
            }
            "approved" -> {
                R.string.view_loan_offer
            }
            else -> {
                R.string.apply_now
            }
        }
    }

    fun getBadgeImageResId(): Int {
        return when (loanInfo.loan?.level) {
            "bronze" -> {
                R.drawable.img_bronze_badge_large
            }
            "silver" -> {
                R.drawable.img_silver_badge_large
            }
            "gold" -> {
                R.drawable.img_gold_badge_large
            }
            else -> {
                R.drawable.img_blue_badge_large
            }
        }
    }

    fun isDue() = loanInfo.loan?.status == "due"

    fun getDueDate(): String {
        if (loanInfo.loan?.dueDate == 0L) { return "" }

        val now = Calendar.getInstance()

        val due = Calendar.getInstance()
        due.timeInMillis = loanInfo.loan?.dueDate ?: 0

        if (now.get(Calendar.DAY_OF_MONTH) == due.get(Calendar.DAY_OF_MONTH) && now.get(Calendar.MONTH) == due.get(
                Calendar.MONTH) && now.get(Calendar.YEAR) == due.get(Calendar.YEAR)) {
            return "is due today"
        }

        now.add(Calendar.DAY_OF_MONTH, 1)
        if (now.get(Calendar.DAY_OF_MONTH) == due.get(Calendar.DAY_OF_MONTH) && now.get(Calendar.MONTH) == due.get(
                Calendar.MONTH) && now.get(Calendar.YEAR) == due.get(Calendar.YEAR)) {
            return "is due tomorrow"
        }

        now.add(Calendar.DAY_OF_MONTH, -1)
        return "is due ${SimpleDateFormat("MM/dd/yyyy").format(now.time)}"
    }

    fun getDueAmount() = "${locale.currency} ${loanInfo.loan?.due}"

    fun getProgressText() = if (loanInfo.loan == null) context.getString(R.string.tala_status_new) else String.format(context.getString(R.string.tala_status_next_limit), locale.currency, locale.loanLimit)

    fun getStoryCardImageResId(): Int {
        return when (loanInfo.locale) {
            "ke" -> {
                R.drawable.img_story_card_ke
            }
            "mx" -> {
                R.drawable.img_story_card_mx
            }
            "ph" -> {
                R.drawable.img_story_card_ph
            } else -> {
                throw Exception("Locale not supported!")
            }
        }
    }

    fun getStoryCardRightPaddingResId() = if (loanInfo.locale == "ke") R.dimen.large_margin else R.dimen.large_8x_margin

    override fun toString(): String {
        return "CurrentLoan(application=$context, locale=$locale, loanInfo=$loanInfo)"
    }
}
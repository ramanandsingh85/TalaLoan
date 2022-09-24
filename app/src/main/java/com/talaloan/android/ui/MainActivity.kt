package com.talaloan.android.ui

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.talaloan.android.databinding.ActivityMainBinding
import com.talaloan.android.util.IntentUtil
import com.talaloan.android.util.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(application)
            )[MainViewModel::class.java]

        initClickListeners()
        initObservers()

        viewModel.start()
        Log.d(TAG, "onCreate")
    }

    private fun initClickListeners() {
        binding.applyNowBtn.setOnClickListener { Toast.makeText(this, "Redirect to apply now screen", Toast.LENGTH_SHORT).show() }
        binding.viewStatusTv.setOnClickListener { Toast.makeText(this, "Redirect to status viewer screen", Toast.LENGTH_SHORT).show() }
        binding.inviteTv.setOnClickListener { startActivity(IntentUtil.createInviteIntent()) }
        binding.viewFaqsTv.setOnClickListener { Toast.makeText(this, "Redirect to FAQs screen", Toast.LENGTH_SHORT).show() }
        binding.makePaymentBtn.setOnClickListener { Toast.makeText(this, "Redirect to payment screen", Toast.LENGTH_SHORT).show() }
        binding.howToRepayTv.setOnClickListener { Toast.makeText(this, "Redirect to payment instructions popup", Toast.LENGTH_SHORT).show() }
        binding.nextBtn.setOnClickListener { viewModel.fetchNextLoanData() }
    }

    private fun initObservers() {
        viewModel.currentLoan.observe(this) {
            setLoanData(it)
            setLoanStatusData(it)
            setTalaStoryData(it)
            Log.d(TAG, "initObservers currentLoan=$it")
        }

        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            Log.d(TAG, "initObservers error=$it")
        }
    }

    private fun setLoanData(currentLoan: CurrentLoan) {
        if (currentLoan.isDue()) {
            binding.loanDueCv.visibility = View.VISIBLE
            binding.loanNoDueCv.visibility = View.GONE

            binding.dueAmountTv.text = currentLoan.getDueAmount()
            binding.dueDateTv.text = currentLoan.getDueDate()
        } else {
            binding.loanDueCv.visibility = View.GONE
            binding.loanNoDueCv.visibility = View.VISIBLE

            binding.loanStatusIv.setImageResource(currentLoan.getLoanStatusImageResId())
            binding.loanStatusTv.text = getString(currentLoan.getLoanTitleTextResId())
            binding.loanMessageTv.text = Html.fromHtml(currentLoan.getLoanMessageText(), 0)
            binding.applyNowBtn.text = getString(currentLoan.getButtonTextResId())
        }
    }

    private fun setLoanStatusData(currentLoan: CurrentLoan) {
        binding.progressLabelTv.text = currentLoan.getProgressText()
        binding.badgeStatusIv.setImageResource(currentLoan.getBadgeImageResId())
    }

    private fun setTalaStoryData(currentLoan: CurrentLoan) {
        binding.storyIv.setImageResource(currentLoan.getStoryCardImageResId())
        binding.storyTv.setPadding(binding.storyTv.paddingLeft, binding.storyTv.paddingTop, resources.getDimension(currentLoan.getStoryCardRightPaddingResId()).toInt(), 0)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
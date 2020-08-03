package com.example.tsaving.helpers

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.tsaving.R

class TransactionHistoryHelper {
    companion object {
        fun setImgSrc(desc: String, img: ImageView) {
            when (desc) {
                "DEPOSIT_TO_MAIN_ACCOUNT" -> {
                    img.setImageResource(R.mipmap.ic_transaction_history_incoming_round)
                }

                "MAIN_TO_VA" -> {
                    img.setImageResource(R.mipmap.ic_transaction_history_outgoing_round)
                }

                "VA_TO_MAIN" -> {
                    img.setImageResource(R.mipmap.ic_transaction_history_incoming_round)
                }
            }
        }

        fun formatTransactionAmount(desc: String, tranAmtTextView: TextView) {
            /* Placeholder text is used to prevent lint.
                the IDE does not allow concatenating string on TextView's setText
            */
            val placeholderString: String
            when (desc) {
                "DEPOSIT_TO_MAIN_ACCOUNT" -> {
                    placeholderString = "+${tranAmtTextView.text}"
                    tranAmtTextView.text = placeholderString
                    tranAmtTextView.setTextColor(
                        ContextCompat.getColor(
                            tranAmtTextView.context,
                            R.color.colorDarkGreen
                        )
                    )
                }

                "MAIN_TO_VA" -> {
                    placeholderString = "-${tranAmtTextView.text}"
                    tranAmtTextView.text = placeholderString
                    tranAmtTextView.setTextColor(
                        ContextCompat.getColor(
                            tranAmtTextView.context,
                            R.color.colorDeepRed
                        )
                    )
                }

                "VA_TO_MAIN" -> {
                    placeholderString = "+${tranAmtTextView.text}"
                    tranAmtTextView.text = placeholderString
                    tranAmtTextView.setTextColor(
                        ContextCompat.getColor(
                            tranAmtTextView.context,
                            R.color.colorDarkGreen
                        )
                    )
                }
            }
        }
    }
}
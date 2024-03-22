package com.beeeam.quiz

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.beeeam.quiz.dialog.BackDialog
import com.beeeam.util.DialogType
import com.beeeam.util.QuizNavType
import com.beeeam.util.QuizType
import java.util.Timer

fun Fragment.setQuizNavigation(quizType: String, currentPosition: QuizNavType) {
    val navIdWithMulti: Int
    val navIdWithOX: Int

    when (currentPosition) {
        QuizNavType.MULTI -> {
            navIdWithMulti = R.id.action_quizDailyMultiChoiceFragment_self
            navIdWithOX = R.id.action_quizDailyMultiChoiceFragment_to_quizDailyOXFragment
        }

        QuizNavType.OX -> {
            navIdWithMulti = R.id.action_quizDailyOXFragment_to_quizDailyMultiChoiceFragment
            navIdWithOX = R.id.action_quizDailyOXFragment_self
        }

        QuizNavType.MAIN -> {
            navIdWithMulti = R.id.action_quizMainFragment_to_quizDailyMultiChoiceFragment
            navIdWithOX = R.id.action_quizMainFragment_to_quizDailyOXFragment
        }
    }

    when (quizType) {
        QuizType.MULTI.type -> findNavController().navigate(navIdWithMulti)
        QuizType.OX.type -> findNavController().navigate(navIdWithOX)
    }
}

fun ImageView.setReviewMode(
    type: DialogType,
    pages: TextView,
    dialog: BackDialog,
    resId: Int,
    progressBar: Timer,
) {
    when (type) {
        DialogType.REVIEW -> {
            pages.isVisible = true
            this.setOnClickListener {
                dialog.showDialog()
                dialog.alertDialog.setOnCancelListener {
                    progressBar.cancel()
                    findNavController().navigate(resId)
                }
            }
        }

        else -> {
            pages.isVisible = false
            this.setOnClickListener {
                progressBar.cancel()
                findNavController().popBackStack()
            }
        }
    }
}
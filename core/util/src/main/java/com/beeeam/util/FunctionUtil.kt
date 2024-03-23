package com.beeeam.util

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.text.style.TextAppearanceSpan
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beeeam.util.Const.backKeyPressTime
import com.shypolarbear.domain.model.HttpError
import org.json.JSONObject
import timber.log.Timber
import java.io.File
import java.util.Timer
import java.util.TimerTask
import kotlin.math.ceil

fun Uri.convertUriToPath(context: Context): String {
    val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(this, proj, null, null, null)
    val index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor?.moveToFirst()
    val result = cursor?.getString(index!!)
    cursor?.close()
    return result!!
}

fun Uri.convertUriToFile(context: Context): File {
    val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(this, proj, null, null, null)
    val index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor?.moveToFirst()
    val path = cursor?.getString(index!!)
    cursor?.close()

    return File(path!!)
}

fun simpleHttpErrorCheck(error: Throwable) {
    if (error is HttpError) {
        val errorBodyData = JSONObject(error.errorBody)
        Timber.tag("ERROR").d("${errorBodyData.get("code")}")
    }
}

fun ProgressBar.initProgressBar(detailText: TextView, submitIncorrect: () -> Unit): Timer {
    var totalProgress = 15000
    val timer = Timer()

    detailText.text = context.getString(com.beeeam.designsystem.R.string.quiz_daily_time, totalProgress / 100)

    timer.scheduleAtFixedRate(
        object : TimerTask() {
            override fun run() {
                if (totalProgress > 0) {
                    totalProgress -= 1
                    progress = totalProgress / 10

                    detailText.post {
                        detailText.text = context.getString(
                            com.beeeam.designsystem.R.string.quiz_daily_time,
                            ceil(totalProgress.toDouble() / 1000).toInt(),
                        )
                    }
                } else {
                    timer.cancel()
                    submitIncorrect()
                }
            }
        },
        0,
        1L,
    )

    return timer
}

fun setVisibilityInvert(vararg views: View) {
    for (view in views) {
        view.visibility =
            if (view.isVisible) {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
    }
}

fun TextView.detectActivation(vararg choices: TextView) {
    for (choice in choices) {
        if (choice.isActivated) {
            choice.isActivated = choice.isActivated.not()
        }
    }
    this.isActivated = this.isActivated.not()
}

fun Button.showLikeBtnIsLike(isLike: Boolean, view: Button) {
    val likeBtnOn = ContextCompat.getDrawable(context, com.beeeam.designsystem.R.drawable.ic_btn_like_on)
    val likeBtnOff = ContextCompat.getDrawable(context, com.beeeam.designsystem.R.drawable.ic_btn_like_off)

    if (isLike) {
        view.background = likeBtnOn
    } else {
        view.background = likeBtnOff
    }
}

fun View.selectedComment(isSelected: Boolean, view: View) {
    val background =
        if (isSelected) ContextCompat.getColor(context, com.beeeam.designsystem.R.color.Blue_05)
        else ContextCompat.getColor(context, com.beeeam.designsystem.R.color.White_01)

    view.setBackgroundColor(background)
}

fun TextView.setSpecificTextColor(
    text: String,
    targetText: String,
    styleId: Int? = null,
    colorId: Int? = null,
) {
    val spanningText = SpannableString(text)
    val startIndex = text.indexOf(targetText)
    val endIndex = startIndex + targetText.length

    if (styleId != null) {
        spanningText.setSpan(
            TextAppearanceSpan(context, styleId),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
        )
    }
    if (colorId != null) {
        spanningText.setSpan(
            ForegroundColorSpan(resources.getColor(colorId, context?.theme)),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
        )
    }

    this.text = spanningText
}

fun EditText.setColorStateWithInput(state: InputState, textView: TextView, imageView: ImageView) {
    when (state) {
        InputState.ACCEPT -> {
            this.background = ResourcesCompat.getDrawable(
                resources,
                com.beeeam.designsystem.R.drawable.background_signup_et_accept,
                context.theme,
            )
            textView.setTextColorById(context, com.beeeam.designsystem.R.color.Success_01)
            imageView.apply {
                setImageResource(com.beeeam.designsystem.R.drawable.ic_signup_success)
                visibility = View.VISIBLE
            }
        }

        InputState.ERROR -> {
            this.background = ResourcesCompat.getDrawable(
                resources,
                com.beeeam.designsystem.R.drawable.background_signup_et_error,
                context.theme,
            )
            textView.setTextColorById(context, com.beeeam.designsystem.R.color.Error_01)
            imageView.apply {
                setImageResource(com.beeeam.designsystem.R.drawable.ic_signup_error)
                visibility = View.VISIBLE
            }
        }

        InputState.ON -> {
            this.background = ResourcesCompat.getDrawable(
                resources,
                com.beeeam.designsystem.R.drawable.background_signup_et_on,
                context.theme,
            )
            textView.setTextColorById(context, com.beeeam.designsystem.R.color.Blue_02)
            imageView.visibility = View.GONE
        }

        InputState.OFF -> {
            textView.setTextColorById(context, com.beeeam.designsystem.R.color.Gray_02)
            this.background = ResourcesCompat.getDrawable(
                resources,
                com.beeeam.designsystem.R.drawable.background_signup_et_off,
                context.theme,
            )
            imageView.visibility = View.GONE
        }
    }
}

fun EditText.afterTextChanged(method: (editable: Editable?) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            method(p0)
        }
    })
}

fun EditText.keyboardDown(fragment: Fragment) {
    this.setOnEditorActionListener { v, _, event ->
        if (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
            fragment.hideKeyboard()
            return@setOnEditorActionListener true
        }
        return@setOnEditorActionListener false
    }
}

fun TextView.setTextColorById(context: Context, colorId: Int) {
    this.setTextColor(
        ContextCompat.getColor(
            context,
            colorId,
        ),
    )
}

fun Fragment.hideKeyboard() {
    if (activity != null && requireActivity().currentFocus != null) {
        val inputManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            requireActivity().currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS,
        )
    }
}

fun RecyclerView.infiniteScroll(method: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val lastVisiblePosition = (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
            val isBottom = lastVisiblePosition + 1 == adapter?.itemCount
            val isDownScroll = dy > 0

            if (isBottom and isDownScroll) { method() }
        }
    })
}

fun onBackPressedToFinish(context: Context, activity: Activity) {
    if (System.currentTimeMillis() > (backKeyPressTime + 2500)) {
        backKeyPressTime = System.currentTimeMillis()
        Toast.makeText(context, context.getString(com.beeeam.designsystem.R.string.msg_on_back_pressed_end), Toast.LENGTH_SHORT).show()
        return
    } else if (System.currentTimeMillis() <= (backKeyPressTime + 2500)) {
        activity.finish()
    }
}

fun updateButtonState(context: Context, button: Button, isAccept: Boolean) {
    if (isAccept) {
        button.background = AppCompatResources.getDrawable(
            context,
            com.beeeam.designsystem.R.drawable.background_solid_blue_01_radius_15,
        )
        button.setTextColor(context.getColor(com.beeeam.designsystem.R.color.White_01))
    } else {
        button.background = AppCompatResources.getDrawable(
            context,
            com.beeeam.designsystem.R.drawable.background_solid_gray_06_radius_15,
        )
        button.setTextColor(context.getColor(com.beeeam.designsystem.R.color.Gray_03))
    }
}

fun createNavDeepLinkRequest(uri: String): NavDeepLinkRequest {
    return NavDeepLinkRequest.Builder
        .fromUri(uri.toUri())
        .build()
}

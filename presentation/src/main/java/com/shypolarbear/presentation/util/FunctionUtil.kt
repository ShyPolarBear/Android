package com.shypolarbear.presentation.util

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
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.ui.feed.feedTotal.FeedTotalFragment
import com.shypolarbear.presentation.ui.quiz.daily.dialog.BackDialog
import com.skydoves.powermenu.PowerMenuItem
import timber.log.Timber
import org.json.JSONObject
import java.io.File
import java.util.Timer
import java.util.TimerTask
import kotlin.math.ceil


val emailPattern = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
val phonePattern = Regex("[^0-9]")

const val SIGNUP_NEED = 1006
const val LOGIN_SUCCESS = 0
const val LOGIN_FAIL = 1007

enum class InputState(val state: Int) {
    ACCEPT(0),
    ERROR(1),
    ON(2),
    OFF(3)
}

enum class DialogType(val point: String) {
    REVIEW("REVIEW"),
    DEFAULT("DEFAULT")
}

enum class QuizType(val type: String) {
    MULTI("MULTIPLE_CHOICE"),
    OX("OX")
}

enum class QuizNavType() {
    MULTI,
    OX,
    MAIN
}

enum class ImageType(val type: String) {
    PROFILE("profile"),
    FEED("feed")
}

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

fun ProgressBar.initProgressBar(detailText: TextView, submitIncorrect: () -> Unit): Timer {
    var totalProgress = 15000
    val timer = Timer()

    detailText.text = context.getString(R.string.quiz_daily_time, totalProgress / 100)

    timer.scheduleAtFixedRate(object : TimerTask() {
        override fun run() {
            if (totalProgress > 0) {
                totalProgress -= 1
                progress = totalProgress / 10

                detailText.post {
                    detailText.text = context.getString(
                        R.string.quiz_daily_time,
                        ceil(totalProgress.toDouble() / 1000).toInt()
                    )
                }
            } else {
                timer.cancel()
                submitIncorrect()
            }
        }
    }, 0, 1L)

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

fun Button.showLikeBtnIsLike(isLike: Boolean, view: Button) {

    val likeBtnOn = ContextCompat.getDrawable(context, R.drawable.ic_btn_like_on)
    val likeBtnOff = ContextCompat.getDrawable(context, R.drawable.ic_btn_like_off)

    if (isLike) {
        view.background = likeBtnOn
    } else {
        view.background = likeBtnOff
    }
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
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    if (colorId != null) {
        spanningText.setSpan(
            ForegroundColorSpan(resources.getColor(colorId, context?.theme)),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    this.text = spanningText
}

fun EditText.setColorStateWithInput(state: InputState, textView: TextView, imageView: ImageView) {
    when (state) {
        InputState.ACCEPT -> {
            this.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.background_signup_et_accept,
                context.theme
            )
            textView.setTextColorById(context, R.color.Success_01)
            imageView.apply {
                setImageResource(R.drawable.ic_signup_success)
                visibility = View.VISIBLE
            }
        }

        InputState.ERROR -> {
            this.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.background_signup_et_error,
                context.theme
            )
            textView.setTextColorById(context, R.color.Error_01)
            imageView.apply {
                setImageResource(R.drawable.ic_signup_error)
                visibility = View.VISIBLE
            }
        }

        InputState.ON -> {
            this.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.background_signup_et_on,
                context.theme
            )
            textView.setTextColorById(context, R.color.Blue_02)
            imageView.visibility = View.GONE
        }

        InputState.OFF -> {
            textView.setTextColorById(context, R.color.Gray_02)
            this.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.background_signup_et_off,
                context.theme
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

// TODO("클릭 동작 수행하기 위해서는 공통으로 사용할 수 없을 듯 API 연동하면서 제거 해야 할 듯;")
fun ImageView.setMenu(
    view: ImageView,
    menuList: List<PowerMenuItem>,
    viewLifecycleOwner: LifecycleOwner,
) {
    PowerMenuUtil.getPowerMenu(
        context,
        viewLifecycleOwner,
        menuList
    ) { _, _ -> }
        .showAsDropDown(
            view,
            FeedTotalFragment.POWER_MENU_OFFSET_X,
            FeedTotalFragment.POWER_MENU_OFFSET_Y
        )
}

fun TextView.setTextColorById(context: Context, colorId: Int) {
    this.setTextColor(
        ContextCompat.getColor(
            context,
            colorId
        )
    )
}

fun Fragment.hideKeyboard() {
    if (activity != null && requireActivity().currentFocus != null) {
        val inputManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            requireActivity().currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
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
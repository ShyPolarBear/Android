package com.shypolarbear.presentation.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.shypolarbear.presentation.R

abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel>(@LayoutRes private val layoutResID: Int) :
    AppCompatActivity() {
    companion object{
        const val DOUBLE_BACK_BUTTON_DELAY = 1500L;
    }
    // 로그용 태그
    private val TAG = this.javaClass.simpleName
    private var doubleClicked: Boolean = false
    // 데이터 바인딩, 뷰모델 연결
    lateinit var binding: DB
    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //뒤로가기 누르면 활성화되는 콜백
        this.onBackPressedDispatcher.addCallback(this, callback)
        // 초기화된 layoutResId로 databinding 객체 생성
        binding = DataBindingUtil.setContentView(this, layoutResID)
        // 액티비티에 lifecycleOwner할당
        binding.lifecycleOwner = this@BaseActivity
        initView()
    }

    abstract fun initView()
    // 뒤로가기 콜백 인스턴스 생성
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (doubleClicked){
                finish()
            }
            doubleClicked = true
            Toast.makeText(this@BaseActivity, getString(R.string.back_pressed_double),Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed({
                doubleClicked = false
            }, DOUBLE_BACK_BUTTON_DELAY)
        }
    }
}
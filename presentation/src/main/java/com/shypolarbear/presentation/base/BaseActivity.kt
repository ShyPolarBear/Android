package com.shypolarbear.presentation.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel>(@LayoutRes private val layoutResID: Int) :
    AppCompatActivity() {
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
            // 뒤로 버튼 이벤트 처리
            Log.e(TAG, "뒤로가기")
            if (doubleClicked){
                finish()
            }
            doubleClicked = true
            Toast.makeText(this@BaseActivity, "한 번 더 누르면 종료됩니다",Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed({
                doubleClicked = false
            }, 1500)
        }
    }
}
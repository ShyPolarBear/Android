package com.beeeam.util

object Const {
    var backKeyPressTime: Long = 0

    val emailPattern = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
    val phonePattern = Regex("[^0-9]")
    val NAME_RANGE = 2..8
    val TITLE_RANGE = 1..20
    val CONTENT_RANGE = 1..1000

    const val MAX_PAGES = 5
    const val UPLOADING = 0
    const val UPLOADED = 1
    const val IMAGE_ADD_INDEX = 0
    const val IMAGE_MAX_COUNT = 5
    const val IMAGE_ADD_MAX = 6
    const val NICKNAME_DUPLICATE_CHECK_TIME = 500
    const val PHONE_NUMBER_DASH_INCLUDE = 13
    const val POWER_MENU_OFFSET_X = -290
    const val POWER_MENU_OFFSET_Y = 0
    const val UNRANKED = -1
    const val MAX_IMAGE_WIDTH = 1440
    const val MAX_IMAGE_HEIGHT = 1440
    const val DEFAULT_COMMENT_ID = 0
    const val SIGNUP_NEED = 1006
    const val LOGIN_SUCCESS = 0
    const val LOGIN_FAIL = 1007
}

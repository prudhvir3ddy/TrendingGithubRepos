package com.prudhvir3ddy.trendinggithubrepos

import android.content.SharedPreferences
import com.prudhvir3ddy.trendinggithubrepos.utils.AppConstants

class SharedPrefs(
  private val sharedPreferences: SharedPreferences
) {

  lateinit var editor: SharedPreferences.Editor

  fun setSort(type: String) {
    editor = sharedPreferences.edit()
    editor.putString(AppConstants.SORT_PREF, type)
    editor.commit()
  }

  fun getSort(): String {
    return sharedPreferences.getString(AppConstants.SORT_PREF, null) ?: "None"
  }
}


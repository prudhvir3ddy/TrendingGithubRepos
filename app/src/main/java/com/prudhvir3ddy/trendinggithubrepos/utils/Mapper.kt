package com.prudhvir3ddy.trendinggithubrepos.utils

import com.prudhvir3ddy.trendinggithubrepos.database.UIResponse
import com.prudhvir3ddy.trendinggithubrepos.model.NetworkResponse

fun NetworkResponse.toUIResponse(): UIResponse {
  return UIResponse(
    url = url,
    name = name,
    avatar = avatar,
    author = author,
    description = description,
    language = language,
    languageColor = languageColor,
    stars = stars,
    forks = forks,
    currentPeriodStars = currentPeriodStars
  )

}
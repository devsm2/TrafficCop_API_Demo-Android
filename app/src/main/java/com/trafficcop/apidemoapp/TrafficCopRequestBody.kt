package com.trafficcop.apidemoapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrafficCopRequestBody(
    val appId: String,
    val navigatorLanguage: String,
    val timezoneOffset: Long
): Parcelable

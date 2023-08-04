package com.trafficcop.apidemoapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrafficCopResponse(
    val ivtScore: Double
): Parcelable
package com.otis.homepractical.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pro(
    val entityId: String,
    val companyName: String,
    val ratingCount: String,
    val compositeRating: String,
    val companyOverview: String,
    val canadianSP: Boolean,
    val spanishSpeaking: Boolean,
    val phoneNumber: String,
    val latitude: Double,
    val longitude: Double,
    val servicesOffered: String?,
    val specialty: String,
    val primaryLocation: String,
    val email: String
) : Parcelable {
    companion object {
        val TAG = "ProData"
    }
}
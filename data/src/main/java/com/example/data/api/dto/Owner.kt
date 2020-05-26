package com.example.data.api.dto

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("reputation") val reputation: Int,
    @SerializedName("user_id") val userId: Long,
    @SerializedName("profile_image") val profileImage: String? = null,
    @SerializedName("display_name") val displayName: String
)
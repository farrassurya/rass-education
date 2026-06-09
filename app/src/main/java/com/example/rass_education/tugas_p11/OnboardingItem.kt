package com.example.rass_education.tugas_p11

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnboardingItem(
    @param:StringRes val titleResId: Int,
    @param:StringRes val descriptionResId: Int,
    @param:DrawableRes val iconResId: Int
)



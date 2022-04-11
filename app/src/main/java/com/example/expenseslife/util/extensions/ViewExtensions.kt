package com.example.expenseslife.util.extensions

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import com.example.expenseslife.R

fun View.startZoomAnimation500(context: Context) {
    this.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_anim_500))
}

fun View.startZoomAnimation300(context: Context) {
    this.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_anim_300))
}

fun View.startLeftAppearanceAnimation(context: Context) {
    this.startAnimation(AnimationUtils.loadAnimation(context, R.anim.left_appearance_anim_500))
}

fun View.startRightAppearanceAnimation(context: Context) {
    this.startAnimation(AnimationUtils.loadAnimation(context, R.anim.right_appearance_anim_500))
}

fun View.startRotateAnimation(context: Context) {
    this.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotation_360))
}
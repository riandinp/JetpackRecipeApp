package com.dicoding.recipeapp.ui.components

import android.content.Context
import android.widget.Toast

fun showToast(
    context: Context,
    message: String,
    duration: Int = Toast.LENGTH_SHORT,
    currentToast: Toast? = null
): Toast {
    currentToast?.cancel()
    return Toast.makeText(context, message, duration)
}
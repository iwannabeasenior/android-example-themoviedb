package com.example.movie.extention

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import com.example.movie.screen.auth.LoginUiState
import com.example.movie.utils.LinkUtil
import androidx.core.net.toUri


fun Context.openWebViewWithUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    this.startActivity(intent)
}
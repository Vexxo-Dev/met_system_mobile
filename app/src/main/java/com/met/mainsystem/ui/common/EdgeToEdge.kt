package com.met.mainsystem.ui.common

import android.app.Activity
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

/**
 * Lets the screen draw behind the status bar while keeping [header] content below it,
 * and pads [root] for the navigation bar and keyboard.
 */
fun Activity.applyEdgeToEdge(root: View, header: View, lightStatusBar: Boolean) {
    WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = lightStatusBar
    val headerTop = header.paddingTop
    ViewCompat.setOnApplyWindowInsetsListener(root) { _, insets ->
        val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())
        header.updatePadding(top = headerTop + bars.top)
        root.updatePadding(left = bars.left, right = bars.right, bottom = bars.bottom)
        insets
    }
}

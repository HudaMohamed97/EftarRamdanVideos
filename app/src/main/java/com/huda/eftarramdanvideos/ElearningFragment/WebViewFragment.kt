package com.huda.eftarramdanvideos.ElearningFragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.huda.eftarramdanvideos.R


class WebViewFragment : Fragment() {
    private lateinit var root: View


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.webview, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        val url = arguments?.getString("url")!!
        val browser = root.findViewById<WebView>(R.id.webview)
        browser.setInitialScale(1)
        browser.settings.javaScriptEnabled = true
        browser.settings.displayZoomControls = true
        browser.settings.loadWithOverviewMode = true
         browser.settings.useWideViewPort = true
        browser.isVerticalScrollBarEnabled = true
        browser.isHorizontalScrollBarEnabled = true
        browser.scrollBarStyle = WebView.SCROLLBARS_INSIDE_OVERLAY
        /*  browser.settings.builtInZoomControls = true
          browser.isScrollbarFadingEnabled = false*/
        browser.loadUrl(url)
    }


}
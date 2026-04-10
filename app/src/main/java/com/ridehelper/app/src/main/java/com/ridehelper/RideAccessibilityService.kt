package com.ridehelper

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.util.Log

class RideAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        val packageName = event.packageName?.toString() ?: return

        if (!packageName.contains("uber") && !packageName.contains("99")) return

        val root = rootInActiveWindow ?: return
        val texts = extractText(root)

        val data = RideParser.parse(texts)

        Log.d("RIDE_DATA", data.toString())

        WebAppBridge.sendData(data)
    }

    override fun onInterrupt() {}

    private fun extractText(node: AccessibilityNodeInfo): List<String> {
        val list = mutableListOf<String>()

        fun traverse(n: AccessibilityNodeInfo?) {
            if (n == null) return

            n.text?.toString()?.let { list.add(it) }

            for (i in 0 until n.childCount) {
                traverse(n.getChild(i))
            }
        }

        traverse(node)
        return list
    }
}

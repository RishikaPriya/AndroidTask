package task.avantari.com.hacked.service

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.os.Bundle

class ReadEditTextService : AccessibilityService() {
    override fun onInterrupt() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAccessibilityEvent(accessibilityEvent: AccessibilityEvent) {
        val source = accessibilityEvent.source

        if ((source != null) and (accessibilityEvent.className == "android.widget.EditText")) {
            val arguments = Bundle()

            if (source!!.text.toString().toLowerCase().contains("android")) {
                arguments.putCharSequence(AccessibilityNodeInfo
                        .ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                        source.text.toString().replace("(?i)android".toRegex(), "Hacked"))
                source.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
            }
        }
    }


}

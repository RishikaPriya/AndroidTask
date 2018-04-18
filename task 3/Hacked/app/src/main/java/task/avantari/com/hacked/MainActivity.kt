package task.avantari.com.hacked

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context
import android.content.ComponentName
import android.text.TextUtils
import task.avantari.com.hacked.service.ReadEditTextService


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!isAccessibilityServiceEnabled(this.applicationContext, ReadEditTextService::class.java)){
            startActivityForResult(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 0)
        }

        your_text.setOnClickListener({
            your_text.hint = ""
        })

    }

    private fun isAccessibilityServiceEnabled(context: Context, accessibilityService: Class<ReadEditTextService>): Boolean {
        val expectedComponentName = ComponentName(context, accessibilityService)

        val enabledServicesSetting = Settings.Secure
                .getString(context.contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES) ?: return false

        val colonSplitter = TextUtils.SimpleStringSplitter(':')
        colonSplitter.setString(enabledServicesSetting)

        while (colonSplitter.hasNext()) {
            val componentNameString = colonSplitter.next()
            val enabledService = ComponentName.unflattenFromString(componentNameString)

            if (enabledService != null && enabledService == expectedComponentName)
                return true
        }

        return false
    }

}

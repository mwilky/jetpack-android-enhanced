package com.mwilky.androidenhanced.xposed

import android.content.Context
import android.graphics.Color
import com.mwilky.androidenhanced.BroadcastUtils
import com.mwilky.androidenhanced.Utils
import java.lang.ref.WeakReference
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers.findAndHookMethod

class SystemUIApplication {

    companion object {
        // Hook Classes
        private const val SYSTEM_UI_APPLICATION_CLASS =
            "com.android.systemui.SystemUIApplication"

        // Use a weak reference to store the SystemUIApplication Context
        private var SystemUIApplicationContextRef: WeakReference<Context>? = null

        fun init(classLoader: ClassLoader?) {
            findAndHookMethod(
                SYSTEM_UI_APPLICATION_CLASS,
                classLoader,
                "onCreate",
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        // Access the 'this' context of the hooked class
                        val hookedContext = param.thisObject as? Context

                        // If the 'this' context is not null, set it as the applicationContext
                        hookedContext?.let {
                            SystemUIApplicationContextRef = WeakReference(it.applicationContext)
                        }

                        if (hookedContext != null) {
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.qsTileVibration,
                                param.thisObject.toString(),
                                false
                            )


                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.hideQsFooterBuildNumber,
                                param.thisObject.toString(),
                                false
                            )

                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.smartPulldown,
                                param.thisObject.toString(),
                                0
                            )

                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.quickPulldown,
                                param.thisObject.toString(),
                                0
                            )

                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.hideLockscreenStatusBar,
                                param.thisObject.toString(),
                                false
                            )

                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.scrambleKeypad,
                                param.thisObject.toString(),
                                false
                            )

                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.disableLockscreenPowerMenu,
                                param.thisObject.toString(),
                                false
                            )

                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.disableQsLockscreen,
                                param.thisObject.toString(),
                                false
                            )

                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.doubleTapToSleep,
                                param.thisObject.toString(),
                                false
                            )

                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.statusBarClockPosition,
                                param.thisObject.toString(),
                                0
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.statusBarBrightnessControl,
                                param.thisObject.toString(),
                                false
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.statusBarClockSeconds,
                                param.thisObject.toString(),
                                false
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.expandAllNotifications,
                                param.thisObject.toString(),
                                false
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.qsStyle,
                                param.thisObject.toString(),
                                0
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.qsColumns,
                                param.thisObject.toString(),
                                2
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.qsColumnsLandscape,
                                param.thisObject.toString(),
                                4
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.qqsColumns,
                                param.thisObject.toString(),
                                2
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.qqsColumnsLandscape,
                                param.thisObject.toString(),
                                4
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.qqsRows,
                                param.thisObject.toString(),
                                2
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.qsRows,
                                param.thisObject.toString(),
                                4
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.qsBrightnessSliderPosition,
                                param.thisObject.toString(),
                                0
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.qqsBrightnessSlider,
                                param.thisObject.toString(),
                                false
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.customStatusbarIconColors,
                                param.thisObject.toString(),
                                false
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.customStatusbarClockColor,
                                param.thisObject.toString(),
                                Color.WHITE
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.customStatusbarBatteryIconColor,
                                param.thisObject.toString(),
                                Color.WHITE
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.customStatusbarBatteryPercentColor,
                                param.thisObject.toString(),
                                Color.WHITE
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.customStatusbarWifiIconColor,
                                param.thisObject.toString(),
                                Color.WHITE
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.customStatusbarMobileIconColor,
                                param.thisObject.toString(),
                                Color.WHITE
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.customStatusbarNotificationIconColor,
                                param.thisObject.toString(),
                                Color.WHITE
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.customStatusbarOtherIconColor,
                                param.thisObject.toString(),
                                Color.WHITE
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.customStatusbarDndIconColor,
                                param.thisObject.toString(),
                                Color.WHITE
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.customStatusbarAirplaneIconColor,
                                param.thisObject.toString(),
                                Color.WHITE
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.customStatusbarHotspotIconColor,
                                param.thisObject.toString(),
                                Color.WHITE
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.customStatusbarBluetoothIconColor,
                                param.thisObject.toString(),
                                Color.WHITE
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.customStatusbarGlobalIconColor,
                                param.thisObject.toString(),
                                Color.WHITE
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.hideCollapsedAlarmIcon,
                                param.thisObject.toString(),
                                true
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.hideCollapsedVolumeIcon,
                                param.thisObject.toString(),
                                true
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.hideCollapsedCallStrengthIcon,
                                param.thisObject.toString(),
                                true
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.hideCollapsedWifiIcon,
                                param.thisObject.toString(),
                                false
                            )
                            BroadcastUtils.registerBroadcastReceiver(
                                hookedContext, Utils.iconBlacklist,
                                param.thisObject.toString(),
                                "false"
                            )
                        }
                    }
                }
            )
        }

        // Call this to get SystemUIApplicationContext
        fun getApplicationContext(): Context? {
            return SystemUIApplicationContextRef?.get()
        }
    }
}

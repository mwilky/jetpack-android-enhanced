package com.mwilky.androidenhanced

import android.content.Context
import android.graphics.Color
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Handler
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import com.mwilky.androidenhanced.MainActivity.Companion.TAG
import com.mwilky.androidenhanced.xposed.Quicksettings.Companion.QSTileHost
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.XposedHelpers.callMethod
import de.robv.android.xposed.XposedHelpers.getObjectField

class Utils(context: Context, handler: Handler) {

    companion object {
        //Torch enabled
        var isTorchEnabled: Boolean = false
        //App Settings
        const val ISDEVICESUPPORTEDKEY = "isDeviceSupported"
        const val ISONBOARDINGCOMPLETEDKEY = "isOnboardingComplete"
        const val LASTBACKUP = "lastBackupDate"
        //Tweak Values
        //Framework
        const val torchPowerScreenOff = "bool_LongPressPowerTorchScreenOff"
        const val torchAutoOffScreenOn = "bool_TorchAutoOffScreenOn"
        const val volKeyMediaControl = "bool_VolKeyMediaControl"
        const val allowAllRotations = "bool_AllowAllRotations"
        const val disableSecureScreenshots = "bool_DisableSecureScreenshots"
        //SystemUI
        const val doubleTapToSleep = "bool_DoubleTapToSleep"
        const val statusBarBrightnessControl = "bool_StatusbarBrightnessControl"
        const val statusBarClockPosition = "int_StatusbarClockPosition"
        const val statusBarClockSeconds = "bool_StatusbarClockSeconds"
        const val hideLockscreenStatusBar = "bool_HideLockscreenStatusbar"
        const val scrambleKeypad = "bool_ScrambleKeypad"
        const val disableLockscreenPowerMenu = "bool_DisableLockscreenPowerMenu"
        const val qsTileVibration = "bool_QsTileVibration"
        const val disableQsLockscreen = "bool_DisableQsLockscreen"
        const val hideQsFooterBuildNumber = "bool_HideQsFooterBuildNumber"
        const val smartPulldown = "int_SmartPulldown"
        const val quickPulldown = "int_QuickPulldown"
        const val muteScreenOnNotifications = "bool_MuteScreenOnNotifications"
        const val expandAllNotifications = "bool_ExpandAllNotifications"
        const val qsStyle = "int_QsStyle"
        const val qqsRows = "int_QQsRows"
        const val qsRows = "int_QsRows"
        const val qsColumns = "int_QSColumns"
        const val qsBrightnessSliderPosition = "int_QSBrightnessSliderPosition"
        const val qqsBrightnessSlider = "boolean_QQSBrightnesSlider"

        var mReloadTiles: Boolean = false

        lateinit var mVibrator: Vibrator

        fun reloadTiles() {
            mReloadTiles = true

            val value = callMethod(
                getObjectField(
                    QSTileHost,
                    "mTunerService"
                ), "getValue", "sysui_qs_tiles"
            ) as String
            callMethod(
                QSTileHost,
                "onTuningChanged",
                "sysui_qs_tiles",
                "flashlight"
            )
            callMethod(
                QSTileHost,
                "onTuningChanged",
                "sysui_qs_tiles",
                value
            )
            mReloadTiles = false
        }

        fun initVibrator(context: Context) {
            if (!::mVibrator.isInitialized) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE)
                        as VibratorManager
                mVibrator = vibratorManager.defaultVibrator
            }
        }

        // Function to check if phone is unlocked
        fun isUnlocked(mKeyguardStateController: Any): Boolean {
            val isShowing =
                XposedHelpers.getBooleanField(mKeyguardStateController, "mShowing")
            val canDismissLockScreen =
                XposedHelpers.getBooleanField(mKeyguardStateController, "mCanDismissLockScreen")

            return !(isShowing && !canDismissLockScreen)
        }

        fun getColorAttrDefaultColor(context: Context, i: Int): Int {
            val obtainStyledAttributes = context.obtainStyledAttributes(intArrayOf(i))
            val color = obtainStyledAttributes.getColor(0, 0)
            obtainStyledAttributes.recycle()
            return color
        }

        fun applyAlpha(f: Float, i: Int): Int {
            return Color.argb(
                (f * Color.alpha(i)).toInt(),
                Color.red(i),
                Color.green(i),
                Color.blue(i)
            )
        }


    }

    private val mContext: Context = context
    private val mHandler: Handler = handler
    private val mCameraManager = mContext.getSystemService(Context.CAMERA_SERVICE)
                as CameraManager
    private var mTorchCallback: TorchCallback = TorchCallback()



    fun registerTorchCallback() {
        mCameraManager.registerTorchCallback(mTorchCallback, mHandler)
    }

    fun toggleTorchMode() {
        setTorchMode(!isTorchEnabled)
    }

    private fun getCameraWithFlash(): String? {
        for (cameraId in mCameraManager.cameraIdList) {
            try {
                val characteristics = mCameraManager.getCameraCharacteristics(cameraId)
                val hasFlash = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
                val lensFacing = characteristics.get(CameraCharacteristics.LENS_FACING)
                if (hasFlash == true && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                    return cameraId
                }
            } catch (e: CameraAccessException) {
                Log.e(TAG, "Couldn't get camera characteristics.", e)
            }
        }
        return null
    }

    private fun setTorchMode(enabled: Boolean): Boolean {
        if (isTorchEnabled != enabled) {
            isTorchEnabled = enabled
            try {
                val cameraId = getCameraWithFlash() ?: return false
                mCameraManager.setTorchMode(cameraId, enabled)
                return true
            } catch (e: CameraAccessException) {
                Log.e(TAG, "Couldn't set torch mode.", e)
            }
        }
        return false
    }

    inner class TorchCallback : CameraManager.TorchCallback() {

        override fun onTorchModeChanged(cameraId: String, enabled: Boolean) {
            if (isTorchEnabled != enabled) {
                isTorchEnabled = enabled
            }
        }

        override fun onTorchModeUnavailable(cameraId: String) {
            isTorchEnabled = false
        }
    }
}
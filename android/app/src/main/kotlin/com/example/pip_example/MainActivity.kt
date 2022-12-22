package com.example.pip_example;

import android.app.PictureInPictureParams
import android.graphics.Point
import android.os.Build
import android.util.Rational
import android.view.Display
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel


class MainActivity : FlutterActivity() {
    private val CHANNEL = "flutter.ordendelfenix.com.channel"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
            .setMethodCallHandler { call: MethodCall, result: MethodChannel.Result ->
                if (call.method == "show") {
                    val d: Display = windowManager
                        .defaultDisplay
                    val p = Point()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                        d.getSize(p)
                    }
                    val width: Int = 16
                    val height: Int = 9
                    var ratio: Rational? = null
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ratio = Rational(width, height)
                    }
                    var pip_Builder: PictureInPictureParams.Builder? = null
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        pip_Builder = PictureInPictureParams.Builder()
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        pip_Builder!!.setAspectRatio(ratio).build()
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        enterPictureInPictureMode(pip_Builder!!.build())
                    }
                } else {
                    result.notImplemented()
                }
            }
    }
}
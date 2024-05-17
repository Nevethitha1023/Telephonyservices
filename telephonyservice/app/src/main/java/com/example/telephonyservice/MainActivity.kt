package com.example.telephonyservice

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.content.pm.PackageManager

import android.telephony.TelephonyManager
import android.widget.TextView

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.telephonyservice.ui.theme.TelephonyserviceTheme

class MainActivity : ComponentActivity() {
    private lateinit var textViewInfo: TextView
    private lateinit var telephonyManager: TelephonyManager
    private val REQUEST_CODE_READ_PHONE_STATE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewInfo = findViewById(R.id.textViewInfo)
        telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), REQUEST_CODE_READ_PHONE_STATE)
        } else {
            displayTelephonyInfo()
        }
    }

    private fun displayTelephonyInfo() {
        val deviceId = telephonyManager.deviceId
        val subscriberId = telephonyManager.subscriberId
        val simSerialNumber = telephonyManager.simSerialNumber
        val networkOperatorName = telephonyManager.networkOperatorName

        val info = """
            Device ID: $deviceId
            Subscriber ID: $subscriberId
            SIM Serial Number: $simSerialNumber
            Network Operator Name: $networkOperatorName
        """.trimIndent()

        textViewInfo.text = info
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_READ_PHONE_STATE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    displayTelephonyInfo()
                } else {
                    textViewInfo.text = "Permission denied."
                }
                return
            }
        }
    }

}


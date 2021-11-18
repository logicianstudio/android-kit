package com.android.kit.sample

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.os.Bundle
import com.android.kit.ktx.screenRatio
import com.android.kit.sample.databinding.ActivityMainBinding
import com.android.kit.ui.activity.ActivityKit

class MainActivity : ActivityKit<ActivityMainBinding>() {

    override fun onCreateBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.textView.text = "$screenRatio"

        requestPermission(READ_EXTERNAL_STORAGE) { isGranted ->
            if (isGranted) {
                // code here
            }
        }

        requestPermission(arrayOf(READ_EXTERNAL_STORAGE, CAMERA)) { grantMap ->
            if (grantMap.all { it.value }) { // all permissions granted
                // code here
            }
        }
    }

}

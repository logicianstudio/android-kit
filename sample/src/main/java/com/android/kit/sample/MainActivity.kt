package com.android.kit.sample

import android.Manifest.permission.*
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.android.kit.contract.ResultContractor
import com.android.kit.ktx.screenRatio
import com.android.kit.sample.databinding.ActivityMainBinding
import com.android.kit.ui.activity.KitActivity

class MainActivity : KitActivity<ActivityMainBinding>() {

    override fun onCreateBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.textView.text = "$screenRatio"

        val contractForPermission = ResultContractor.registerForActivityResult(
            this,
            ActivityResultContracts.RequestPermission()
        )
        contractForPermission.launch(WRITE_EXTERNAL_STORAGE) { isGranted ->

        }


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

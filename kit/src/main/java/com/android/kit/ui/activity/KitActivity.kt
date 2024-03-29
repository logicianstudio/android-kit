package com.android.kit.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.viewbinding.ViewBinding
import com.android.kit.contract.ResultContractor
import com.android.kit.ktx.lifecycleAwareLazy
import com.android.kit.listener.EventListener
import com.android.kit.model.NightMode
import com.android.kit.preference.KitPreference
import com.android.kit.ui.utility.FilterHelper

abstract class KitActivity<Binding : ViewBinding> : AppCompatActivity() {

    private lateinit var contractForResult: ResultContractor<Intent, ActivityResult>
    private lateinit var contractForPermission: ResultContractor<String, Boolean>
    private lateinit var contractForMultiplePermissions: ResultContractor<Array<String>, Map<String, Boolean>>

    open val blockScreenShot = false

    val binding by lifecycleAwareLazy {
        onCreateBinding()
    }

    abstract fun onCreateBinding(): Binding

    open fun onBindView(binding: Binding) {
    }

    private var isBackButtonEnabled = false
    private var backClickListener: EventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        contractForResult = ResultContractor.registerActivityForResult(this)
        contractForPermission = ResultContractor.registerForActivityResult(
            this,
            ActivityResultContracts.RequestPermission(),
        )
        contractForMultiplePermissions = ResultContractor.registerForActivityResult(
            this,
            ActivityResultContracts.RequestMultiplePermissions(),
        )

        val mode = KitPreference.nightMode
        AppCompatDelegate.setDefaultNightMode(mode.mode)
        super.onCreate(savedInstanceState)

        if (blockScreenShot) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE,
            )
        }

        setContentView(binding.root)
        onBindView(binding)
    }

    fun enableBackButton(backClickListener: EventListener? = null) {
        isBackButtonEnabled = true
        this.backClickListener = backClickListener
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            android.R.id.home -> {
                if (isBackButtonEnabled) {
                    backClickListener?.let { it() } ?: kotlin.run { finish() }
                }
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun setMenuIconsColor(menu: Menu, @ColorRes color: Int) {
        for (i in 0 until menu.size()) {
            menu.getItem(i).icon?.let { drawable ->
                drawable.mutate()
                FilterHelper.setColorFilter(
                    drawable,
                    ContextCompat.getColor(this, color),
                    FilterHelper.Mode.SRC_ATOP,
                )
            }
        }
    }

    protected fun finish(resultCode: Int) {
        setResult(resultCode)
        finish()
    }

    protected fun finish(resultCode: Int, data: Intent) {
        setResult(resultCode, data)
        finish()
    }

    protected fun launchForResult(intent: Intent, result: (result: ActivityResult) -> Unit) {
        contractForResult.launch(intent) { result(it) }
    }

    protected fun launchForResult(intent: Intent, options: ActivityOptionsCompat, result: (result: ActivityResult) -> Unit) {
        contractForResult.launch(intent, options) { result(it) }
    }

    protected fun requestPermission(permission: String, result: (isGranted: Boolean) -> Unit) {
        contractForPermission.launch(permission) { result(it) }
    }

    protected fun requestPermission(
        permissions: Array<String>,
        result: (resultMap: Map<String, Boolean>) -> Unit,
    ) {
        contractForMultiplePermissions.launch(permissions) { result(it) }
    }

    fun replaceFragment(containerId: Int, fragment: Fragment, allowStateLoss: Boolean = false) {
        supportFragmentManager.commit(allowStateLoss) {
            replace(containerId, fragment)
        }
    }

    fun restart() {
        finish()
        startActivity(intent)
    }

    protected fun setNightMode(nightMode: NightMode) {
        KitPreference.nightMode = nightMode
        AppCompatDelegate.setDefaultNightMode(nightMode.mode)
    }
}

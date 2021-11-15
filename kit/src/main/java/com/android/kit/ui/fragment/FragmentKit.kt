package com.android.kit.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.android.kit.contract.ResultContractor
abstract class FragmentKit<Binding : ViewDataBinding> : Fragment() {

    protected lateinit var binding: Binding

    private lateinit var contractForResult: ResultContractor<Intent, ActivityResult>
    private lateinit var contractForPermission: ResultContractor<String, Boolean>
    private lateinit var contractForMultiplePermissions: ResultContractor<Array<String>, Map<String, Boolean>>

    abstract fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contractForResult = ResultContractor.registerActivityForResult(this)
        contractForPermission = ResultContractor.registerForActivityResult(this, ActivityResultContracts.RequestPermission())
        contractForMultiplePermissions = ResultContractor.registerForActivityResult(this, ActivityResultContracts.RequestMultiplePermissions())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = onCreateBinding(layoutInflater, container)
        return binding.root
    }

    protected fun launchForResult(intent: Intent, result: (result: ActivityResult) -> Unit) {
        contractForResult.launch(intent) { result(it) }
    }

    protected fun requestPermission(permission: String, result: (isGranted: Boolean) -> Unit) {
        contractForPermission.launch(permission) { result(it) }
    }

    protected fun requestPermission(
        permissions: Array<String>,
        result: (resultMap: Map<String, Boolean>) -> Unit
    ) {
        contractForMultiplePermissions.launch(permissions) { result(it) }
    }

    fun replaceFragment(containerId: Int, fragment: Fragment, allowStateLoss: Boolean = false) {
        childFragmentManager.commit(allowStateLoss) {
            replace(containerId, fragment)
        }
    }
}

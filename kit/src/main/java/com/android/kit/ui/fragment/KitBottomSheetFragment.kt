package com.android.kit.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.viewbinding.ViewBinding
import com.android.kit.contract.ResultContractor
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class KitBottomSheetFragment<Binding : ViewBinding> : BottomSheetDialogFragment() {

    private var _binding: Binding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    protected val binding: Binding get() = _binding!!

    private lateinit var contractForResult: ResultContractor<Intent, ActivityResult>
    private lateinit var contractForPermission: ResultContractor<String, Boolean>
    private lateinit var contractForMultiplePermissions: ResultContractor<Array<String>, Map<String, Boolean>>

    abstract fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): Binding

    open fun onBindView(binding: Binding){

    }

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
        _binding = onCreateBinding(layoutInflater, container)
        onBindView(binding)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
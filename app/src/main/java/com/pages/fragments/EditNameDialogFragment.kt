package com.pages.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.Util.AppUtil
import com.kau.kotlinchatapp.R
import com.kau.kotlinchatapp.databinding.FragmentEditNameDialogBinding

class EditNameDialogFragment : DialogFragment() {

    private lateinit var editNameDialogBinding: FragmentEditNameDialogBinding
    private lateinit var fName: String
    private lateinit var lName: String
    private lateinit var appUtil: AppUtil


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_name_dialog, container, false)
    }


}
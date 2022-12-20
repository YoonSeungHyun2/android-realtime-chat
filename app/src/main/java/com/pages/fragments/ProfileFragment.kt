package com.pages.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.Permission.AppPermission
import com.ViewModels.ProfileViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.Util.AppUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.StorageReference
import com.kau.kotlinchatapp.R
import com.kau.kotlinchatapp.databinding.FragmentEditNameDialogBinding
import com.kau.kotlinchatapp.databinding.FragmentProfileBinding
import com.kau.kotlinchatapp.databinding.FragmentStatusDialogBinding
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment: Fragment() {

    private lateinit var profileBinding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var storageReference: StorageReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var statusDialogBinding: FragmentStatusDialogBinding
    private lateinit var editNameDialogBinding: FragmentEditNameDialogBinding
    private lateinit var dialog: AlertDialog
    private lateinit var appPermission: AppPermission
    private lateinit var appUtil: AppUtil


    fun loadImage(view: CircleImageView, imageUrl: String?) {
        imageUrl?.let {
            Glide.with(view.context).load(imageUrl).into(view)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        profileViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application).create(ProfileViewModel::class.java)
        statusDialogBinding = FragmentStatusDialogBinding.inflate(inflater, container, false)
        editNameDialogBinding = FragmentEditNameDialogBinding.inflate(inflater,container, false)
        appPermission = AppPermission()
        appUtil = AppUtil()


        profileViewModel.getUser().observe(viewLifecycleOwner, Observer {
            profileBinding.userModel = it

            Glide.with(profileBinding.root)
                .load(it.image)
                .fitCenter()
                .apply(RequestOptions().override(300,300))
                .into(profileBinding.imgProfile)

        })

        profileBinding.editNameBtn.setOnClickListener{
            getEditNameDialog()
        }

        profileBinding.imgEditStatus.setOnClickListener{
            getStatusDialog()
        }

        profileBinding.imgPickImage.setOnClickListener{
            editImage()
        }

        return profileBinding.root
    }


    private fun getStatusDialog(){
        if (statusDialogBinding.root.parent != null) {
            (statusDialogBinding.root.parent as ViewGroup).removeView(statusDialogBinding.root) // <- fix
        }

        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setView(statusDialogBinding.root)
        alertDialog.setCancelable(false)

        statusDialogBinding.btnEditStatus.setOnClickListener{
            val status = statusDialogBinding.edtUserStatus.text.toString()
            if(status.isNotEmpty()){
                profileViewModel.updateStatus(status)
                dialog.dismiss()
            }
        }

        dialog = alertDialog.create()
        dialog.show()
    }

    private fun getEditNameDialog(){
        if (editNameDialogBinding.root.parent != null) {
            (editNameDialogBinding.root.parent as ViewGroup).removeView(editNameDialogBinding.root) // <- fix
        }


        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setView(editNameDialogBinding.root)
        alertDialog.setCancelable(false)

        editNameDialogBinding.btnEditName.setOnClickListener {
            if(areFieldEmpty()){
                val userName = (editNameDialogBinding.edtFName.text.toString()+editNameDialogBinding.edtLName.text.toString()).trim()
                profileViewModel.updateName(userName)
                dialog.dismiss()
            }
        }
        dialog = alertDialog.create()
        dialog.show()
    }

    fun editImage(){
        val writePermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)

        if(writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE), GetUserDataFragment.REQ_GALLERY
                )
            } else {
                // 권한이 있는 경우 갤러리 실행
            }
            val intent = Intent(Intent.ACTION_PICK)
            // intent의 data와 type을 동시에 설정하는 메서드
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            imageResult.launch(intent)
    }



    private val imageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            // 이미지를 받으면 ImageView에 적용한다
            val image = result.data?.data
            image?.let{
                Glide.with(profileBinding.root)
                    .load(image)
                    .fitCenter()
                    .apply(RequestOptions().override(300,300))
                    .into(profileBinding.imgProfile)

                appUtil.uploadImg(image)
            }
        }
    }

    private fun areFieldEmpty(): Boolean {
        val fName = editNameDialogBinding.edtFName.text.toString()
        val lName = editNameDialogBinding.edtLName.text.toString()
        var required: Boolean = false
        var view: View? = null

        if (fName.isEmpty()) {
            editNameDialogBinding.edtFName.error = "값은 필수입니다."
            required = true
            view = editNameDialogBinding.edtFName

        } else if (lName.isEmpty()) {
            editNameDialogBinding.edtLName.error = "값은 필수입니다."
            required = true
            view = editNameDialogBinding.edtLName

        }

        return if (required) {
            view?.requestFocus()
            false
        } else true
    }
}
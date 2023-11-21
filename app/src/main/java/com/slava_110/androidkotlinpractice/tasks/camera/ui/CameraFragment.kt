package com.slava_110.androidkotlinpractice.tasks.camera.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.slava_110.androidkotlinpractice.R
import com.slava_110.androidkotlinpractice.databinding.FragmentCameraBinding
import com.slava_110.androidkotlinpractice.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

// Because Camera2 API is convoluted
@Suppress("DEPRECATION")
class CameraFragment : Fragment(R.layout.fragment_camera) {
    private val viewBinding by viewBinding(FragmentCameraBinding::bind)
    private val cameraViewModel by viewModel<CameraViewModel>()
    private lateinit var camera: android.hardware.Camera

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(checkCameraPermission()) {
            initCamera()
        }

        viewBinding.butPhoto.setOnClickListener {
            camera.takePicture(null, null) { pictureBytes, _ ->
                cameraViewModel.saveImage(pictureBytes)
            }
        }

        viewBinding.butLib.setOnClickListener {
            parentFragmentManager.commit {
                replace<CameraListFragment>(R.id.activity_main)
                addToBackStack(null)
            }
        }

        viewBinding.butBack25.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun initCamera() {
        camera = android.hardware.Camera.open()
        camera.setDisplayOrientation(90)

        viewBinding.surfaceView.holder.addCallback(object: SurfaceHolder.Callback {

            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    camera.setPreviewDisplay(holder)
                    camera.startPreview()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                try {
                    camera.stopPreview()
                } catch (e: Exception) {
                    Log.e("CameraPreview", "Error on stopping preview", e)
                }

                try {
                    camera.setPreviewDisplay(holder)
                    camera.startPreview()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                camera.stopPreview()
                camera.release()
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        camera.release()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                initCamera()
            } else {
                parentFragmentManager.popBackStackImmediate()
            }
        }

    private fun checkCameraPermission(): Boolean {
        return if(checkSelfPermission(requireContext(), Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED) {
            true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            false
        }
    }
}
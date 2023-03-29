package com.sam.watermyplant.presentation.cameraScreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.camera.view.PreviewView.ScaleType
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.sam.watermyplant.R
import com.sam.watermyplant.presentation.navigation.CameraNavigation
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CameraScreen(onAddClicked:()->Unit) {
    Scaffold {
        CameraNavigation(modifier = Modifier.padding(it)){
            onAddClicked()
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Camera(
    modifier: Modifier = Modifier,
    permission: String = android.Manifest.permission.CAMERA,
    onCapture:(Uri) ->Unit
) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(permission = permission)
    LaunchedEffect(key1 = true) {
        permissionState.launchPermissionRequest()
    }
    PermissionRequired(
        permissionState = permissionState,
        permissionNotGrantedContent = {},
        permissionNotAvailableContent = {
            Column(modifier) {
                Text(text = "Permission required")
                Button(
                    onClick = {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivity(intent)
                    }
                ) {
                    Text(text = "Open Settings")
                }
            }
        },
        content = {
            CameraCapture {
                onCapture(it.toUri())
            }
        }
    )
}


@Composable
fun CameraScreenPreview(
    modifier: Modifier = Modifier,
    scaleType: ScaleType = ScaleType.FILL_CENTER,
    onUseCase: (UseCase) -> Unit
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val previewView = PreviewView(context).apply {
                this.scaleType = scaleType
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }

            //Camera X useCase
            onUseCase(
                Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
            )
            previewView
        }
    )
}

@Composable
fun CameraCapture(
    modifier: Modifier = Modifier,
    selector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    onImageFile: (File) -> Unit = { }
) {
    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        var previewUseCase by remember {
            mutableStateOf<UseCase>(Preview.Builder().build())
        }
        val imageCaptureUseCase by remember {
            mutableStateOf(
                ImageCapture.Builder()
                    .setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY)
                    .build()
            )
        }
        CameraScreenPreview(
            modifier = Modifier.fillMaxSize(),
            onUseCase = {
                previewUseCase = it
            }
        )
        FloatingActionButton(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            onClick = {
                // TODO: Code to capture image
                scope.launch {
                    imageCaptureUseCase.takePicture(context.executor).let {
                        onImageFile(it)
                    }
                }
            }
        ) {
            Icon(
                modifier = Modifier.size(35.dp),
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription ="Capture Image"
            )
        }
        LaunchedEffect(key1 = previewUseCase) {
            val cameraProvider = context.getCameraProvider()
            try {
                // Must unbind the use-cases before rebinding them.
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, selector, previewUseCase, imageCaptureUseCase
                )
            } catch (ex: Exception) {
                Log.e("CameraCapture", "Failed to bind camera use cases", ex)
            }
        }
    }
}
package com.wngud.foodscanner.ui.screen.scanner

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScannerScreen() {
    val context = LocalContext.current
    val cameraXModule = remember { CameraXModule() }
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    var showPermissionDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        cameraPermissionState.launchPermissionRequest()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (cameraPermissionState.status.isGranted) {
            cameraXModule.initializeCamera(context)
            CameraPreview(cameraXModule = cameraXModule)
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    showPermissionDialog = true
                }) {
                    Text("카메라 권한 요청")
                }
            }
        }
    }

    // 권한 거부 다이얼로그
    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { Text("카메라 권한 필요") },
            text = { Text("바코드 스캔을 위해서는 카메라 권한이 필요합니다. 설정에서 권한을 허용해주세요.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showPermissionDialog = false
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivity(intent)
                    }
                ) {
                    Text("설정으로 이동")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPermissionDialog = false }) {
                    Text("취소")
                }
            }
        )
    }
}

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    cameraXModule: CameraXModule
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        cameraXModule.startCamera(lifecycleOwner)
    }

    DisposableEffect(Unit) {
        onDispose {
            cameraXModule.unbindCamera()
        }
    }

    Box(modifier.fillMaxSize()) {
        AndroidView(
            factory = { cameraXModule.getPreviewView() },
            modifier = modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(300.dp)
        ) {
            ScannerCornerOverlay()
        }
    }
}

@Composable
fun ScannerCornerOverlay() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // 스캐너 영역 크기 설정
        val scannerWidth = width * 0.8f
        val scannerHeight = height * 0.7f

        // 스캐너 영역의 좌측 상단 좌표
        val left = (width - scannerWidth) / 2
        val top = (height - scannerHeight) / 2

        // 스캐너 영역의 우측 하단 좌표
        val right = left + scannerWidth
        val bottom = top + scannerHeight

        // 모서리 길이 (스캐너 영역 너비/높이의 15%)
        val cornerLength = minOf(scannerWidth, scannerHeight) * 0.15f

        // 모서리 곡률 반경
        val cornerRadius = 12.dp.toPx()

        val strokeWidth = 2.dp.toPx()
        val cornerColor = Color.White

        // 스트로크 스타일 설정 (둥근 끝 부분)
        val stroke = Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )

        // 좌측 상단 모서리
        drawPath(
            path = Path().apply {
                moveTo(left + cornerRadius, top)
                lineTo(left + cornerLength, top)
                moveTo(left, top + cornerRadius)
                lineTo(left, top + cornerLength)
                // 모서리 곡선 추가
                moveTo(left + cornerRadius, top)
                quadraticTo(left, top, left, top + cornerRadius)
            },
            color = cornerColor,
            style = stroke
        )

        // 우측 상단 모서리
        drawPath(
            path = Path().apply {
                moveTo(right - cornerRadius, top)
                lineTo(right - cornerLength, top)
                moveTo(right, top + cornerRadius)
                lineTo(right, top + cornerLength)
                // 모서리 곡선 추가
                moveTo(right - cornerRadius, top)
                quadraticTo(right, top, right, top + cornerRadius)
            },
            color = cornerColor,
            style = stroke
        )

        // 좌측 하단 모서리
        drawPath(
            path = Path().apply {
                moveTo(left + cornerRadius, bottom)
                lineTo(left + cornerLength, bottom)
                moveTo(left, bottom - cornerRadius)
                lineTo(left, bottom - cornerLength)
                // 모서리 곡선 추가
                moveTo(left + cornerRadius, bottom)
                quadraticTo(left, bottom, left, bottom - cornerRadius)
            },
            color = cornerColor,
            style = stroke
        )

        // 우측 하단 모서리
        drawPath(
            path = Path().apply {
                moveTo(right - cornerRadius, bottom)
                lineTo(right - cornerLength, bottom)
                moveTo(right, bottom - cornerRadius)
                lineTo(right, bottom - cornerLength)
                // 모서리 곡선 추가
                moveTo(right - cornerRadius, bottom)
                quadraticTo(right, bottom, right, bottom - cornerRadius)
            },
            color = cornerColor,
            style = stroke
        )
    }
}
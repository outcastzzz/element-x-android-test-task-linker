/*
 * Copyright (c) 2026 Element Creations Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package io.element.android.features.preferences.impl.reportProblem

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.zacsweers.metro.Inject
import io.element.android.libraries.architecture.Presenter
import io.element.android.libraries.core.meta.BuildMeta
import java.util.Locale
import java.util.TimeZone

@Inject
class ReportProblemPresenter(
    private val buildMeta: BuildMeta,
    private val context: Context,
) : Presenter<ReportProblemState> {

    @Composable
    override fun present(): ReportProblemState {
        var description by remember { mutableStateOf("") }
        val diagnosticsInfo = remember { getDiagnosticsInfo() }

        fun handleEvents(event: ReportProblemEvents) {
            when (event) {
                is ReportProblemEvents.UpdateDescription -> {
                    description = event.text
                }
                is ReportProblemEvents.CopyToClipboard -> {
                    val state = ReportProblemState(
                        problemDescription = description,
                        diagnosticsInfo = diagnosticsInfo,
                        eventSink = {}
                    )
                    copyToClipboard(state.fullReport)
                }
                is ReportProblemEvents.Share -> {
                    val state = ReportProblemState(
                        problemDescription = description,
                        diagnosticsInfo = diagnosticsInfo,
                        eventSink = {}
                    )
                    shareReport(state.fullReport)
                }
            }
        }

        return ReportProblemState(
            problemDescription = description,
            diagnosticsInfo = diagnosticsInfo,
            eventSink = ::handleEvents
        )
    }

    private fun getDiagnosticsInfo(): DiagnosticsInfo {
        return DiagnosticsInfo(
            appVersion = buildMeta.versionName,
            sdkLevel = Build.VERSION.SDK_INT,
            androidVersion = Build.VERSION.RELEASE,
            deviceModel = Build.MODEL,
            deviceManufacturer = Build.MANUFACTURER,
            locale = Locale.getDefault().toString(),
            timezone = TimeZone.getDefault().id
        )
    }

    private fun copyToClipboard(text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Bug Report", text)
        clipboard.setPrimaryClip(clip)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareReport(report: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Element X Bug Report")
            putExtra(Intent.EXTRA_TEXT, report)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(Intent.createChooser(intent, "Share Bug Report").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }
}

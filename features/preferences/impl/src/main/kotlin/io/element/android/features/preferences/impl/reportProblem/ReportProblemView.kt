/*
 * Copyright (c) 2026 Element Creations Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package io.element.android.features.preferences.impl.reportProblem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import io.element.android.compound.theme.ElementTheme
import io.element.android.libraries.designsystem.components.preferences.PreferencePage
import io.element.android.libraries.designsystem.preview.ElementPreview
import io.element.android.libraries.designsystem.preview.PreviewsDayNight
import io.element.android.libraries.designsystem.theme.components.Icon
import io.element.android.libraries.designsystem.theme.components.Text

@Composable
fun ReportProblemView(
    state: ReportProblemState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PreferencePage(
        modifier = modifier,
        onBackClick = onBackClick,
        title = "Report a Problem"
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Problem Description Section
            Text(
                text = "Describe your problem",
                style = ElementTheme.typography.fontBodyLgMedium,
            )

            OutlinedTextField(
                value = state.problemDescription,
                onValueChange = { state.eventSink(ReportProblemEvents.UpdateDescription(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp),
                placeholder = {
                    Text(
                        text = "Enter problem description...\n\n" +
                            "Include:\n" +
                            "• Steps to reproduce\n" +
                            "• Expected behavior\n" +
                            "• Actual behavior"
                    )
                }
            )

            // Diagnostics Section
            DiagnosticsCard(diagnostics = state.diagnosticsInfo)

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { state.eventSink(ReportProblemEvents.CopyToClipboard) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp).padding(end = 8.dp)
                    )

                    Text("Copy")
                }

                Button(
                    onClick = { state.eventSink(ReportProblemEvents.Share) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp).padding(end = 8.dp)
                    )

                    Text("Share")
                }
            }
        }
    }
}

@Composable
private fun DiagnosticsCard(
    diagnostics: DiagnosticsInfo,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = ElementTheme.colors.bgSubtleSecondary,
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "Device Diagnostics",
                style = ElementTheme.typography.fontBodyLgMedium,
                fontWeight = FontWeight.Bold,
            )

            DiagnosticRow("App Version", diagnostics.appVersion)
            DiagnosticRow("Android SDK", diagnostics.sdkLevel.toString())
            DiagnosticRow("Android Version", diagnostics.androidVersion)
            DiagnosticRow("Device", "${diagnostics.deviceManufacturer} ${diagnostics.deviceModel}")
            DiagnosticRow("Locale", diagnostics.locale)
            DiagnosticRow("Timezone", diagnostics.timezone)
        }
    }
}

@Composable
private fun DiagnosticRow(
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = ElementTheme.typography.fontBodyMdRegular,
            color = ElementTheme.colors.textSecondary
        )
        Text(
            text = value,
            style = ElementTheme.typography.fontBodyMdRegular,
        )
    }
}

@PreviewsDayNight
@Composable
internal fun ReportProblemViewPreview(
    @PreviewParameter(ReportProblemStateProvider::class) state: ReportProblemState
) = ElementPreview {
    ReportProblemView(
        state = state,
        onBackClick = {},
    )
}

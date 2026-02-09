/*
 * Copyright (c) 2026 Element Creations Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package io.element.android.features.preferences.impl.reportProblem

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ReportProblemStateProvider : PreviewParameterProvider<ReportProblemState> {
    override val values: Sequence<ReportProblemState>
        get() = sequenceOf(
            ReportProblemState(),
            ReportProblemState(problemDescription = "App crashes when I open settings"),
        )
}

fun ReportProblemState(
    problemDescription: String = "",
) = ReportProblemState(
    problemDescription = problemDescription,
    diagnosticsInfo = DiagnosticsInfo(
        appVersion = "1.0.0",
        sdkLevel = 34,
        androidVersion = "14",
        deviceModel = "Pixel 8",
        deviceManufacturer = "Google",
        locale = "en_US",
        timezone = "Europe/Moscow"
    ),
    eventSink = {}
)

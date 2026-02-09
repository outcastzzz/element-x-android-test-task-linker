/*
 * Copyright (c) 2026 Element Creations Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package io.element.android.features.preferences.impl.reportProblem

data class ReportProblemState(
    val problemDescription: String,
    val diagnosticsInfo: DiagnosticsInfo,
    val eventSink: (ReportProblemEvents) -> Unit,
) {
    val fullReport: String
        get() = buildString {
            appendLine(problemDescription.ifEmpty { TEMPLATE })
            appendLine()
            append(diagnosticsInfo.toFormattedString())
        }
}

data class DiagnosticsInfo(
    val appVersion: String,
    val sdkLevel: Int,
    val androidVersion: String,
    val deviceModel: String,
    val deviceManufacturer: String,
    val locale: String,
    val timezone: String,
) {
    fun toFormattedString(): String = buildString {
        appendLine("=== Device Diagnostics ===")
        appendLine("App Version: $appVersion")
        appendLine("Android SDK: $sdkLevel")
        appendLine("Android Version: $androidVersion")
        appendLine("Device: $deviceManufacturer $deviceModel")
        appendLine("Locale: $locale")
        appendLine("Timezone: $timezone")
        appendLine("==========================")
    }
}

sealed interface ReportProblemEvents {
    data class UpdateDescription(val text: String) : ReportProblemEvents
    data object CopyToClipboard : ReportProblemEvents
    data object Share : ReportProblemEvents
}

private val TEMPLATE = """
## Problem Description
[Describe the issue you encountered]

## Steps to Reproduce
1. 
2. 
3. 

## Expected Behavior
[What did you expect to happen?]

## Actual Behavior
[What actually happened?]

## Additional Context
[Any other relevant information]
""".trimIndent()

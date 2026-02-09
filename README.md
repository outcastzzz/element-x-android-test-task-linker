# Test Task: Report a Problem Screen

## Overview

Implemented a "Report a Problem" screen for Element X Android that allows users to create bug reports with device diagnostic information.

## Features

- ✅ Multi-line TextField with a template for problem description
- ✅ Device diagnostics block:
  - App version
  - Android SDK level
  - Android version
  - Device model
  - Locale
  - Timezone
- ✅ Copy button — copies the report to clipboard
- ✅ Share button — shares via Intent

## Created Files
features/preferences/impl/src/main/kotlin/io/element/android/features/preferences/impl/reportproblem/
├── ReportProblemState.kt         # State and Events
├── ReportProblemPresenter.kt     # Presenter with business logic
├── ReportProblemView.kt          # Compose UI
├── ReportProblemNode.kt          # Appyx Node for navigation
└── ReportProblemStateProvider.kt # Preview Provider

## Modified Files

### PreferencesFlowNode.kt

Added new NavTarget:

```kotlin
sealed interface NavTarget : Parcelable {
    // ... existing targets
    
    @Parcelize
    data object ReportProblem : NavTarget
}

NavTarget.ReportProblem -> {
    createNode<ReportProblemNode>(buildContext)
}

override fun navigateToBugReport() {
    backstack.push(NavTarget.ReportProblem)
}
```

# CivilShield Agent Guidelines & Architecture Principles

This document serves as the single source of truth for coding agents (such as Antigravity) when implementing or refactoring features, screens, and shared logic in the CivilShield project.

---

## 1. Project Organization: Feature-First & Core Split

Organize code cleanly by dividing the codebase into **`core`** and **`features`**:

```
com.civil.shield/
├── core/
│   ├── ui/
│   │   ├── components/      # Reusable, completely stateless Compose components
│   │   └── theme/           # Theme provider, Color mapping, Spacing tokens
│   ├── data/                # Core networking, shared database/cache, global repositories
│   └── di/                  # Core dependency injection modules
└── features/
    └── <feature_name>/      # e.g., auth, alerts, reporting, settings
        ├── ui/              # Screens, screen-specific subcomponents, local UI state
        ├── data/            # Feature-specific repositories, API services, mappers
        └── di/              # Feature-specific DI bindings
```

### Screen Decomposition Rules
- **Break Down Large Screens**: Decompose screens into cohesive sections (e.g., TopBar, Identity/Content Cluster, Footer/Actions) within the feature's `ui` package.
- **Extract Reusable Components**: Generic UI components (e.g., Buttons, Logos, Dividers, Input fields, Badges) must **never** stay trapped inside a specific screen. Move them to `core/ui/components/`.

---

## 2. Design System Integration (`:design-system`)

All visual tokens and user-facing copy must be driven by the design system module (`:design-system`).

### A. Colors (No Hardcoding)
- **Rule**: Never hardcode colors directly in screen composables (e.g., `Color(0xFF002B7F)` is strictly forbidden).
- **Usage**: Access colors through `CivilShieldTheme.colors.<token>` or `Color(CivilShieldTheme.colors.<token>)`.
- **Definition**: New color tokens must be registered in `:design-system` (`AppColors.kt` / `BaseDsColors`).
- Reference: [kmp-design-system #colors](https://github.com/savantarch/kmp-design-system#colors)

### B. Strings (No Hardcoded Copy)
- **Rule**: Never hardcode text strings in Compose UI (e.g., `text = "CIVILSHIELD"` or `"Autentificare"` is forbidden).
- **Usage**: Define all strings as tokens in `AppStrings.kt` (under `:design-system`) using `AppStrings.<TOKEN>.defaultText()`.
- Reference: [kmp-design-system #strings](https://github.com/savantarch/kmp-design-system#strings)

### C. Spacing & Sizing Tokens
- **Rule**: Avoid arbitrary magic numbers for dimensions and padding across screens.
- **Usage**: Define and reference standardized layout tokens in `core/ui/theme/Spacing.kt`:
  - `SpacingXXSmall` (2.dp), `SpacingXSmall` (4.dp), `SpacingSmall` (8.dp), `SpacingMedium` (16.dp), `SpacingLarge` (20.dp), `SpacingXLarge` (24.dp), `SpacingXXLarge` (32.dp), `SpacingHuge` (56.dp)
  - `MinTouchTarget` (56.dp)
  - `IconSizeSmall` (18.dp), `IconSizeMedium` (24.dp), `IconSizeLarge` (64.dp)
  - `LogoContainerSize` (120.dp)

---

## 3. Jetpack Compose Previews Mandatory for All Components

Every reusable component in `core/ui/components/` and every feature screen composable **must** include `@Preview` composables.

- **Requirements**:
  - Always wrap previews inside `CivilShieldTheme { ... }`.
  - Set `showBackground = true` and `backgroundColor` matching the design (e.g., `0xFF0A192F` for navy theme).
  - Provide previews covering primary states (e.g., **Default State**, **Loading State**, **Disabled/Error State**).

Example:
```kotlin
@Preview(name = "Primary Button - Default", showBackground = true, backgroundColor = 0xFF0A192F)
@Composable
private fun PrimaryButtonPreview() {
    CivilShieldTheme {
        PrimaryButton(
            text = "Autentificare cu ROeID",
            leadingIcon = Icons.Default.Fingerprint,
            onClick = {}
        )
    }
}
```

---

## 4. Kotlin Multiplatform (KMP) ViewModels & State Management

All presentation logic, UI state, and actions reside in the shared module (`:app:sharedLogic`) in `commonMain`:

### A. Official `androidx.lifecycle:lifecycle-viewmodel` KMP
- ViewModels must extend `androidx.lifecycle.ViewModel` (which natively supports Kotlin Multiplatform in AndroidX 2.8+).
- Do not introduce Android-only framework dependencies inside `commonMain`.

### B. MVI / Unidirectional Data Flow (UDF) Structure
- **State**: Immutable data class (e.g., `AuthScreenState`).
- **Actions**: Sealed interface modeling all user interactions (e.g., `AuthUiAction`).
- **StateFlow**: Expose `val state: StateFlow<ScreenState> = _state.asStateFlow()`.
- **Action Dispatcher**: Handle events via `fun onAction(action: ScreenAction)`.

### C. Compose Screen Connection
- Split each screen into:
  1. **Stateful Wrapper**:
     ```kotlin
     @Composable
     fun FeatureScreen(
         modifier: Modifier = Modifier,
         viewModel: FeatureViewModel = viewModel()
     ) {
         val state by viewModel.state.collectAsStateWithLifecycle()
         FeatureScreenContent(
             state = state,
             onAction = viewModel::onAction,
             modifier = modifier
         )
     }
     ```
  2. **Stateless Content Composable**:
     ```kotlin
     @Composable
     fun FeatureScreenContent(
         state: FeatureState,
         onAction: (FeatureUiAction) -> Unit,
         modifier: Modifier = Modifier
     ) { ... }
     ```

---

## 5. Visual Fidelity & Verification (Stitch / Design Alignment)

When building or updating UI from design specifications (e.g., Stitch / Figma):
- **Verify against real rendering**: Use the Maestro MCP / ADB screencap tools to capture live device screenshots and compare side-by-side with Stitch designs.
- **Pay attention to subtle details**:
  - Exact canvas background color (e.g. `primaryContainer` `#0A192F` vs `surface`).
  - Corner radius tokens (`RoundedCornerShape(SpacingSmall)` 8dp for `rounded-lg`).
  - Correct iconography (e.g. `Fingerprint` vs generic `Lock`).
  - Letter spacing and line heights for typography.
  - Component borders and subtle opacity levels.

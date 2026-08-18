# CivilShield Design System (Kotlin Multiplatform / Mobile)

Mobile design system specifications for **CivilShield**, adapted from the Stitch MCP project (`projects/8327322751124332825`) for cross-platform implementation on **Android** (Jetpack Compose / Compose Multiplatform) and **iOS** (SwiftUI / UIKit) using `kmp-design-system`.

---

## 🎨 Color Tokens (`DsColors` / `AppColors`)

All colors are defined as 8-digit ARGB hex tokens for Compose Multiplatform and mapped natively to iOS `Color` asset catalogs / SwiftUI `Color`.

### Base & Surfaces
| Token | Hex Value | Compose (`Color`) | SwiftUI / iOS | Description |
| :--- | :--- | :--- | :--- | :--- |
| `background` | `#FF131315` | `Color(0xFF131315)` | `Color(hex: "131315")` | Main app background |
| `surface` | `#FF131315` | `Color(0xFF131315)` | `Color(hex: "131315")` | Base surface |
| `surfaceContainerLowest` | `#FF0E0E10` | `Color(0xFF0E0E10)` | `Color(hex: "0E0E10")` | Lowest elevation surface |
| `surfaceContainerLow` | `#FF1B1B1D` | `Color(0xFF1B1B1D)` | `Color(hex: "1B1B1D")` | Low elevation surface |
| `surfaceContainer` | `#FF1F1F21` | `Color(0xFF1F1F21)` | `Color(hex: "1F1F21")` | Standard container surface |
| `surfaceContainerHigh` | `#FF2A2A2C` | `Color(0xFF2A2A2C)` | `Color(hex: "2A2A2C")` | Elevated card/modal surface |
| `surfaceContainerHighest` | `#FF343536` | `Color(0xFF343536)` | `Color(hex: "343536")` | Highest elevation surface |
| `surfaceBright` | `#FF39393B` | `Color(0xFF39393B)` | `Color(hex: "39393B")` | Bright surface variant |
| `surfaceDim` | `#FF131315` | `Color(0xFF131315)` | `Color(hex: "131315")` | Dim surface variant |
| `onSurface` | `#FFE4E2E4` | `Color(0xFFE4E2E4)` | `Color(hex: "E4E2E4")` | Primary text on surface |
| `onSurfaceVariant` | `#FFC5C6CD` | `Color(0xFFC5C6CD)` | `Color(hex: "C5C6CD")` | Muted secondary text |

### Primary Accent
| Token | Hex Value | Compose (`Color`) | SwiftUI / iOS | Description |
| :--- | :--- | :--- | :--- | :--- |
| `primary` | `#FFB9C7E4` | `Color(0xFFB9C7E4)` | `Color(hex: "B9C7E4")` | Soft blue primary tint |
| `onPrimary` | `#FF233148` | `Color(0xFF233148)` | `Color(hex: "233148")` | Text/icon on primary |
| `primaryContainer` | `#FF0A192F` | `Color(0xFF0A192F)` | `Color(hex: "0A192F")` | Deep navy primary container |
| `onPrimaryContainer` | `#FF74829D` | `Color(0xFF74829D)` | `Color(hex: "74829D")` | Text on primary container |
| `primaryFixed` | `#FFD6E3FF` | `Color(0xFFD6E3FF)` | `Color(hex: "D6E3FF")` | Fixed primary highlight |
| `primaryFixedDim` | `#FFB9C7E4` | `Color(0xFFB9C7E4)` | `Color(hex: "B9C7E4")` | Dimmed fixed primary |

### Secondary & Tertiary
| Token | Hex Value | Compose (`Color`) | SwiftUI / iOS | Description |
| :--- | :--- | :--- | :--- | :--- |
| `secondary` | `#FFC6C6C7` | `Color(0xFFC6C6C7)` | `Color(hex: "C6C6C7")` | Slate secondary accent |
| `secondaryContainer` | `#FF454747` | `Color(0xFF454747)` | `Color(hex: "454747")` | Muted secondary container |
| `tertiary` | `#FFE7BF99` | `Color(0xFFE7BF99)` | `Color(hex: "E7BF99")` | Warm amber accent |
| `tertiaryContainer` | `#FF281400` | `Color(0xFF281400)` | `Color(hex: "281400")` | Deep amber container |

### Status, Emergency & Outlines
| Token | Hex Value | Compose (`Color`) | SwiftUI / iOS | Description |
| :--- | :--- | :--- | :--- | :--- |
| `error` | `#FFFFB4AB` | `Color(0xFFFFB4AB)` | `Color(hex: "FFB4AB")` | High visibility error tint |
| `errorContainer` | `#FF93000A` | `Color(0xFF93000A)` | `Color(hex: "93000A")` | Deep red error container |
| `emergencySos` | `#FFCE1126` | `Color(0xFFCE1126)` | `Color(hex: "CE1126")` | Critical SOS alert red |
| `activeWarning` | `#FFFCD116` | `Color(0xFFFCD116)` | `Color(hex: "FCD116")` | Cautionary state yellow |
| `outline` | `#FF8F9097` | `Color(0xFF8F9097)` | `Color(hex: "8F9097")` | Primary border / outline |
| `outlineVariant` | `#FF44474D` | `Color(0xFF44474D)` | `Color(hex: "44474D")` | Subtle divider outline |

---

## 🔤 Mobile Typography (`DsTypography` / `AppTypography`)

Font sizes use scale-independent units (**`sp`** in Compose Multiplatform for Android / **`pt`** scalable points in iOS SwiftUI) to ensure accessibility and dynamic type scaling across both operating systems.

- **Primary Font Family**: **Inter** (`FontFamily` in Compose / `Font.custom("Inter", ...)` in SwiftUI)

| Token / Style | Android / KMP (`sp`) | iOS SwiftUI (`pt`) | Weight | Line Height | Letter Spacing | KMP Material Token Mapping |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| `headlineLg` | `32.sp` | `32pt` | Bold (`FontWeight.W700`) | `40.sp` / `40pt` | `-0.02.em` | `displayLarge` |
| `headlineLgMobile` | `28.sp` | `28pt` | Bold (`FontWeight.W700`) | `34.sp` / `34pt` | `-0.02.em` | `headlineLarge` |
| `headlineMd` | `24.sp` | `24pt` | SemiBold (`FontWeight.W600`) | `32.sp` / `32pt` | Normal | `headlineMedium` |
| `callout` | `20.sp` | `20pt` | SemiBold (`FontWeight.W600`) | `26.sp` / `26pt` | Normal | `titleMedium` |
| `bodyLg` | `18.sp` | `18pt` | Regular (`FontWeight.W400`) | `28.sp` / `28pt` | Normal | `bodyLarge` |
| `bodyMd` | `16.sp` | `16pt` | Regular (`FontWeight.W400`) | `24.sp` / `24pt` | Normal | `bodyMedium` |
| `labelBold` | `14.sp` | `14pt` | Bold (`FontWeight.W700`) | `20.sp` / `20pt` | `0.05.em` | `labelLarge` |

---

## 📐 Mobile Layout, Spacing & Shapes (`DsSpacing` & `DsShapes`)

Density-independent physical spacing units (**`dp`** on Android / **`pt`** on iOS) defined according to the `kmp-design-system` token contract.

### Spacing Grid (`DsSpacing`)
Derived from an **8dp / 8pt base unit grid**:

| Token Name | Android (`Dp`) | iOS (`CGFloat` / `pt`) | Usage / Purpose |
| :--- | :--- | :--- | :--- |
| `none` | `0.dp` | `0` | Zero margin / padding |
| `xxs` | `2.dp` | `2` | Micro border offsets / hairline spacing |
| `xs` | `4.dp` | `4` | Stack SM / compact inner padding |
| `sm` | `8.dp` | `8` | Base spacing unit / Stack MD |
| `md` | `16.dp` | `16` | Gutter / card padding / Stack LG |
| `lg` | `20.dp` | `20` | Container side margins (screen padding) |
| `xl` | `32.dp` | `32` | Section spacing / Stack XL |
| `xxl` | `56.dp` | `56` | Minimum touch target height/width (`touchTargetMin`) |

### Specialized Layout Contract Tokens
- `baseUnit = 8.dp` (`8pt`)
- `touchTargetMin = 56.dp` (`56pt`) - Guarantees WCAG AAA touch accessibility under high stress
- `containerMargin = 20.dp` (`20pt`) - Standard screen side padding
- `gutter = 16.dp` (`16pt`) - Column/card grid spacing
- `ghostBorderWidth = 2.dp` (`2pt`) - High-contrast solid border width for dark mode buttons/inputs

### Shapes & Corner Radii (`DsShapes`)
Soft geometric corners tailored for official defense applications:

| Token Name | Android (`Dp`) | iOS (`CGFloat` / `pt`) | Corner Description |
| :--- | :--- | :--- | :--- |
| `sm` | `2.dp` | `2` | Subtle rounding (badges, chips) |
| `md` / `default` | `4.dp` | `4` | Standard soft geometry (buttons, cards, inputs) |
| `lg` | `8.dp` | `8` | Medium rounding (bottom sheets, modals) |
| `xl` | `12.dp` | `12` | Large container rounding |
| `full` | `9999.dp` | `9999` | Fully rounded pill / circular shape |

---

## 🛠️ `kmp-design-system` Implementation Guide

Below is the Kotlin Multiplatform contract implementation for shared modules referenced by both Android (Jetpack Compose) and iOS (SwiftUI / UIKit):

```kotlin
// commonMain/kotlin/com/civilshield/designsystem/AppSpacing.kt
package com.civilshield.designsystem

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppSpacing(
    val none: Dp = 0.dp,
    val xxs: Dp = 2.dp,
    val xs: Dp = 4.dp,
    val sm: Dp = 8.dp,
    val md: Dp = 16.dp,
    val lg: Dp = 20.dp,
    val xl: Dp = 32.dp,
    val xxl: Dp = 56.dp,
    val touchTargetMin: Dp = 56.dp,
    val containerMargin: Dp = 20.dp,
    val gutter: Dp = 16.dp,
    val ghostBorderWidth: Dp = 2.dp
)

val LocalAppSpacing = staticCompositionLocalOf { AppSpacing() }
```

```kotlin
// commonMain/kotlin/com/civilshield/designsystem/AppTypography.kt
package com.civilshield.designsystem

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class AppTypography(
    val headlineLg: TextStyle = TextStyle(
        fontFamily = FontFamily.Default, // Inter
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.02).sp
    ),
    val headlineLgMobile: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = (-0.02).sp
    ),
    val headlineMd: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    val callout: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp
    ),
    val bodyLg: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 28.sp
    ),
    val bodyMd: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    val labelBold: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.05.sp
    )
)

val LocalAppTypography = staticCompositionLocalOf { AppTypography() }
```

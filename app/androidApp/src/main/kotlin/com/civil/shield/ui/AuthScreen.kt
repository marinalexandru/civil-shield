package com.civil.shield.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.civilshield.designsystem.AppColors

@Composable
fun AuthScreen(
    onAuthenticateClick: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {}
) {
    var selectedLanguage by remember { mutableStateOf("RO") }
    var languageDropdownExpanded by remember { mutableStateOf(false) }

    val appColors = CivilShieldTheme.colors

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(appColors.surfaceContainerHigh))
                        .clickable { languageDropdownExpanded = true }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Language,
                        contentDescription = "Language",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = selectedLanguage,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Select Language",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                }

                DropdownMenu(
                    expanded = languageDropdownExpanded,
                    onDismissRequest = { languageDropdownExpanded = false }
                ) {
                    listOf("RO", "EN", "HU").forEach { lang ->
                        DropdownMenuItem(
                            text = { Text(text = lang) },
                            onClick = {
                                selectedLanguage = lang
                                languageDropdownExpanded = false
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Identity Cluster (Logo + Title + Subtitle)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                // Shield Logo with Glow Effect
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    Color(appColors.primaryFixed).copy(alpha = 0.25f),
                                    Color.Transparent
                                )
                            )
                        )
                        .border(
                            width = 1.dp,
                            color = Color(appColors.outlineVariant).copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "CivilShield Logo",
                        tint = Color(appColors.primaryFixed),
                        modifier = Modifier.size(64.dp)
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Romanian Tricolor Border line under Title
                val roBlue = Color(0xFF002B7F)
                val roYellow = Color(0xFFFCD116)
                val roRed = Color(0xFFCE1126)

                Text(
                    text = "CIVILSHIELD",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        letterSpacing = 4.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .drawBehind {
                            val strokeWidth = 2.dp.toPx()
                            val y = size.height + 6.dp.toPx()
                            val segmentWidth = size.width / 3f

                            // Romanian Tricolor bottom border
                            drawLine(
                                color = roBlue,
                                start = Offset(0f, y),
                                end = Offset(segmentWidth, y),
                                strokeWidth = strokeWidth
                            )
                            drawLine(
                                color = roYellow,
                                start = Offset(segmentWidth, y),
                                end = Offset(segmentWidth * 2f, y),
                                strokeWidth = strokeWidth
                            )
                            drawLine(
                                color = roRed,
                                start = Offset(segmentWidth * 2f, y),
                                end = Offset(size.width, y),
                                strokeWidth = strokeWidth
                            )
                        }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Sistemul Național de Alertă și Protecție Civilă",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(0.85f)
                )
            }

            // Transactional Actions (Primary Button + Protection info + Footer links)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Primary Action Button (ROeID Login)
                Button(
                    onClick = onAuthenticateClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(appColors.primaryContainer)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Fingerprint,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Autentificare cu ROeID",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(appColors.primaryContainer)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "PROTEJAT DE SERVICIUL DE TELECOMUNICAȚII SPECIALE",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 10.sp,
                        letterSpacing = 1.2.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onTermsClick) {
                        Text(
                            text = "Termeni și Condiții",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    TextButton(onClick = onPrivacyClick) {
                        Text(
                            text = "Confidențialitate",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    CivilShieldTheme {
        AuthScreen()
    }
}

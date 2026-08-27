package com.civilshield.designsystem

import com.savantarch.design.DsStrings

/**
 * CivilShield string tokens conforming to DsStrings.
 */
enum class AppStrings : DsStrings {
    APP_NAME,
    AUTH_LANGUAGE_CD,
    AUTH_SELECT_LANGUAGE_CD,
    AUTH_LOGO_CD,
    AUTH_TITLE,
    AUTH_SUBTITLE,
    AUTH_LOGIN_BUTTON,
    AUTH_PROTECTED_BY,
    AUTH_TERMS,
    AUTH_PRIVACY,
    AUTH_LOGOUT,
    AUTH_SUCCESS_BADGE,
    LANG_RO,
    LANG_EN,
    LANG_HU;

    /**
     * Default fallback / English & Romanian translation mapping.
     */
    fun defaultText(): String = when (this) {
        APP_NAME -> "CivilShield"
        AUTH_LANGUAGE_CD -> "Language"
        AUTH_SELECT_LANGUAGE_CD -> "Select Language"
        AUTH_LOGO_CD -> "CivilShield Logo"
        AUTH_TITLE -> "CIVILSHIELD"
        AUTH_SUBTITLE -> "Sistemul Național de Alertă și Protecție Civilă"
        AUTH_LOGIN_BUTTON -> "Autentificare cu ROeID"
        AUTH_PROTECTED_BY -> "PROTEJAT DE SERVICIUL DE TELECOMUNICAȚII SPECIALE"
        AUTH_TERMS -> "Termeni și Condiții"
        AUTH_PRIVACY -> "Confidențialitate"
        AUTH_LOGOUT -> "Deconectare"
        AUTH_SUCCESS_BADGE -> "Autentificat cu succes"
        LANG_RO -> "RO"
        LANG_EN -> "EN"
        LANG_HU -> "HU"
    }
}

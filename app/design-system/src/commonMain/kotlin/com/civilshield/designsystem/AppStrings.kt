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
    AUTH_DEV_SHORTCUT_MAIN,
    MAIN_SCREEN_TITLE,
    MAIN_SCREEN_SUBTITLE,
    MAIN_SCREEN_LOGOUT_BUTTON,
    MAIN_SCREEN_WELCOME_DESC,
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
        AUTH_SUBTITLE -> "Sistemul de Alertă și Protecție Civilă"
        AUTH_LOGIN_BUTTON -> "Autentificare"
        AUTH_PROTECTED_BY -> "Datele tale de conectare sunt procesate în siguranță prin serviciul Auth0."
        AUTH_TERMS -> "Termeni și Condiții"
        AUTH_PRIVACY -> "Confidențialitate"
        AUTH_LOGOUT -> "Deconectare"
        AUTH_SUCCESS_BADGE -> "Autentificat cu succes"
        AUTH_DEV_SHORTCUT_MAIN -> "Comandă Rapidă: Ecran Principal"
        MAIN_SCREEN_TITLE -> "Ecran Principal"
        MAIN_SCREEN_SUBTITLE -> "Panou de Control CivilShield"
        MAIN_SCREEN_LOGOUT_BUTTON -> "Deconectare"
        MAIN_SCREEN_WELCOME_DESC -> "Bine ați venit în panoul principal CivilShield. Acest ecran este modularizat și conectat prin shared navigation."
        LANG_RO -> "RO"
        LANG_EN -> "EN"
        LANG_HU -> "HU"
    }
}

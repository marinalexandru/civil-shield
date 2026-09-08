package com.civil.shield.auth

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class IdTokenParserTest {

    @Test
    fun testParseValidIdToken() {
        // Real Auth0 ID token issued for google-oauth2 connection
        val idToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IkRsYS10aDR1TmJVS3RoR3ZwMlBHMCJ9." +
                "eyJnaXZlbl9uYW1lIjoiYW5kdSIsImZhbWlseV9uYW1lIjoibWFyaW4iLCJuaWNrbmFtZSI6Im1hcmluLmFsZXhh" +
                "bmRydS5hbmR1IiwibmFtZSI6ImFuZHUgbWFyaW4iLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNv" +
                "bnRlbnQuY29tL2EvQUNnOG9jS0IwX19ZRllBTHdtTjRSRmJHd09oTlF0UjBJOHNGbEE0SWZRSmRpS2YwX3h4UnFq" +
                "Tl89czk2LWMiLCJ1cGRhdGVkX2F0IjoiMjAyNi0wOS0wOFQyMDoyODowMi4wNjFaIiwiZW1haWwiOiJtYXJpbi5h" +
                "bGV4YW5kcnUuYW5kdUBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiaXNzIjoiaHR0cHM6Ly9jaXZp" +
                "bC1zaGllbGQuZXUuYXV0aDAuY29tLyIsImF1ZCI6Ijl5R1JUNFg3U2hMaGl0SU44ZzJZOEk5WnBGMTc3TFprIiwi" +
                "c3ViIjoiZ29vZ2xlLW9hdXRoMnwxMDIwMTAxNzcxMTMzMjMzMjQ5NDUiLCJpYXQiOjE3ODg4OTkyODUsImV4cCI6" +
                "MTc4ODkzNTI4NSwic2lkIjoiVGkzcVVWWmYxT2FKWGM0eExUM3JDc1g5MWpxTzJ0RFkifQ.u6cXd-xfBBBm7u-O" +
                "MDz0OzZVeFe7yH_-jNuPYPfkyt_LK7Ww7FTOoNf7nsfzrWsST35phVPEdjeebf_dPBtLBq4zlOzjiD2H7A_yTn8c" +
                "_LSDNqYZ34sWff1fs1FdyuTG9VkOTnC6ljOgHpBSGOFBSutjR9Zj7BO_KtFWt1nnYEzseGAuI4KejcYNWXs3Fm1E" +
                "6xjjb0utpgAZDyMHGMziELB6tKEZkgH8OlP_1BPSrjJQeeN8mrkx4eaIAz3mgCpGxq_G_yJLLa6urkSTglK0YRGI" +
                "fvU0HT6TaSUjJC-5k0mJ3ZBOKhoXb2vYOCcoewaSCNq0avVQsJNgrhznbCgP5g"

        val profile = IdTokenParser.parse(idToken)

        assertNotNull(profile)
        assertEquals("google-oauth2|102010177113323324945", profile.userId)
        assertEquals("andu marin", profile.name)
        assertEquals("marin.alexandru.andu@gmail.com", profile.email)
        assertEquals(
            "https://lh3.googleusercontent.com/a/ACg8ocKB0__YFYALwmN4RFbGwOhNQtR0I8sFlA4IfQJdiKf0_xxRqjN_=s96-c",
            profile.pictureUrl
        )
        assertTrue(profile.isEmailVerified)
    }

    @Test
    fun testParseInvalidTokens() {
        assertNull(IdTokenParser.parse(""))
        assertNull(IdTokenParser.parse("single_part_token"))
        assertNull(IdTokenParser.parse("header.not_valid_base64_json!@#.signature"))
    }
}

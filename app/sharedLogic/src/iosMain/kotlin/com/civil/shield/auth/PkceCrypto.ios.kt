package com.civil.shield.auth

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.pin
import kotlinx.cinterop.usePinned
import platform.CoreCrypto.CC_SHA256
import platform.CoreCrypto.CC_SHA256_DIGEST_LENGTH
import platform.Foundation.NSData
import platform.Foundation.base64EncodedStringWithOptions
import platform.Foundation.create
import platform.Security.SecRandomCopyBytes
import platform.Security.kSecRandomDefault
import platform.posix.size_t

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual object PkceCrypto {

    actual fun generateCodeVerifier(): String {
        val bytes = ByteArray(32)
        bytes.usePinned { pinned ->
            SecRandomCopyBytes(kSecRandomDefault, 32.toULong(), pinned.addressOf(0))
        }
        return base64UrlEncode(bytes)
    }

    actual fun generateCodeChallenge(verifier: String): String {
        val bytes = verifier.encodeToByteArray()
        val digest = UByteArray(CC_SHA256_DIGEST_LENGTH)

        bytes.usePinned { pinnedBytes ->
            digest.usePinned { pinnedDigest ->
                CC_SHA256(
                    pinnedBytes.addressOf(0),
                    bytes.size.toUInt(),
                    pinnedDigest.addressOf(0)
                )
            }
        }
        return base64UrlEncode(digest.asByteArray())
    }

    actual fun generateState(): String {
        val bytes = ByteArray(16)
        bytes.usePinned { pinned ->
            SecRandomCopyBytes(kSecRandomDefault, 16.toULong(), pinned.addressOf(0))
        }
        return base64UrlEncode(bytes)
    }

    private fun base64UrlEncode(bytes: ByteArray): String {
        val nsData = bytes.usePinned { pinned ->
            NSData.create(bytes = pinned.addressOf(0), length = bytes.size.toULong())
        }
        val base64 = nsData.base64EncodedStringWithOptions(0u)
        return base64
            .replace("+", "-")
            .replace("/", "_")
            .trimEnd('=')
    }

    private fun UByteArray.asByteArray(): ByteArray =
        ByteArray(size) { this[it].toByte() }
}

@file:Suppress("UNUSED")

package com.appnew.basecode.common

sealed interface ExceptionCause

data object Unauthorized : ExceptionCause

data object Unknown : ExceptionCause
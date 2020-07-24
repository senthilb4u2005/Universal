package com.ps.universal.viewmodel

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false

    fun getValueIfNotHandled() = if (hasBeenHandled)
        null
    else {
        hasBeenHandled = true
        content
    }

    fun peekContent(): T = content


}
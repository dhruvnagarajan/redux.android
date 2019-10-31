package com.dhruvnagarajan.android.pubsub.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * @author Dhruvaraj Nagarajan
 */
inline fun <reified T> String.fromJsonInline(): T = Gson().fromJson(this, T::class.java)

fun <T> String.fromJson(): T {
    val tt: Type = object : TypeToken<T>() {}.type
    return Gson().fromJson(this, tt)
}

fun <T> String.readValue(): T {
    val tt = object : TypeToken<T>() {}.type
    val mapper = ObjectMapper()
    return mapper.readValue(this, object : TypeReference<T>() {})
}

fun Any.toJson(): String = Gson().toJson(this)
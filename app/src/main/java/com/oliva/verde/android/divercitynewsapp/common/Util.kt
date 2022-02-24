package com.oliva.verde.android.divercitynewsapp.common

object Util {
    fun replaceSlashToHyphen(string: String) : String {
        return string.replace(Regex("/"), "-")
    }
    fun replaceHyphenToSlash(string: String) : String {
        return string.replace(Regex("-"), "/")
    }
}
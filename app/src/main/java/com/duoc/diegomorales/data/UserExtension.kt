package com.duoc.diegomorales.data

fun String.isGmail(): Boolean = this.lowercase().endsWith("@gmail.com")
fun String.isDuoc(): Boolean = this.lowercase().endsWith("@duocuc.cl")

val String.displayName: String
    get() = substringBefore("@")

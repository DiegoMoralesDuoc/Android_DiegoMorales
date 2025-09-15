package com.duoc.diegomorales.data

fun String.isGmail(): Boolean = this.endsWith("@gmail.com")
fun String.isDuoc(): Boolean = this.endsWith("@duocuc.cl")

val String.displayName: String
    get() = substringBefore("@")

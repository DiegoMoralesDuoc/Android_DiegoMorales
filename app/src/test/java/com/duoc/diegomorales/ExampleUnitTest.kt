package com.duoc.diegomorales

import org.junit.Test
import org.junit.Assert.*
import com.duoc.diegomorales.data.isGmail
import com.duoc.diegomorales.data.isDuoc

class ExampleUnitTest {

    @Test
    fun user_isGmail_or_Duoc() {
        val gmailUser = "usuario@gmail.com"
        val duocUser = "alumno@duocuc.cl"

        assertTrue(gmailUser.isGmail())
        assertFalse(gmailUser.isDuoc())

        assertTrue(duocUser.isDuoc())
        assertFalse(duocUser.isGmail())
    }
}

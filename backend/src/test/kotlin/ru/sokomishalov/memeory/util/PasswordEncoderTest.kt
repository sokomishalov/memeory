package ru.sokomishalov.memeory.util

import junit.framework.Assert.assertTrue
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import ru.sokomishalov.memeory.AbstractSpringMemeoryTest


/**
 * @author sokomishalov
 */

class PasswordEncoderTest : AbstractSpringMemeoryTest() {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun generatePassword() {
        val password = "admin";
        val encodedPassword = passwordEncoder.encode(password)

        println("Encoded password: $encodedPassword")

        assertTrue(passwordEncoder.matches(password, encodedPassword))
    }
}

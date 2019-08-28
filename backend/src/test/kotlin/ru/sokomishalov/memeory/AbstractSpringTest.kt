package ru.sokomishalov.memeory

import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


/**
 * @author sokomishalov
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
abstract class AbstractSpringTest : AbstractTest()

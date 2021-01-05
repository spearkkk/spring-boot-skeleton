package com.spearkkk.skeleton.web

import com.spearkkk.skeleton.config.auth.SecurityConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

@WebMvcTest(controllers = HelloController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class))
class HelloControllerTest extends Specification {
    @Autowired
    private MockMvc mockMvc

    @WithMockUser(roles = "USER")
    def "HelloController should return 'hello, dude!' for '/hello'."() {
        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/hello"))

        then:
        result.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("hello, dude!"))
    }
}
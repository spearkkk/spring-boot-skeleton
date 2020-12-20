package com.spearkkk.skeleton.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IndexControllerTest extends Specification {
  @Autowired
  private TestRestTemplate restTemplate

  def "IndexController should be return html which has 'Hello'."() {
    expect:
    restTemplate.getForObject("/", String.class).contains("Hello")
  }
}

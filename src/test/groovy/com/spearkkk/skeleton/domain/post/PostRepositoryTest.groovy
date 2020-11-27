package com.spearkkk.skeleton.domain.post

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest
class PostRepositoryTest extends Specification {
  @Autowired
  private PostRepository postRepository

  def cleanup() {
    postRepository.deleteAll()
  }

  def "PostRepository should find all post from database."() {
    given:
    def title = "POST_SAMPLE_TITLE"
    def content = "POST_SAMPLE_CONTENT"
    def author = "POST_SAMPLE_AUTHOR"
    def post = Post.builder()
        .title(title)
        .content(content)
        .author(author)
        .build()

    def stamp = LocalDateTime.now()
    postRepository.save(post)

    when:
    def result = postRepository.findAll()

    then:
    !result.isEmpty()
    result.size() == 1
    def foundPost = result.stream()
        .findFirst().orElseThrow({ new IllegalStateException("PostRepositoryTest") })
    foundPost.title == "POST_SAMPLE_TITLE"
    foundPost.content == "POST_SAMPLE_CONTENT"
    foundPost.author == "POST_SAMPLE_AUTHOR"
    foundPost.getCreatedDatetime().isAfter(stamp)
    foundPost.getModifiedDatetime().isAfter(stamp)
  }
}

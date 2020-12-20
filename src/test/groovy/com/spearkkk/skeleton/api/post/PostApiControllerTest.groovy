package com.spearkkk.skeleton.api.post

import com.spearkkk.skeleton.domain.post.Post
import com.spearkkk.skeleton.domain.post.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = ["org.eclipse.jetty.client.HttpClient=true"])
class PostApiControllerTest extends Specification {
  @LocalServerPort
  private int port
  @Autowired
  private TestRestTemplate restTemplate
  @Autowired
  private PostRepository postRepository

  def cleanup() {
    postRepository.deleteAll()
  }

  def "PostApiController should save post into database."() {
    given:
    def title = "POST_SAMPLE_TITLE"
    def content = "POST_SAMPLE_CONTENT"
    def dto = PostSaveRequestDto.builder()
        .title(title)
        .content(content)
        .build()

    def url = "http://localhost:$port/api/posts"

    when:
    def result = restTemplate.postForEntity(url, dto, Long)

    then:
    result.getStatusCode() == HttpStatus.OK
    result.getBody() >= 0L

    def foundPosts = postRepository.findAll()
    !foundPosts.isEmpty()
    foundPosts.size() == 1
    def foundPost = foundPosts.stream()
                          .findFirst().orElseThrow({ new IllegalStateException("PostRepositoryTest") })
    foundPost.title == "POST_SAMPLE_TITLE"
    foundPost.content == "POST_SAMPLE_CONTENT"
    foundPost.author == "anonymous"
  }

  def "PostApiController should update post which is from database."() {
    given:
    def title = "POST_SAMPLE_TITLE"
    def content = "POST_SAMPLE_CONTENT"
    def author = "POST_SAMPLE_AUTHOR"
    def existingPost = Post.builder()
        .title(title)
        .content(content)
        .author(author)
        .build()
    def savedPost = postRepository.save(existingPost)

    def url = "http://localhost:$port/api/posts/${savedPost.getId()}"

    def dto = PostUpdateRequestDto.builder()
                                .title("POST_UPDATE_TITLE")
                                .content("POST_UPDATE_CONTENT")
                                .build()

    def requestBody = new HttpEntity<>(dto)

    when:
    def result = restTemplate.exchange(url, HttpMethod.PUT, requestBody, Long.class)

    then:
    result.getStatusCode() == HttpStatus.OK
    result.getBody() >= 0L

    def foundPosts = postRepository.findAll()
    !foundPosts.isEmpty()
    foundPosts.size() == 1
    def foundPost = foundPosts.stream()
                              .findFirst().orElseThrow({ new IllegalStateException("PostRepositoryTest") })
    foundPost.title == "POST_UPDATE_TITLE"
    foundPost.content == "POST_UPDATE_CONTENT"
    foundPost.author == "POST_SAMPLE_AUTHOR"
  }

  def "PostApiController should delete post which is from database."() {
    given:
    def title = "POST_SAMPLE_TITLE"
    def content = "POST_SAMPLE_CONTENT"
    def author = "POST_SAMPLE_AUTHOR"
    def existingPost = Post.builder()
                           .title(title)
                           .content(content)
                           .author(author)
                           .build()
    def savedPost = postRepository.save(existingPost)

    def url = "http://localhost:$port/api/posts/${savedPost.getId()}"

    when:
    def result = restTemplate.exchange(url, HttpMethod.DELETE, null, Long.class)

    then:
    result.getStatusCode() == HttpStatus.OK
    result.getBody() >= 0L

    def foundPosts = postRepository.findAll()
    foundPosts.isEmpty()
  }
}

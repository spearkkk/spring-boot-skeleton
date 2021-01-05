package com.spearkkk.skeleton.api.post

import com.fasterxml.jackson.databind.ObjectMapper
import com.spearkkk.skeleton.domain.post.Post
import com.spearkkk.skeleton.domain.post.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = ["org.eclipse.jetty.client.HttpClient=true"])
class PostApiControllerTest extends Specification {
  @LocalServerPort
  private int port
  @Autowired
  private TestRestTemplate restTemplate
  @Autowired
  private PostRepository postRepository
  @Autowired
  private WebApplicationContext webApplicationContext;
  private MockMvc mockMvc

  def setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(sharedHttpSession()).build()
  }

  def cleanup() {
    postRepository.deleteAll()
  }

  @WithMockUser(roles = "USER")
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
    def result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto)))
        .andExpect(status().isOk())

    then:
    result

    def foundPosts = postRepository.findAll()
    !foundPosts.isEmpty()
    foundPosts.size() == 1
    def foundPost = foundPosts.stream()
                          .findFirst().orElseThrow({ new IllegalStateException("PostRepositoryTest") })
    foundPost.title == "POST_SAMPLE_TITLE"
    foundPost.content == "POST_SAMPLE_CONTENT"
    foundPost.author == "anonymous"
  }

  @WithMockUser(roles = "USER")
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

    when:
    def result = mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto)))
                        .andExpect(status().isOk())

    then:
    result

    def foundPosts = postRepository.findAll()
    !foundPosts.isEmpty()
    foundPosts.size() == 1
    def foundPost = foundPosts.stream()
                              .findFirst().orElseThrow({ new IllegalStateException("PostRepositoryTest") })
    foundPost.title == "POST_UPDATE_TITLE"
    foundPost.content == "POST_UPDATE_CONTENT"
    foundPost.author == "POST_SAMPLE_AUTHOR"
  }

  @WithMockUser(roles = "USER")
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
    def result = mockMvc.perform(delete(url))
                        .andExpect(status().isOk())

    then:
    result

    def foundPosts = postRepository.findAll()
    foundPosts.isEmpty()
  }
}

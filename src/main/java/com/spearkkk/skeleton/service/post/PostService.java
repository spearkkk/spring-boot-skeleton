package com.spearkkk.skeleton.service.post;

import com.spearkkk.skeleton.api.post.PostResponseDto;
import com.spearkkk.skeleton.api.post.PostSaveRequestDto;
import com.spearkkk.skeleton.api.post.PostUpdateRequestDto;
import com.spearkkk.skeleton.domain.post.Post;
import com.spearkkk.skeleton.domain.post.PostRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
  private final PostRepository postRepository;

  @Transactional
  public Long save(final PostSaveRequestDto postSaveRequestDto) {
    return postRepository.save(postSaveRequestDto.toEntity()).getId();
  }

  @Transactional
  public Long updateBy(final Long id, final PostUpdateRequestDto postUpdateRequestDto) {
    Optional<Post> post = postRepository.findById(id);
    if (post.isPresent()) {
      post.get().update(postUpdateRequestDto.getTitle(), postUpdateRequestDto.getContent());
      return post.get().getId();
    }
    log.warn("There is no post from database. id: {}", id);
    throw new IllegalStateException("Cannot find post from database.");
  }

  public PostResponseDto findBy(final Long id) {
    Optional<Post> post = postRepository.findById(id);
    if (post.isPresent()) {
      Post foundPost = post.get();
      return PostResponseDto.builder()
                            .id(foundPost.getId())
                            .title(foundPost.getTitle())
                            .content(foundPost.getContent())
                            .author(foundPost.getAuthor())
                            .build();
    }
    log.warn("There is no post from database. id: {}", id);
    throw new IllegalStateException("Cannot find post from database.");
  }

  @Transactional(readOnly = true)
  public List<PostResponseDto> findAllDesc() {
    List<PostResponseDto> dtos = new ArrayList<>();

    for (Post foundPost: postRepository.findAllDesc()) {
      dtos.add(PostResponseDto.builder()
                              .id(foundPost.getId())
                              .title(foundPost.getTitle())
                              .content(foundPost.getContent())
                              .author(foundPost.getAuthor())
                              .createdDatetime(foundPost.getCreatedDatetime())
                              .modifiedDatetime(foundPost.getModifiedDatetime())
                              .build());
    }
    log.info("count of post: {}", dtos.size());
    return dtos;
  }

  @Transactional
  public void delete(final Long id) {
    Optional<Post> post = postRepository.findById(id);
    if (post.isPresent()) {
      Post foundPost = post.get();
      postRepository.delete(foundPost);
      return;
    }
    log.warn("There is no post from database. id: {}", id);
    throw new IllegalStateException("Cannot find post from database.");
  }
}

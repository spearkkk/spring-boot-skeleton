package com.spearkkk.skeleton.api.post;

import com.spearkkk.skeleton.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@Getter
public class PostSaveRequestDto {
  private final String title;
  private final String content;
  private final String author;

  @Builder
  private PostSaveRequestDto(String title, String content, String author) {
    if (title == null || content == null) {
      log.warn("The `title` and `content` should be not null. title: {}, content: {}", title, content);
      throw new IllegalStateException("Cannot build PostSaveRequestDto object.");
    }
    this.title = title;
    this.content = content;
    this.author = author;
  }

  public Post toEntity() {
    if (StringUtils.isBlank(author)) {
      return Post.builder()
          .title(title)
          .content(content)
          .author("anonymous")
          .build();
    }
    return Post.builder()
               .title(title)
               .content(content)
               .author(author)
               .build();
  }
}

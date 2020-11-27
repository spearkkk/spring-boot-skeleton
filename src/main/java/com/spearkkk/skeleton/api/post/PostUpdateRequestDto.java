package com.spearkkk.skeleton.api.post;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class PostUpdateRequestDto {
  private final String title;
  private final String content;

  @Builder
  private PostUpdateRequestDto(String title, String content) {
    if (title == null || content == null) {
      log.warn("The `title` and `content` should be not null. title: {}, content: {}", title, content);
      throw new IllegalStateException("Cannot build PostSaveRequestDto object.");
    }
    this.title = title;
    this.content = content;
  }
}

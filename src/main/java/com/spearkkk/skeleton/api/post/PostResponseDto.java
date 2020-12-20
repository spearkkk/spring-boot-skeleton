package com.spearkkk.skeleton.api.post;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class PostResponseDto {
  private final Long id;
  private final String title;
  private final String content;
  private final String author;
  private final LocalDateTime createdDatetime;
  private final LocalDateTime modifiedDatetime;

  @Builder
  private PostResponseDto(Long id, String title, String content, String author, LocalDateTime createdDatetime,
                          LocalDateTime modifiedDatetime) {

    if (id == null || title == null || content == null || author == null) {
      log.warn("The `id`, `title`, `content`, and `author` should be not null. id: {}, title: {}, content: {}, author: {}",
               id, title, content, author);
      throw new IllegalStateException("Cannot build PostResponseDto object.");
    }
    this.id = id;
    this.title = title;
    this.content = content;
    this.author = author;
    this.createdDatetime = createdDatetime;
    this.modifiedDatetime = modifiedDatetime;
  }
}

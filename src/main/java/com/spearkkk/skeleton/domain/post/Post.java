package com.spearkkk.skeleton.domain.post;

import com.spearkkk.skeleton.domain.BaseDatetime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseDatetime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(length = 500, nullable = false)
  private String title;
  @Column(columnDefinition = "TEXT", nullable = false)
  private String content;
  private String author;

  @Builder
  private Post(String title, String content, String author) {
    if (title == null || content == null) {
      log.warn("The `title` and `content` should be not null. title: {}, content: {}", title, content);
      throw new IllegalStateException("Cannot build Post object.");
    }
    this.title = title;
    this.content = content;
    this.author = author;
  }

  public void update(final String title, final String content) {
    this.title = title;
    this.content = content;
  }
}

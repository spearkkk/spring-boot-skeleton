package com.spearkkk.skeleton.api.post;

import com.spearkkk.skeleton.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostApiController {
  private final PostService postService;

  @PostMapping("/api/posts")
  public Long save(@RequestBody PostSaveRequestDto requestDto) {
    log.debug("request dto: {}", requestDto);
    return postService.save(requestDto);
  }

  @PutMapping("/api/posts/{id}")
  public Long update(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto) {
    return postService.updateBy(id, requestDto);
  }

  @GetMapping("/api/posts/{id}")
  public PostResponseDto findBy(@PathVariable Long id) {
    return postService.findBy(id);
  }
}

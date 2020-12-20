package com.spearkkk.skeleton.web;

import com.spearkkk.skeleton.api.post.PostResponseDto;
import com.spearkkk.skeleton.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {
  private final PostService postService;

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("posts", postService.findAllDesc());
    return "index";
  }

  @GetMapping("/posts/save")
  public String postSave() {
    return "post-save";
  }

  @GetMapping("/posts/update/{id}")
  public String postUpdate(@PathVariable Long id, Model model) {
    PostResponseDto dto = postService.findBy(id);
    model.addAttribute("post", dto);

    return "post-update";
  }
}

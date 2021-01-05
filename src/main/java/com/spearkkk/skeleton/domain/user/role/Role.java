package com.spearkkk.skeleton.domain.user.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum  Role {
  GUEST("ROLE_GUEST", "Guest"),
  USER("ROLE_USER", "User");

  private final String key;
  private final String title;
}

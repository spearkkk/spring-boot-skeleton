package com.spearkkk.skeleton.config.auth.dto;

import com.spearkkk.skeleton.domain.user.User;
import com.spearkkk.skeleton.domain.user.role.Role;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttribute {
  private Map<String, Object> attributes;
  private String nameAttributeKey;
  private String name;
  private String email;
  private String picture;

  @Builder(access = AccessLevel.PRIVATE)
  private OAuthAttribute(final Map<String, Object> attributes, final String nameAttributeKey, final String name,
                        final String email, final String picture) {

    this.attributes = attributes;
    this.nameAttributeKey = nameAttributeKey;
    this.name = name;
    this.email = email;
    this.picture = picture;
  }

  public static OAuthAttribute of(final String registrationId, final String userNameAttributeName, final Map<String, Object> attributes) {
    if ("naver".equals(registrationId)) {
      return ofNaver("id", attributes);
    }
    return OAuthAttribute.ofGoogle(userNameAttributeName, attributes);
  }

  private static OAuthAttribute ofNaver(String nameAttributeKey, Map<String, Object> attributes) {
    Map<String, Object> response = (Map<String, Object>) attributes.get("response");

    return OAuthAttribute.builder()
                         .nameAttributeKey(nameAttributeKey)
                         .name((String)response.get("name"))
                         .email((String)response.get("email"))
                         .picture((String)response.get("profile_image"))
                         .attributes(response)
                         .build();
  }

  private static OAuthAttribute ofGoogle(String nameAttributeKey, Map<String, Object> attributes) {
    return OAuthAttribute.builder()
                         .nameAttributeKey(nameAttributeKey)
                         .name((String)attributes.get("name"))
                         .email((String)attributes.get("email"))
                         .picture((String)attributes.get("picture"))
                         .attributes(attributes)
                         .build();
  }

  public User toEntity() {
    return User.builder()
               .name(name)
               .email(email)
               .picture(picture)
               .role(Role.GUEST)
               .build();
  }
}

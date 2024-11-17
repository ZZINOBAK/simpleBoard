package com.simpleboard.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Post {

    private Long id;
    private String title;
    private String content;

    public Post() {
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

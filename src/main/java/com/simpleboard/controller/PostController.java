package com.simpleboard.controller;

import com.simpleboard.domain.Post;
import com.simpleboard.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //게시글 생성 폼
    @GetMapping("/new")
    public String getCreateForm() {
        return "new-form";
    }

    //게시글 생성
    @PostMapping
    public String createPost(@RequestParam("title") String title,
                             @RequestParam("content") String content, Model model) {
        Post post = postService.save(title, content);
        model.addAttribute("post", post);
        return "post";
    }

    //게시글 전체 조회
    @GetMapping
    public String findAllPost(Model model) {
        List<Post> posts = postService.findAllPost();
        model.addAttribute("posts", posts);
        return "posts";
    }

    //게시글 한개 조회
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "post";
    }

    //게시글 수정 폼
    @GetMapping("/{id}/edit")
    public String getEditForm(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "edit-form";
    }


    //게시글 수정
    @PostMapping("/{id}")
    public String editPost(@RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestParam(required = false) Long id,
                           Model model) {
        Post post = postService.updatePost(title, content, id);
        model.addAttribute("post", post);
        return "post";
    }

    //게시글 삭제
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }
}

package com.simpleboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.simpleboard.domain.Post;
import com.simpleboard.repository.PostRepository;

import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {
    private final PostRepository postRepository;

    @Autowired
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/{id}")
    public String post(@PathVariable Long id, Model model) {
        Post post = postRepository.findById(id);  // ID로 게시글을 찾음
        model.addAttribute("post", post);
        return "post";
    }

    @GetMapping("/new")
    public String newPost() {
        return "post-form";
    }

    @RequestMapping(value = {"/save", "/edit/save"}, method = RequestMethod.POST)
    public String save(@RequestParam("title") String title, @RequestParam("content") String content,
                       @RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            Post post = new Post(title, content);
            postRepository.save(post);
            model.addAttribute("post", post);
            return "post";
        } else {
            Post post = postRepository.findById(id);  // 수정할 게시글 찾기
            if (post != null) {
                post.setTitle(title);   // 제목 수정
                post.setContent(content);  // 내용 수정
                postRepository.updatePost(post);  // 수정된 게시글 저장
                model.addAttribute("post", post);
                return "post";  // 수정 후 게시글 목록으로 리다이렉트
            }
            return "error";  // 기본 에러 처리
        }
    }

    @GetMapping("/list")
    public String posts(Model model) {
        List<Post> posts = postRepository.findAllPost();
        model.addAttribute("posts", posts);
        return "posts";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Post post = postRepository.findById(id);  // ID로 게시글을 찾음
        if (post != null) {
            model.addAttribute("post", post);  // 수정할 게시글 데이터를 모델에 추가
            return "edit-form";  // 수정 폼을 보여주는 뷰
        }
        return "redirect:/post/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        postRepository.delete(id);  // ID로 게시글을 찾음
        return "redirect:/post/list";
    }


}

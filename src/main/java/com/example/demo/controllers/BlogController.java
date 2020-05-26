package com.example.demo.controllers;

import com.example.demo.models.Blog;
import com.example.demo.repositories.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class BlogController {

    @Autowired
    BlogRepository blogRepository;

    @GetMapping("/blog")
    public List<Blog> index() {
        return blogRepository.findAll();
    }

    @GetMapping("/blog/{id}")
    public Optional<Blog> show(@PathVariable String id) {
        int blogId = Integer.parseInt(id);
        return blogRepository.findById(blogId);
    }

    @PostMapping("/blog/search")
    public List<Blog> search(@RequestBody Map<String, String> body) {
        String text = body.get("text");
        return blogRepository.findByTitleContainingOrContentContaining(text, text);
    }

    @PostMapping("/blog")
    public Blog create(@RequestBody Map<String, String> body) {
        int id = Integer.parseInt(body.get("id"));
        String title = body.get("title");
        String content = body.get("content");
        return blogRepository.save(new Blog(title, content));
    }

    @PutMapping("/blog/{id}")
    public Blog update(@PathVariable String id, @RequestBody Map<String, String> body) {
        int blogId = Integer.parseInt(id);
        String title = body.get("title");
        String content = body.get("content");

        // Find and update the blog.
        Blog blog = blogRepository.getOne(blogId);
        blog.setTitle(title);
        blog.setContent(content);
        return blogRepository.save(blog);
    }

    @DeleteMapping("/blog/{id}")
    public boolean delete(@PathVariable String id) {
        int blogId = Integer.parseInt(id);
        blogRepository.deleteById(blogId);
        return true;
    }
}

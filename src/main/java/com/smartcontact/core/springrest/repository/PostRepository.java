package com.smartcontact.core.springrest.repository;

import com.smartcontact.core.springrest.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

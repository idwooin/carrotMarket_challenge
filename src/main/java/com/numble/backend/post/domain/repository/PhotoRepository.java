package com.numble.backend.post.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.backend.post.domain.Photo;

public interface PhotoRepository extends JpaRepository<Photo, String> {
}

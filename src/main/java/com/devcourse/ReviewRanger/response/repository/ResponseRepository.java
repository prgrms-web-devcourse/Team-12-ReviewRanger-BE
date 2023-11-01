package com.devcourse.ReviewRanger.response.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.response.domain.Response;

public interface ResponseRepository extends JpaRepository<Response, Long> {
}

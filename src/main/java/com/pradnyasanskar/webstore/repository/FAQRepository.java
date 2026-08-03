package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FAQRepository extends JpaRepository<FAQ, Long> {

    // ============================================================
    // Get All Active FAQs
    // ============================================================

    List<FAQ> findByIsActiveTrue();

    // ============================================================
    // Get FAQs Ordered By Display Order
    // ============================================================

    List<FAQ> findAllByOrderByDisplayOrderAsc();

    // ============================================================
    // Search FAQ By Question
    // ============================================================

    List<FAQ> findByQuestionContainingIgnoreCase(String keyword);

}
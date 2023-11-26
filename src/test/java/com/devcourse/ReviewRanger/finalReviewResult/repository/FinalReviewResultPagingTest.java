package com.devcourse.ReviewRanger.finalReviewResult.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.devcourse.ReviewRanger.common.config.QueryDslConfig;
import com.devcourse.ReviewRanger.finalReviewResult.dto.FinalReviewResultListResponse;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QueryDslConfig.class)
@DataJpaTest
class FinalReviewResultPagingTest {

	@Autowired
	private FinalReviewResultRepository finalReviewResultRepository;

	@Test
	void 최종결과_최신순_페이징_성공() {
		// given
		Pageable pageable = PageRequest.of(0, 12);
		Long userId = 2L;
		Long cursorId = null;

		// when
		Slice<FinalReviewResultListResponse> allFinalReviewResults
			= finalReviewResultRepository.findAllFinalReviewResults(cursorId, userId, pageable);
		System.out.println("size: " + allFinalReviewResults.getNumberOfElements());

		// then
		assertEquals(12, allFinalReviewResults.getSize());
		assertEquals(19, allFinalReviewResults.getContent().get(0).id());
		assertEquals(18, allFinalReviewResults.getContent().get(1).id());
		assertEquals(10, allFinalReviewResults.getContent().get(9).id());
		assertTrue(allFinalReviewResults.hasNext());
	}
}

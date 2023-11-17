package com.devcourse.ReviewRanger.finalReviewResult.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devcourse.ReviewRanger.finalReviewResult.dto.FinalReviewResultListResponse;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class FinalReviewResultPagingTest {

	@Autowired
	private FinalReviewResultRepository finalReviewResultRepository;

	@Test
	void 커서페이징_성공() {
		// given
		PageRequest pageRequest = PageRequest.of(0, 12);

		// when
		Slice<FinalReviewResultListResponse> finalReviewResultResponses
			= finalReviewResultRepository.findAllFinalReviewResults(0L, 3L, pageRequest);

		// then
		assertEquals(12, finalReviewResultResponses.getSize());
		assertEquals(4, finalReviewResultResponses.getNumberOfElements());
		assertEquals(4, finalReviewResultResponses.getContent().get(0).id());
		assertEquals(5, finalReviewResultResponses.getContent().get(1).id());
		assertFalse(finalReviewResultResponses.hasNext());
	}
}

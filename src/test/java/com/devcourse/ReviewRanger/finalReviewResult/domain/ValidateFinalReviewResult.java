package com.devcourse.ReviewRanger.finalReviewResult.domain;

import static org.junit.jupiter.api.Assertions.*;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.devcourse.ReviewRanger.common.config.QueryDslConfig;
import com.devcourse.ReviewRanger.finalReviewResult.repository.FinalReviewResultRepository;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QueryDslConfig.class)
@DataJpaTest
class ValidateFinalReviewResult {

	@Autowired
	private FinalReviewResultRepository finalReviewResultRepository;

	@Test
	@DisplayName("최종 리뷰 결과 필드엔 빈 값이 들어갈 수 없다.")
	void notValidateFinalReviewResult() {
		// given
		FinalReviewResult finalReviewResult = new FinalReviewResult(null, " ", null, null, "설명은 null이어도 됨");

		// when & then
		Assertions.assertThrows(ConstraintViolationException.class,
			() -> finalReviewResultRepository.save(finalReviewResult));
	}

	@Test
	@DisplayName("최종 리뷰 결과 필드에 유효한 필드 값을 넣을 수 있다.")
	void validateFinalReviewResult() {
		// given
		FinalReviewResult finalReviewResult = new FinalReviewResult(
			1L,
			"장수연",
			1L,
			"1차 피어리뷰 입니다.",
			"설명은 null이어도 됨"
		);

		// when
		FinalReviewResult saveFinalReviewResult = finalReviewResultRepository.save(finalReviewResult);

		// then
		assertNotNull(saveFinalReviewResult);
		assertEquals(finalReviewResult.getId(), saveFinalReviewResult.getId());
		assertEquals(finalReviewResult.getUserId(), saveFinalReviewResult.getUserId());
		assertEquals(finalReviewResult.getReviewId(), saveFinalReviewResult.getReviewId());
		assertEquals(finalReviewResult.getTitle(), saveFinalReviewResult.getTitle());
		assertEquals(finalReviewResult.getDescription(), saveFinalReviewResult.getDescription());
	}
}

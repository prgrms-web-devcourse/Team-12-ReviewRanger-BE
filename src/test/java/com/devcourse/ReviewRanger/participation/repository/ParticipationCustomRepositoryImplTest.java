package com.devcourse.ReviewRanger.participation.repository;

import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import com.devcourse.ReviewRanger.participation.domain.Participation;
import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.repository.UserRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ParticipationCustomRepositoryImplTest {

	@Autowired
	private ParticipationRepository participationRepository;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void setUp() {
		User responser = userRepository.save(new User("juwoong", "wwong7247@gmail.com", "1234"));

		List<Participation> participations = LongStream.range(1, 10).mapToObj(reviewId -> {
			Participation participation = new Participation(responser);
			participation.assignReviewId(reviewId);
			return participation;
		}).toList();

		participationRepository.saveAll(participations);
	}

	@Test
	@DisplayName("커서 페이징 커리 첫 조회 시 cursorId 값이 null일때 성공")
	public void fwhenCursorIdIsNullOnInitialCursorPaging_thenSucceed() {
		PageRequest pageRequest = PageRequest.of(0, 3);
		Slice<Participation> results = participationRepository.findByResponserId(null, 1L, pageRequest);

		Assertions.assertEquals(3, results.getContent().size());
		Assertions.assertEquals(9, results.getContent().get(0).getId());
		Assertions.assertEquals(7, results.getContent().get(results.getContent().size() - 1).getId());
	}

	@Test
	@DisplayName("커서 페이징 커리 첫 번재가 아닌 조회 시 cursorId 값이 null이 아닐 때 성공")
	public void whenCursorIdIsNotNullOnInitialCursorPaging_thenSucceed() {
		PageRequest pageRequest = PageRequest.of(0, 3);
		Slice<Participation> results = participationRepository.findByResponserId(7L, 1L, pageRequest);

		Assertions.assertEquals(3, results.getContent().size());
		Assertions.assertEquals(6, results.getContent().get(0).getId());
		Assertions.assertEquals(4, results.getContent().get(results.getContent().size() - 1).getId());
	}
}

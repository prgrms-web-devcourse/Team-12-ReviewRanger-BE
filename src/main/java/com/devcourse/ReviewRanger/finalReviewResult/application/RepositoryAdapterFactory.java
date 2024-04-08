package com.devcourse.ReviewRanger.finalReviewResult.application;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestionType;
import com.devcourse.ReviewRanger.finalReviewResult.domain.agent.RepositoryAdapter;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class RepositoryAdapterFactory {
	private final Map<FinalQuestionType, RepositoryAdapter> repositoryAdapters = new HashMap<>();

	public RepositoryAdapterFactory(Set<RepositoryAdapter> repositoryAdapterSet) {
		repositoryAdapterSet.forEach(
			repositoryAdapter -> repositoryAdapters.put(repositoryAdapter.finalQuestionType, repositoryAdapter));
	}

	public RepositoryAdapter from(FinalQuestionType finalQuestionType) {
		return repositoryAdapters.get(finalQuestionType);
	}
}
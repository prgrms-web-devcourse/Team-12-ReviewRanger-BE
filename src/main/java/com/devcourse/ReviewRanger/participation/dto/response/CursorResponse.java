package com.devcourse.ReviewRanger.participation.dto.response;

import java.util.List;

public record CursorResponse<T>(
	List<T> contents,
	boolean hasNext,
	Long nextCursor
){
}

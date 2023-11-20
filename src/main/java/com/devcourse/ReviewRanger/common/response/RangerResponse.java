package com.devcourse.ReviewRanger.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;

@Getter
@JsonPropertyOrder({"success", "data"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RangerResponse<T> {

	private final Boolean success;
	private final T data;

	public RangerResponse(Boolean success, T data) {
		this.success = success;
		this.data = data;
	}

	public static RangerResponse<Void> ok(Boolean success) {
		return new RangerResponse<>(success, null);
	}

	public static <T> RangerResponse<T> ok(T data) {
		return new RangerResponse<>(true, data);
	}

	public static <T> RangerResponse<T> ok(Boolean success, T data) {
		return new RangerResponse<>(success, data);
	}

	public static RangerResponse<Void> noData() {
		return new RangerResponse<>(true, null);
	}
}

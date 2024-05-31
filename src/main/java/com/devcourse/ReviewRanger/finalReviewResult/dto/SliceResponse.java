package com.devcourse.ReviewRanger.finalReviewResult.dto;

import java.util.List;

public class SliceResponse<T> {
    private List<T> data;
    private int currentPage;
    private int pageSize;
    private boolean hasNext;

    public SliceResponse(List<T> data, int currentPage, int pageSize, boolean hasNext) {
        this.data = data;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.hasNext = hasNext;
    }

}

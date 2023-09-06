package com.btc.snow.include.page;

import lombok.Data;

@Data
public class PageMakerDto {
    private int startPage;          // 시작 페이지
    private int endPage;            // 끝 페이지
    private boolean prev, next;     // 이전 페이지 또는 다음 페이지 출력 유/무
    private int total;              // 전체 게시물 개수
    private int totalPage;          // 전체 페이지 수
    private Criteria criteria;      // 현재 페이지, 페이지당 게시물 정보

    public PageMakerDto(Criteria criteria, int total) {
        this.criteria = criteria;
        this.total = total;

        this.endPage = (int) (Math.ceil(criteria.getPageNum() / 10.0)) * 10;
        this.startPage = this.endPage - 9;

        int realEnd = (int) (Math.ceil(total * 1.0 / criteria.getAmount()));

        if (realEnd < this.endPage)
            this.endPage = realEnd;

        this.prev = this.startPage > 1;
        this.next = this.endPage < realEnd;

        this.totalPage = total / criteria.getAmount();
        if (total % criteria.getAmount() != 0)
            totalPage += 1;

    }


}

package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.service.RankingService;
import com.ssafy.enjoytrip.dto.RankingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RankingController {

    private final RankingService rankingService;

    @Autowired
    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping("/api/ranking/reviews")
    public List<RankingDto> getReviewRanking() {
        return rankingService.getReviewRanking();
    }

    @GetMapping("/api/ranking/likes")
    public List<RankingDto> getLikeRanking() {
        return rankingService.getLikeRanking();
    }

    @GetMapping("/api/ranking/badges")
    public List<RankingDto> getBadgeRanking() {
        return rankingService.getBadgeRanking();
    }
}

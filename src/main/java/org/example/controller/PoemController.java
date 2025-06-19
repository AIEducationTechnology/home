package org.example.controller;

import org.example.entity.PoetryRecord;
import org.example.model.ApiResponse;
import org.example.model.Poem;
import org.example.repository.PoemRepository;
import org.example.repository.PoetryRecordRepository;
import org.example.service.GushiwenScraper;
import org.example.service.GushiwenScraperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/poems")
public class PoemController {
    private static final Logger logger = LoggerFactory.getLogger(PoemController.class);

    private final GushiwenScraperService scraperService;
    private final PoetryRecordRepository poetryRepo;

    @Autowired
    private PoemRepository poemRepository;

    @Autowired
    private GushiwenScraper gushiwenScraper;

    public PoemController(GushiwenScraperService scraperService, PoetryRecordRepository poetryRepo) {
        this.scraperService = scraperService;
        this.poetryRepo = poetryRepo;
    }

    @GetMapping("/{fragment}")
    public List<PoetryRecord> searchAndSave(@PathVariable String fragment) {
        List<PoetryRecord> results = scraperService.scrapePoems(fragment);
        return poetryRepo.saveAll(results);
    }

    /**
     * 通过古诗片段搜索完整古诗（本地数据库）
     * @param keyword 古诗片段
     * @return 匹配的古诗列表
     */
    @GetMapping("/searchPoemLocal")
    public ApiResponse searchPoemLocal(@RequestParam String keyword) {
        // 使用本地数据库查询
        List<Poem> poems = poemRepository.searchByKeyword(keyword);

        // 以日志方式打印结果
        logger.info("找到 {} 首匹配的古诗", poems.size());

        return ApiResponse.success(poems);
    }

    /**
     * 通过古诗片段搜索完整古诗（使用古诗文网）
     * @param keyword 古诗片段
     * @return 匹配的古诗列表
     */
    @GetMapping("/searchPoemOnline")
    public ApiResponse searchPoemOnline(@RequestParam String keyword) {
        try {
            // 使用古诗文网爬虫服务查询
            List<Poem> poems = gushiwenScraper.searchPoems(keyword);

            // 以日志方式打印结果
            logger.info("找到 {} 首匹配的古诗", poems.size());

            return ApiResponse.success(poems);
        } catch (IOException e) {
            logger.error("搜索古诗时发生错误", e);
            return ApiResponse.error("搜索古诗时发生错误: " + e.getMessage());
        }
    }
}
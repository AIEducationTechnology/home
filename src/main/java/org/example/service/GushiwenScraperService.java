package org.example.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.example.entity.PoetryRecord;

@Service
public class GushiwenScraperService {

    private static final Logger logger = LoggerFactory.getLogger(GushiwenScraperService.class);

    @Value("${gushiwen.search-url}")
    private String searchUrl;

    public List<PoetryRecord> scrapePoems(String fragment) {
        List<PoetryRecord> records = new ArrayList<>();

        try {
            String encodedQuery = URLEncoder.encode(fragment, "UTF-8");
            String firstChar = fragment.isEmpty() ? "" : String.valueOf(fragment.charAt(0));
            String encodedFirstChar = URLEncoder.encode(firstChar, "UTF-8");
            String url = searchUrl + "?value=" + encodedQuery + "&valuej=" + encodedFirstChar;

            Document document = Jsoup.connect(url).get();
            String htmlContent = document.html();
            records = parsePoetryFromHtml(htmlContent);
        } catch (IOException e) {
            logger.error("Failed to scrape poems: {}", e.getMessage(), e);
        }

        return records;
    }
    
//    public List<PoetryRecord> scrapePoemDetails(Document doc) {
//        List<PoetryRecord> poetryList = new ArrayList<>();
//
//        Elements textAreas = doc.select("textarea:containsOwn(——)");
//        for (Element area : textAreas) {
//            String rawText = area.val().trim();
//
//            // 优化正则表达式
//            Pattern pattern = Pattern.compile("(.*?)——(?:([^\\u4e00-\\u9fa5\\s]*[^\\s·]+)·)?([^\\u4e00-\\u9fa5《》\\s]+)(?:《([^》]+)》)?(https?://[^\\"\\s]+)", Pattern.DOTALL);
//            Matcher matcher = pattern.matcher(rawText);
//
//            if (matcher.find()) {
//                String content = matcher.group(1).trim();
//                String authorDynastyStr = matcher.group(2);
//                String dynasty = "";
//                String author = "";
//                if (authorDynastyStr != null && !authorDynastyStr.isEmpty()) {
//                    String[] parts = authorDynastyStr.split("·");
//                    dynasty = parts.length > 0 ? parts[0] : "";
//                    author = parts.length > 1 ? parts[1] : "";
//                }
//                String title = matcher.group(3);
//                String source = matcher.group(4);
//
//                PoetryRecord record = new PoetryRecord();
//                record.setTitle(title == null || title.isEmpty() ? "未知标题" : title);
//                record.setAuthor(author == null || author.isEmpty() ? "未知作者" : author);
//                record.setDynasty(dynasty == null || dynasty.isEmpty() ? "未知朝代" : dynasty);
//                record.setContent(content.isEmpty() ? "暂无内容" : content);
//                record.setSource(source == null || source.isEmpty() ? "未知来源" : source);
//                record.setCreateTime(LocalDateTime.now());
//
//                poetryList.add(record);
//            } else {
//                logger.warn("No match found for raw text: {}", rawText);
//            }
//        }
//        return poetryList;
//    }

    // 新增解析方法
    public List<PoetryRecord> parsePoetryFromHtml(String htmlContent) {
        List<PoetryRecord> records = new ArrayList<>();
        Document doc = Jsoup.parse(htmlContent);

        // 找到所有诗歌条目容器
        Elements sonsElements = doc.select(".sons");

        for (Element sons : sonsElements) {
            // 使用更精确的选择器定位标题元素
            Element titleElement = sons.selectFirst(".cont > p:first-of-type > a:first-of-type");
            // 使用更精确的选择器定位来源元素
            Element sourceElement = sons.selectFirst(".source > a");
            Element contentElement = sons.selectFirst(".contson");

            if (titleElement != null && sourceElement != null && contentElement != null) {
                String title = titleElement.text().trim();
                String rawSource = sourceElement.text().trim();
                String content = contentElement.text().trim();
                String link = titleElement.absUrl("href");

                // 提取朝代和作者 - 更稳定的匹配方式
                String dynasty = "";
                String author = "";
                // 改进后的正则表达式
                Pattern pattern = Pattern.compile("([\\u4e00-\\u9fa5]{1,10}[朝|代])");
                Matcher matcher = pattern.matcher(rawSource);
                if (matcher.find()) {
                    dynasty = matcher.group(1);
                    // 提取朝代之后的部分作为作者
                    author = rawSource.substring(matcher.end()).trim();
                } else {
                    author = rawSource;
                }

                records.add(new PoetryRecord(title, author, dynasty, content, link));
            }
        }

        return records;
    }
}
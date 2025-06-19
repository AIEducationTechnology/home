package org.example.service;

import org.example.model.Poem;
import org.example.repository.PoemRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 古诗文网爬虫服务
 */
@Service
public class GushiwenScraper {
    @Autowired
    private PoemRepository poemRepository; // 注入 PoemRepository

    private static final String BASE_URL = "https://www.gushiwen.cn/";

    /**
     * 根据关键词搜索古诗
     * @param keyword 搜索关键词
     * @return 匹配的古诗列表
     * @throws IOException 如果网络请求失败
     */
    public List<Poem> searchPoems(String keyword) throws IOException {
        List<Poem> poems = new ArrayList<>();
        
        // 构建搜索URL，使用更精确的查询参数
        String searchUrl = BASE_URL + "search.aspx?value=" + keyword;
        
        // 如果关键词不为空，添加valuej参数以匹配开头
        if (!keyword.isEmpty()) {
            searchUrl += "&valuej=" + keyword.charAt(0);
        }
        
        // 获取搜索结果页面
        Document doc = Jsoup.connect(searchUrl).get();
        
        // 解析搜索结果
        Elements poemElements = doc.select("div.sons");
        
        for (Element poemElement : poemElements) {
            try {
                // 解析每首古诗的信息
                Element titleElement = poemElement.selectFirst("p > a[style^=font-size:18px;] b");
                Element contentElement = poemElement.selectFirst("div.contson, p.contson");
                Element authorElement = poemElement.selectFirst("p.source > a");
                
                if (titleElement != null && contentElement != null && authorElement != null) {
                    // 提取古诗信息
                    String title = titleElement.text();
                    // 提取并清理诗歌内容（保留纯文本）
                    String content = Jsoup.parse(contentElement.html()).text();
                    String author = authorElement.text();
                    
                    // 创建新的古诗对象
                    Poem poem = new Poem(title, author, content);
        
                    // 保存到数据库
                    poemRepository.save(poem);
        
                    // 添加到结果列表
                    poems.add(poem);
                }
            } catch (Exception e) {
                // 忽略解析错误的条目
                continue;
            }
        }
        
        return poems;
    }
}
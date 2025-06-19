package org.example.repository;

import org.example.model.Poem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * 古诗数据访问层接口
 */
public interface PoemRepository extends JpaRepository<Poem, Long> {
    /**
     * 根据关键词搜索古诗
     * @param keyword 关键词
     * @return 匹配的古诗列表
     */
    List<Poem> findByTitleContainingOrAuthorContainingOrContentContaining(String keyword, String keyword1, String keyword2);

    /**
     * 自定义方法：根据关键词搜索古诗
     * @param keyword 关键词
     * @return 匹配的古诗列表
     */
    default List<Poem> searchByKeyword(String keyword) {
        return findByTitleContainingOrAuthorContainingOrContentContaining(keyword, keyword, keyword);
    }
}
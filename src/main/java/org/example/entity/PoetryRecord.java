package org.example.entity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "poetry_records")
@Data
public class PoetryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "author", nullable = false, length = 50)
    private String author;

    @Column(name = "dynasty", nullable = false, length = 20)
    private String dynasty;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "source", length = 50, nullable = true)
    private String source;

    @Column(name = "create_time", updatable = false, insertable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    // 手动添加的构造函数
    public PoetryRecord(String title, String author, String dynasty, String content, String link) {
        this.title = title;
        this.author = author;
        this.dynasty = dynasty;
        this.content = content;
        this.source = link;
    }
}

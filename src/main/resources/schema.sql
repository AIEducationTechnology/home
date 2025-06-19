DROP TABLE IF EXISTS poetry_records;
CREATE TABLE poetry_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(50) NOT NULL,
    dynasty VARCHAR(20) NOT NULL,
    content TEXT NOT NULL,
    genre VARCHAR(20),
    fragment VARCHAR(200) NOT NULL,
    source VARCHAR(50),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 创建索引
CREATE INDEX idx_title ON poetry_records(title);
CREATE INDEX idx_author ON poetry_records(author);
CREATE INDEX idx_fragment ON poetry_records(fragment);
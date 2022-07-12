DROP TABLE IF EXISTS coupon_history;
DROP TABLE IF EXISTS member;

CREATE TABLE member
(
    id          BIGINT(0) NOT NULL AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL,
    email       VARCHAR(350) NOT NULL,
    social_id   VARCHAR(255) NOT NULL,
    image_url   VARCHAR(2000) NOT NULL,
    created_at  DATETIME    NOT NULL,
    modified_at DATETIME    NOT NULL,
    PRIMARY KEY (id)
) DEFAULT CHARSET=utf8mb4
ENGINE=InnoDB;

CREATE TABLE coupon_history
(
    id          BIGINT(0) NOT NULL AUTO_INCREMENT,
    sender_id   BIGINT(0) NOT NULL,
    receiver_id BIGINT(0) NOT NULL,
    coupon_type VARCHAR(20) NOT NULL,
    title       VARCHAR(50) NOT NULL,
    message     VARCHAR(255) NOT NULL,
    created_at  DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (sender_id) REFERENCES member (id),
    FOREIGN KEY (receiver_id) REFERENCES member (id)
) DEFAULT CHARSET=utf8mb4
ENGINE=InnoDB;

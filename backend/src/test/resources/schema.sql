CREATE TABLE users (
    id BIGINT(0) NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    social_nickname VARCHAR(255) NOT NULL,
    social_id BIGINT(255) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,
PRIMARY KEY (id))
DEFAULT CHARSET=utf8mb4
ENGINE=InnoDB;

CREATE TABLE coupon (
    id BIGINT(0) NOT NULL AUTO_INCREMENT,
    type VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,
    PRIMARY KEY (id))
DEFAULT CHARSET=utf8mb4
ENGINE=InnoDB;

CREATE TABLE coupon_history (
    id BIGINT(0) NOT NULL AUTO_INCREMENT,
    sender_id BIGINT(0) NOT NULL,
    receiver_id BIGINT(0) NOT NULL,
    coupon_id BIGINT(0) NOT NULL,
    created_at DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (sender_id) REFERENCES users(id),
    FOREIGN KEY (receiver_id) REFERENCES users(id),
    FOREIGN KEY (coupon_id) REFERENCES coupon(id))
DEFAULT CHARSET=utf8mb4
ENGINE=InnoDB;

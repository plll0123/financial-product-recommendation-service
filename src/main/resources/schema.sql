DROP TABLE if EXISTS cart CASCADE;
DROP TABLE if EXISTS concern_product CASCADE;
DROP TABLE if EXISTS member CASCADE;
DROP TABLE if EXISTS product CASCADE;

CREATE TABLE member
(
    member_id    BIGINT NOT NULL AUTO_INCREMENT,
    login_id     VARCHAR(255),
    password     VARCHAR(255),
    username     VARCHAR(255),
    phone_number VARCHAR(255),
    roles        VARCHAR(255),
    job          VARCHAR(255),
    interest     VARCHAR(255),
    real_estate  VARCHAR(255),
    car          VARCHAR(255),
    asset        VARCHAR(255),
    salary       VARCHAR(255),
    age          VARCHAR(255),
    is_not_first TINYINT DEFAULT 0,
    PRIMARY KEY (member_id)
);

CREATE TABLE product
(
    product_id   BIGINT NOT NULL,
    company_name VARCHAR(255),
    product_name VARCHAR(255),
    price        VARCHAR(255),
    job          VARCHAR(255),
    interest     VARCHAR(255),
    real_estate  VARCHAR(255),
    car          VARCHAR(255),
    asset        VARCHAR(255),
    salary       VARCHAR(255),
    age          VARCHAR(255),
    tag          VARCHAR(255),
    tag_content  VARCHAR(255),
    details      VARCHAR(255),
    logo         VARCHAR(255),
    description  VARCHAR(255),
    PRIMARY KEY (product_id)
);

CREATE TABLE concern_product
(
    concern_product_id BIGINT NOT NULL AUTO_INCREMENT,
    member_id          BIGINT,
    product_id         BIGINT,
    PRIMARY KEY (concern_product_id)
);

CREATE TABLE cart
(
    cart_id    BIGINT NOT NULL AUTO_INCREMENT,
    member_id  BIGINT,
    product_id BIGINT,
    PRIMARY KEY (cart_id)
);

ALTER TABLE cart
    ADD CONSTRAINT FKix170nytunweovf2v9137mx2o
        FOREIGN KEY (member_id)
            REFERENCES member (member_id);

ALTER TABLE cart
    ADD CONSTRAINT FK3d704slv66tw6x5hmbm6p2x3u
        FOREIGN KEY (product_id)
            REFERENCES product (product_id);

ALTER TABLE concern_product
    ADD CONSTRAINT FK2sbvcbe4evw3gsc2uw9xpur4e
        FOREIGN KEY (member_id)
            REFERENCES member (member_id);

ALTER TABLE concern_product
    ADD CONSTRAINT FKan16mxw04m31afq8cjqajdlvs
        FOREIGN KEY (product_id)
            REFERENCES product (product_id);
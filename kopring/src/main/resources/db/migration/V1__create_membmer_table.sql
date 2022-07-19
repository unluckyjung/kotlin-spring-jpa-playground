CREATE TABLE IF not EXISTS member
(
    member_id   BIGINT       NOT NULL auto_increment,
    member_name VARCHAR(255) NOT NULL,

    PRIMARY KEY (member_id)
)
DROP TABLE IF EXISTS `rentals`;

CREATE TABLE `rentals`
(
    `id`               bigint NOT NULL AUTO_INCREMENT,
    `rental_date`      date   NOT NULL,
    `rental_day_count` int    NOT NULL,
    `rental_discount`  int    NOT NULL,
    `tool_id`          bigint NOT NULL,
    `created_on`       datetime(6) DEFAULT NULL,
    `last_updated_on`  datetime(6) DEFAULT NULL,
    `version`          int DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY                `FKl5jg75ajrxsbvyj8rvruep0do` (`tool_id`),
    CONSTRAINT `FKl5jg75ajrxsbvyj8rvruep0do` FOREIGN KEY (`tool_id`) REFERENCES `tools` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
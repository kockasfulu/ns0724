DROP TABLE IF EXISTS `tools`;
DROP TABLE IF EXISTS `tool_brands`;
DROP TABLE IF EXISTS `tool_types`;

CREATE TABLE `tool_brands`
(
    `id`              bigint      NOT NULL AUTO_INCREMENT,
    `name`            varchar(30) NOT NULL,
    `created_on`      datetime(6) DEFAULT NULL,
    `last_updated_on` datetime(6) DEFAULT NULL,
    `version`         int DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tool_types`
(
    `id`                bigint      NOT NULL AUTO_INCREMENT,
    `daily_charge`      decimal(10, 2) DEFAULT NULL,
    `is_holiday_charge` bit(1)      NOT NULL,
    `is_weekday_charge` bit(1)      NOT NULL,
    `is_weekend_charge` bit(1)      NOT NULL,
    `name`              varchar(30) NOT NULL,
    `created_on`        datetime(6) DEFAULT NULL,
    `last_updated_on`   datetime(6) DEFAULT NULL,
    `version`           int            DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tools`
(
    `id`              bigint      NOT NULL AUTO_INCREMENT,
    `code`            varchar(10) NOT NULL,
    `tool_brand_id`   bigint      NOT NULL,
    `tool_type_id`    bigint      NOT NULL,
    `created_on`      datetime(6) DEFAULT NULL,
    `last_updated_on` datetime(6) DEFAULT NULL,
    `version`         int DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK1s923wkmf1f3q19fvxe2hm17l` (`code`),
    KEY               `FKsiltytsip43ihje6rffhya6sp` (`tool_brand_id`),
    KEY               `FKg098apokbbyhgnyqyebsoy3lu` (`tool_type_id`),
    CONSTRAINT `FKg098apokbbyhgnyqyebsoy3lu` FOREIGN KEY (`tool_type_id`) REFERENCES `tool_types` (`id`),
    CONSTRAINT `FKsiltytsip43ihje6rffhya6sp` FOREIGN KEY (`tool_brand_id`) REFERENCES `tool_brands` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
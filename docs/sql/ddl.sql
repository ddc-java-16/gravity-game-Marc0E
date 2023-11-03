-- Generated 2023-11-02 17:11:34-0600 for database version 1

CREATE TABLE IF NOT EXISTS `user`
(
    `user_id`           INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `created`           INTEGER                           NOT NULL,
    `oauth_key`         TEXT                              NOT NULL,
    `display_name`      TEXT                              NOT NULL COLLATE NOCASE,
    `display_nick_name` TEXT                              NOT NULL COLLATE NOCASE,
    `user_photo`        TEXT                              NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS `index_user_oauth_key` ON `user` (`oauth_key`);

CREATE UNIQUE INDEX IF NOT EXISTS `index_user_display_name` ON `user` (`display_name`);

CREATE TABLE IF NOT EXISTS `score`
(
    `score_id`  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `created`   INTEGER                           NOT NULL,
    `started`   INTEGER                           NOT NULL,
    `duration`  INTEGER                           NOT NULL,
    `value`     INTEGER                           NOT NULL,
    `player_id` INTEGER                           NOT NULL,
    FOREIGN KEY (`player_id`) REFERENCES `user` (`user_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS `index_score_value` ON `score` (`value`);

CREATE INDEX IF NOT EXISTS `index_score_player_id` ON `score` (`player_id`);

CREATE VIEW `user_score` AS
SELECT s.player_id, u.display_name, u.display_nick_name, s.value, s.created
FROM user AS u
         JOIN score AS s ON u.user_id = s.player_id
ORDER BY value DESC;
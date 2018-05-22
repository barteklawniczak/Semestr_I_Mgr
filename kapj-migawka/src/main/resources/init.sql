USE `migawka`;

LOCK TABLES `ROLES` WRITE;
INSERT INTO `ROLES` VALUES (1,'ROLE_USER');
INSERT INTO `ROLES` VALUES (2,'ROLE_EMPLOYEE');
INSERT INTO `ROLES` VALUES (3,'ROLE_ADMIN');
UNLOCK TABLES;

LOCK TABLES `USERS` WRITE, `USERS_ROLES` WRITE, `TICKET_TYPES` WRITE;
#insert admin into database, all values are default, password 'admin' as bcrypt hash
INSERT INTO `USERS` VALUES (0, '1970-1-1', 'admin@admin.pl', 1, 'admin',
                            '$2a$11$PUNSo3qGxEUuUNx7InrwlOcjW3Up203h3R7hM.6fBkw2/LeN97/2m',
                            CURRENT_TIMESTAMP, 'admin', null, 'admin');
INSERT INTO `USERS_ROLES` VALUES (0,3);
INSERT INTO `TICKET_TYPES` VALUES (1, 'All lines - bearer', 5, 33);
INSERT INTO `TICKET_TYPES` VALUES (2, 'All lines - bearer', 30, 135);
INSERT INTO `TICKET_TYPES` VALUES (3, 'All lines - bearer', 90, 338);
INSERT INTO `TICKET_TYPES` VALUES (4, 'All lines - personal', 30, 90);
INSERT INTO `TICKET_TYPES` VALUES (5, 'All lines - personal', 90, 225);
INSERT INTO `TICKET_TYPES` VALUES (6, 'All lines - personal', 365, 730);
UNLOCK TABLES;
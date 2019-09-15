CREATE TABLE userDb(
 userName varchar(100) NOT NULL,
 userId UUID NOT NULL PRIMARY KEY,
 userEmail varchar(100) DEFAULT NULL,
 userpassword varchar(20) NOT NULL
);
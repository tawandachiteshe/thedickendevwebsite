CREATE TABLE userdb(
 userName varchar(100) NOT NULL,
 userId UUID NOT NULL PRIMARY KEY,
 userEmail varchar(100) DEFAULT NULL,
 userpassword varchar(1000) NOT NULL,
 enabled bool NOT NULL,
 user_role varchar(20) not null
);

CREATE TABLE stories(
storyId UUID not null primary key,
heading varchar,
body varchar,
category varchar
);
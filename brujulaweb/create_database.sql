CREATE TABLE `user` (
	id int NOT NULL AUTO_INCREMENT,
	email varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	last_login DATETIME NULL,
	lockout_date DATETIME NULL,
	lockout_count SMALLINT NULL,
	association int NOT NULL,
	creation_date DATETIME NOT NULL,
	creation_user INT NOT NULL,
	modification_date DATETIME NOT NULL,
	modification_user INT NOT NULL,
	status varchar(20) NOT NULL,
	CONSTRAINT user_PK PRIMARY KEY (id)
);

CREATE TABLE `association` (
	id INT auto_increment NOT NULL,
	name varchar(255) NOT NULL,
	creation_date DATETIME NOT NULL,
	creation_user varchar(255) NOT NULL,
	modification_date DATETIME NOT NULL,
	modification_user INT NOT NULL,
	status varchar(20) NOT NULL,
	taxonomy json NOT NULL,
	CONSTRAINT association_pk PRIMARY KEY (id)
);

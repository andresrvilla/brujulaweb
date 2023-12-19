CREATE TABLE `user` (
	id varchar(100) NOT NULL,
	email varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	last_login DATETIME NULL,
	status varchar(20) NOT NULL,
	lockout_date DATETIME NULL,
	lockout_count SMALLINT NULL,
	CONSTRAINT user_PK PRIMARY KEY (id)
)
create table comment
(
	id BIGINT auto_increment,
	parent_id BIGINT not null,
	type int not null,
	commentator BIGINT,
	gmt_create BIGINT,
	gmt_modified BIGINT,
	like_count BIGINT default 0,
	comment_count BiGINT default  0,
	constraint comment_pk
		primary key (id)
);
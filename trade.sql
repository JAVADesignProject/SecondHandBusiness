create schema trade collate utf8_general_ci;

create table test
(
	sender int null,
	receiver int null,
	id int auto_increment
		primary key
);

create table user
(
	id varchar(16) not null comment '用户id同时也是学号'
		primary key,
	username varchar(32) not null comment '用户名',
	password varchar(32) not null comment '密码',
	status tinyint(1) null comment '登录状态'
);

create table comment
(
	id int auto_increment comment '评论的id'
		primary key,
	reviewer_id varchar(16) not null comment '评论人的id',
	production_id int unsigned null comment '被评论的商品的id',
	review_time timestamp null comment '评论时间',
	content text null comment '评论内容',
	constraint comment_ibfk_1
		foreign key (reviewer_id) references user (id)
);

create index production_id
	on comment (production_id);

create index reviewer_id
	on comment (reviewer_id);

create table message
(
	id int auto_increment comment '信息id'
		primary key,
	text text null comment '信息内容',
	sender_id varchar(16) null comment '发送人的id',
	receiver_id varchar(16) null comment '接收人的id',
	time timestamp null comment '发送时间',
	constraint message_ibfk_1
		foreign key (sender_id) references user (id),
	constraint message_ibfk_2
		foreign key (receiver_id) references user (id)
);

create index receiver_id
	on message (receiver_id);

create index sender_id
	on message (sender_id);

create table production
(
	id int unsigned auto_increment comment '商品id'
		primary key,
	name text not null comment '商品名称',
	price int not null comment '价格或者拍卖最初价格',
	introduction text null comment '商品介绍',
	producer_id varchar(16) not null comment '发布者id',
	post_time timestamp not null comment '发布时间',
	bought tinyint(1) default 0 null comment '是否已被购买',
	buyer_id varchar(16) null comment '购买者',
	auction tinyint(1) null comment '是否是拍卖商品',
	auction_max_price int null comment '拍卖最高价格',
	max_price_user_id varchar(16) null comment '出价最高的人',
	image mediumblob null comment '商品图片1',
	constraint production_ibfk_1
		foreign key (producer_id) references user (id),
	constraint production_ibfk_2
		foreign key (buyer_id) references user (id)
);

create index buyer_id
	on production (buyer_id);

create index producer_id
	on production (producer_id);


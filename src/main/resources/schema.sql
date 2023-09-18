-- public.youtube_video definition

-- Drop table

-- DROP TABLE public.youtube_video;

CREATE TABLE public.youtube_video (
	client varchar(255) NOT NULL,
	keyword varchar(255) NOT NULL,
	video_id varchar(255) NOT NULL,
	title varchar(255) NULL,
	thumbnail_url varchar(255) NULL,
	channel varchar(255) NULL,
	channel_id varchar(255) NULL,
	published_at varchar(255) NULL,
	w_date date NULL DEFAULT now(),
	CONSTRAINT youtube_video_pk PRIMARY KEY (client, keyword, video_id)
);

-- public.youtube_video_stat definition

-- Drop table

-- DROP TABLE public.youtube_video_stat;

CREATE TABLE public.youtube_video_stat (
	video_id varchar(255) NOT NULL,
	view_count int8 NULL,
	like_count int8 NULL,
	comment_count int8 NULL,
	w_date date NULL DEFAULT now(),
	CONSTRAINT youtube_video_stat_pk PRIMARY KEY (video_id)
);

-- public.youtube_channel_stat definition

-- Drop table

-- DROP TABLE public.youtube_channel_stat;

CREATE TABLE public.youtube_channel_stat (
	channel_id varchar(255) NOT NULL,
	subscriber_count int8 NULL,
	view_count int8 NULL,
	video_count int8 NULL,
	w_date date NULL DEFAULT now(),
	CONSTRAINT youtube_channel_stat_pk PRIMARY KEY (channel_id)
);

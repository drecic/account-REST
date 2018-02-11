create EXTENSION if not exists pgcrypto;

drop table if exists Users cascade;
drop table if exists Devices;
drop table if exists Codes;

create table Users (
	UserID UUID primary key default gen_random_uuid() NOT NULL,
	CreatedAt TIMESTAMP default now() NOT NULL,
	Username TEXT NOT NULL UNIQUE,
	Password TEXT NOT NULL,
	RecoveryEmail TEXT,
	EmailVerified BOOL default FALSE NOT NULL,
	Secret TEXT NOT NULL,
	DisabledAt TIMESTAMP default NULL
);

create table Devices (
	DeviceID UUID primary key default gen_random_uuid() NOT NULL,
	UserID UUID NOT NULL,
	Token TEXT NOT NULL,
	Issued TIMESTAMP default now() NOT NULL,
	Expires TIMESTAMP default now() NOT NULL,
	LastRefresh TIMESTAMP default now() NOT NULL,
	Name TEXT,
	foreign key (UserID) references Users(UserID)
);

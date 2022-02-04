-- test table (spring)

CREATE TABLE IF NOT EXISTS tbl_member(
	userid VARCHAR(50),
    userpw VARCHAR(45),
    username VARCHAR(45),
    regdate TIMESTAMP,
    updatedate TIMESTAMP
);

DESC tbl_member;
commit;
SELECT * FROM tbl_member;

CREATE TABLE tbl_board(
	bno INT PRIMARY KEY auto_increment,
    title VARCHAR(200) NOT NULL,
    content LONGTEXT NULL,
    writer VARCHAR(50) NOT NULL,
    regdate TIMESTAMP NOT NULL DEFAULT now(),
    viewcnt INT DEFAULT 0
);

DESC tbl_board;
commit;

SELECT * FROM tbl_board ORDER BY bno ASC;

INSERT INTO tbl_board(title,content,writer) SELECT title,content,writer FROM tbl_board;

CREATE TABLE tbl_comment(
	cno INT PRIMARY KEY auto_increment,
	bno INT NOT NULL default 0,
	commentText TEXT NOT NULL,
	commentAuth VARCHAR(50) NOT NULL,
    commentDelete char(1) default 'N',
	regdate TIMESTAMP NOT NULL default now(),
	updatedate TIMESTAMP NOT NULL default now(),
	INDEX(bno),
	CONSTRAINT fk_tbl_board_bno FOREIGN KEY(bno) REFERENCES tbl_board(bno)
);

DESC tbl_comment;

SELECT * FROM tbl_board WHERE bno = 1;
commit;
SELECT * FROM tbl_comment;

CREATE TABLE tbl_user(
	uno INT PRIMARY KEY auto_increment,		-- 사용자 번호
    uid VARCHAR(50) NOT NULL UNIQUE,		-- 아이디
    upw VARCHAR(200) NOT NULL,				-- 비밀번호
    uname VARCHAR(50) NOT NULL,				-- 이름
    upoint INT NOT NULL DEFAULT 0			-- 포인트
);

CREATE TABLE tbl_message(
	mno INT PRIMARY KEY auto_increment,					-- 메세지 번호
    targetid VARCHAR(50) NOT NULL,						-- 수신자 아이디
    sender VARCHAR(50) NOT NULL,						-- 발신자 아이디
    message TEXT NOT NULL,								-- 메세지
    opendate TIMESTAMP NULL,							-- 수신확인 시간
    senddate TIMESTAMP NOT NULL default now(),			-- 발신 시간
    FOREIGN KEY(targetid) REFERENCES tbl_user(uid),
    FOREIGN KEY(sender) REFERENCES tbl_user(uid)
);


INSERT INTO tbl_user(uid,upw,uname) VALUES('bmg','123','BMG');
INSERT INTO tbl_user(uid,upw,uname) VALUES('bmj','123','BMJ');
INSERT INTO tbl_user(uid,upw,uname) VALUES('ojm','123','OJM');

commit;

SELECT * FROM tbl_user;	

SELECT * FROM tbl_message;

CREATE TABLE ban_ip(
	ip VARCHAR(50) PRIMARY KEY,
    cnt INT DEFAULT 1,
    bandate TIMESTAMP DEFAULT now()
);

DESC ban_ip;

commit;

SELECT * FROM ban_ip;

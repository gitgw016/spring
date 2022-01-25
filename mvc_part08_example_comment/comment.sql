use smartWeb;



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

SELECT bno FROM tbl_board limit 1;

SELECT * FROM tbl_comment;





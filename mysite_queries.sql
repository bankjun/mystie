desc guestbook;
select * from guestbook;
select now() from dual;


select no, name, password, message, reg_date from guestbook;
select no, name, password, message, date_format(reg_date, '%Y-%m-%d') from guestbook;

insert into guestbook values(null, '안대혁', 'abcd', '안녕하세요', NOW());

insert into guestbook values(null, '안대혁', 'abcd', '안녕하세요', '2023-01-03');


-- 회원가입
insert into user values(null, '둘리', 'dooly@gmail.com', password(1234), 'male', now());

select * from user;

-- 로그인
select no, name from user where email = ? and password = password(?);

-- 업데이트 인증 


-- 업데이트a
update user
set name= ?, password = password(?), gender= ?
where no =?;


-- 게시판
desc board;
desc user;

select * from board;
select * from user;
-- 가상데이터

insert into board
values (null, "밥먹자", "무슨밥먹을래", 0, now(), 1, 1, 0, 1 );

insert into board
values (null, "안녕", "안녕하세요", 0, now(), 2, 1, 0, 2 );

insert into board
values (null, "짜장면", "짜장면 맛있겠다", 0, now(), 1, 2, 1, 3 );

select max(g_no)
  from board;
  
-- 게시판 리스트
  select a.title, b.name, a.hit, a.reg_date
    from board a, user b
   where a.user_no = b.no
order by a.g_no desc, a.o_no asc;

-- 게시판 view
select title, contents from board where no = 2;
select a.title, a.contents, a.g_no, a.o_no, a.depth, b.no
  from board a, user b
 where a.user_no = b.no
   and a.no = 2; 
 
 update board set contents = " 안녕하세요 안녕하세요 안녕하세요 /n\n 안녕하세요"
 where no =2;

-- 게시판 write 기본글쓰기
insert into board
values (null, "testtitle", "testcontent", 0, now(), 
((select max(a.g_no) from board a)+1), 1, 0, 1 );

select * from board order by g_no desc, o_no desc;

delete from board
where no=7;

-- 게시판 reply 답글쓰기
insert into board
values (null, "testtitle", "testcontent", 0, now(), 
?, ?, ?, ? );

update board set o_no = o_no+1
 where g_no = 1 and o_no >= 2;
 

-- 게시판 delete(게시판no, 유저password)
delete a from board a, user b
 where a.user_no = b.no
   and a.no = 8
   and b.password = password(1234);

update board set title="짬뽕" where no=14;

select * from board order by g_no desc, o_no desc;

select * from emaillist;
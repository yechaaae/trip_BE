-- 1. 데이터베이스 생성 (이름: enjoytrip)
CREATE DATABASE IF NOT EXISTS enjoytrip DEFAULT CHARACTER SET utf8mb4;

-- 2. 사용할 DB 선택
USE enjoytrip;




CREATE TABLE user (
    mno INT NOT NULL AUTO_INCREMENT,
    user_id VARCHAR(50) NOT NULL UNIQUE,  -- 중복 불가
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20),
    birth_date VARCHAR(20),
    sex CHAR(1),                          -- 'M' or 'F'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 가입시 자동 시간 입력
    PRIMARY KEY (mno)
);

-- 4. (선택사항) 테스트용 관리자 계정 미리 하나 넣어두기
-- 이걸 넣어두면 회원가입 안 하고 바로 로그인 테스트가 가능합니다.
INSERT INTO user (user_id, password, email, nickname, phone_number, birth_date, sex, created_at)
VALUES ('admin', '1234', 'admin@ssafy.com', '관리자', '010-0000-0000', '1990-01-01', 'M', now());

-- 5. 잘 들어갔나 확인
SELECT * FROM user;




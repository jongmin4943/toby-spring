# 토비의 스프링 스터디
## 초기 설정 방법
1. Database
- 책에서는 mysql 5.1 버전을 사용하지만 튼튼해 보이는 8.x 버전을 사용함
- ./settings 디렉토리에 있는 docker-compose.yml 실행
  - ./settings/db 에 있는 .env 와 my.cnf 설정 파일을 참조하여 컨테이너가 생성됩니다.
  - ./db 디렉토리 하위 디렉토리로 볼륨 설정해놓음
```
> cd ./settings
> docker-compose up
```

3. 데이터 베이스 생성 확인
- ./settings/db/.env 파일에 계정, 패스워드 정보 있습니다.
```
# docker container 접속
> docker exec -it toby-spring-db bash
# mysql 접속
root@bdf248d97aca:/# mysql -u root -p
Enter password:
# root123 입력 후 엔터
```

4. Chapter1 유저 데이블 생성
- **./settings/db/sql/table.sql** 참조

5. Connection Test
- settings 의 설정으로 데이터베이스 생성했을 경우에 확인
- ./chapter1/src/test/java/DataSourceTest.class 테스트 실행


---------------

### GitHub 저장소(repository) 미러링

fork 뜨고 작업한 레포지토리의 커밋로그가 잔디가 안심어져서 신경쓰이시는분은 mirror 방법이 있으니 함 알아보세요.방법은 밑에 적어놨습니다.

---

- ##### 미러링 : commit log를 유지하며 clone

##### <br>

1. #### 저장소 미러링

   1. 복사하고자 하는 저장소의 bare clone 생성

      ```bach
      git clone --bare {복사하고자하는저장소의 git 주소}
      ```

   2. 새로운 저장소로 mirror-push

      ```bash
      cd {복사하고자하는저장소의git 주소}
      git push --mirror {붙여놓을저장소의git주소}
      ```

   3. 1번에서 생성된 저장소 삭제

<br>

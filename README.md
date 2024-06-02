# 실행
1. dist 폴더 안에 main.exe 파일을 다운받습니다.
2. main.exe 파일을 실행합니다.
    만약 'Window의 PC 보호'창 뜬다면 파일 속성에 들어가서

   ![image](https://github.com/SE-Issue-Mgmt-Sys/GUI/assets/67683170/697bdc2c-401a-47dc-879b-7a6a8872c3f1)

    위에 보이는 '차단 해제' 설정을 해주면 됩니다.

# 메뉴얼

## 시작 화면

![image](https://github.com/SE-Issue-Mgmt-Sys/GUI/assets/67683170/ff60afb6-5ae1-4cf6-8753-11c414c1524d)

## 로그인

![image](https://github.com/SE-Issue-Mgmt-Sys/GUI/assets/67683170/64731a2f-6ef2-4e4c-bdd5-99e3f923760c)
  
    해당 버튼을 누르면 왼쪽에서 사이드바가 나오는데 'Login' 버튼을 누르면 로그인 창이 뜹니다.
    로그인하면 사이드바 상단에 'Hi, 유저이름'을 볼 수 있습니다.

  ![image](https://github.com/SE-Issue-Mgmt-Sys/GUI/assets/67683170/9c2d40b2-a218-400b-97bb-66de5b8c5418)


## 로그인 화면

![image](https://github.com/SE-Issue-Mgmt-Sys/GUI/assets/67683170/3296c605-f6c7-44b7-a633-88f79089c3fa)

    계정이 이미 있다면 바로 로그인하거나 계정이 없다면 '계정이 없나요? 계정 만들기' 버튼을 누르면 됩니다.
    또는 로그인 없이 다시 메인 화면으로 돌아갈 수 있습니다.

## 회원 가입 화면

![image](https://github.com/SE-Issue-Mgmt-Sys/GUI/assets/67683170/b596fc72-9bc7-4fc7-b77f-abd2bc3d8a96)

    계정을 만드는데 중복된 아이디가 있거나 이름이 있다면 안내 문구가 뜹니다. 
    유효한 값들은 작성해야 계정이 만들어집니다.
    계정을 만들고 로그인 화면으로 돌아가거나 만들지 않고 로그인 화면으로 돌아갈 수 있습니다.

## 프로젝트 검색

![image](https://github.com/SE-Issue-Mgmt-Sys/GUI/assets/67683170/fd7cab87-baa1-469c-b078-78067e8b1edc)

    프로젝트 검색은 어느 페이지에서든 할 수 있습니다. 검색은 프로젝트 이름으로 검색해야 합니다.

  - 예시)
    
  **검색 전)**
  ![image](https://github.com/SE-Issue-Mgmt-Sys/GUI/assets/67683170/65646bcd-afcf-43aa-ac0a-da0521663203)

  **'테스트용'이라는 키워드로 검색 후)**
  ![image](https://github.com/SE-Issue-Mgmt-Sys/GUI/assets/67683170/dc3f6e6a-4f4b-470c-8f47-9297027eb0ec)

## 프로젝트 페이지

![image](https://github.com/SE-Issue-Mgmt-Sys/GUI/assets/67683170/12703720-6a24-43a0-8968-b4ac76875bdd)

    프로젝트 페이지에서는 '프로젝트 리스트 업데이트', '프로젝트 생성', '내 프로젝트 검색'이 가능하고 
    각 프로젝트에 대해 상세정보를 확인할 수 있습니다.

  - 프로젝트 생성
    ![image](https://github.com/SE-Issue-Mgmt-Sys/GUI/assets/67683170/575ee8d3-6d3c-40ea-93c9-75c197128960)
  
  'Add' 버튼을 누르면 다음과 같이 입력란이 보입니다. 
  프로젝트 추가를 취소하거나 입력란에 올바르게 적어 프로젝트를 추가할 수 있습니다.

  - 프로젝트 리스트 업데이트

  'Update' 버튼을 누르면 프로젝트 리스트 데이터를 업데이트 합니다.
    프로젝트 생성할 때 자동으로 업데이트 된 프로젝트를 리스트를 보여주지만 따로 버튼을 만들어두었습니다.

  - 내 프로젝트 검색

  'My project' 체크 박스에 체크 표시를 하고 'Searh' 버튼을 누르면 
  현재 로그인한 유저(나)와 관련된 프로젝트만 리스트업한 결과를 볼 수 있습니다.
  만약 로그인하지 않은 유저라면 권한이 없다는 문구와 체크 표시를 없애달라는 안내를 받게 됩니다.

  - 프로젝트 상세 정보 열람

  'Detail' 열에 open이라고 적힌 버튼을 누르면 상세 정보를 열람할 수 있습니다.
  이때도 로그인하지 않은 유저라면 권한이 없다라는 안내를 받게 됩니다.


## 프로젝트 상세 정보 페이지

![image](https://github.com/SE-Issue-Mgmt-Sys/GUI/assets/67683170/05f8a592-a445-4c31-ac5e-fb39346014ab)

    특정 프로젝트에 대한 상세 정보를 확인할 수 있습니다. 이곳에서 프로젝트 멤버 추가, 이슈 생성, 이슈 상세 정보 확인 등이 가능합니다.

## 이슈 상세 정보

  이슈 상세 정보는 

  ![image](https://github.com/SE-Issue-Mgmt-Sys/GUI/assets/67683170/ce8c411d-be8d-4b07-86e5-cc9c7938009f)

      사진과 같이 Detail 열의 Click 부분을 클릭하면,
      (다른 열의 셀을 클릭할 때는 아무런 이벤트가 발생하지 않습니다. 해당 열의 셀들에서만 이벤트가 발생합니다.)
      'Detail' 탭이 활성화 됩니다.
      해당 탭에서 이슈 상세 정보를 확인할 수 있습니다.

  ![image](https://github.com/SE-Issue-Mgmt-Sys/GUI/assets/67683170/d79cce95-f9a2-491a-987d-abb3dce6c1a9)

  
## 팀 페이지

      팀 페이지에서는 개발에 참가한 팀원들에 대한 정보를 볼 수 있습니다.

  ![image](https://github.com/SE-Issue-Mgmt-Sys/GUI/assets/67683170/2a8bb2d3-dfc0-4e47-bf01-5ddb95ab9158)


  

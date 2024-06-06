import sys
import webbrowser
import requests
from PyQt5 import QtGui, uic
from PyQt5.QtWidgets import *
from PyQt5.QtCore import Qt, QDateTime
from IssueMgmt_ui import Ui_MainWindow
from login_ui import Ui_Dialog
from datetime import datetime

import os

def resource_path(relative_path):
    try:
        base_path = sys._MEIPASS
    except Exception:
        base_path = os.path.abspath(".")

    return os.path.join(base_path, relative_path)

##http 요청 url
url = 'http://43.203.47.111:8080'

##dateTime 변환
def formatted_datetime(date_string):
    try:
        dt, us = date_string.split('.')
    except ValueError:
        # '.' 문자가 없는 경우
        dt = date_string
        us = '000000'

    if 'Z' in us:
        us = us.replace('Z', '')
        us = (us + '000000')[:6]  # 마이크로초를 6자리로 패딩 또는 잘라내기
        date_string = f"{dt}.{us}Z"
    else:
        us = (us + '000000')[:6]  # 마이크로초를 6자리로 패딩 또는 잘라내기
        date_string = f"{dt}.{us}"
    iso_string = date_string.replace('Z', '+00:00')
    # datetime 객체로 변환
    datetime_obj = datetime.fromisoformat(iso_string)
    formatted = datetime_obj.strftime("%Y-%m-%d %H:%M:%S")
    return formatted
## 알림 창
def about_event(self,str):
    QMessageBox.about(self,str,str)
    
class MainWindow(QMainWindow):
    def __init__(self):
        super(MainWindow, self).__init__()

        self.ui = Ui_MainWindow()
        self.ui.setupUi(self)
        self.setFixedSize(1200, 800)
        self.setWindowTitle("SE ISSUE MGMT")
        self.setWindowIcon(QtGui.QIcon(resource_path('Logo_11.png')))
        ## 유저 관련 변수(유저 데이터 관리)
        self.user_data = ""
        self.user_token=""
        ##기타 세팅
        self.ui.loginorout.setText('Login')
        self.ui.user_name.setText('Unknown')
        self.ui.userBtn.clicked.connect(self.setUserWidget)
        self.ui.user_widget.hide()
        self.ui.icon_only_widget.hide()
        self.ui.stackedWidget.setCurrentIndex(0)
        self.ui.homeButton_2.setChecked(True)
        self.ui.add_widget.hide()
        ## 검색
        self.search_keyword = ""
        self.ui.update_btn.clicked.connect(self.update_project)
        ## 로그인 화면 
        self.login = False
        ## track of uwer_widget hidden or not
        self.user_widget_hidden = True
        ## project 생성
        self.ui.add_btn_2.clicked.connect(self.create_project)
        self.ui.cancel_btn.clicked.connect(self.cancel_create_project)
    
    ## Function for user_widget
    def setUserWidget(self):
        if self.user_widget_hidden :
            self.ui.user_widget.show()
            self.user_widget_hidden = False
        else:
            self.ui.user_widget.hide()
            self.user_widget_hidden = True

    ## Function for searching
    def on_searchBtn_clicked(self):
        search_text = self.ui.searchInput.text().strip()
        if search_text :
            ## keyword 저장해둠
            self.search_keyword = search_text
            ## 스크린 이동
            self.go_to_project_screen()
            self.ui.searchInput.setText("")
    def on_my_btn_clicked(self):
       self.setDataTable()
##===================Project Screen===============================##
    ## user token
    def get_user_token(self):
        return self.user_token
    ## project screen 
    def go_to_project_screen(self):
        self.ui.stackedWidget.setCurrentIndex(2)
        self.setDataTable()
            
    ## proj detail screen 
    def go_to_projdetail(self,id,isPrivate):
        headers = {}
        ## private인 경우
        if isPrivate:
            if self.user_token:
                headers = {
                    "Authorization": f"Bearer {self.user_token}"
                }
            else:
                about_event(self,'권한이 없습니다.')
                return   
        res = requests.get(f'{url}/project/info/{id}', headers=headers)     
        result = res.json()
        if result['isSuccess']:
            detail = result['result']
            self.detail = ProjDetailScreen(detail,parent=self)
            self.detail.exec()
        else:
            return
    ## Fuction for setting project data
    def setDataTable(self):
        self.ui.label_2.setText('')
        table_widget = self.ui.project_table
        ## search keyword가 있을 경우
        keyword = ""
        res = None
        if self.ui.checkBox.isChecked():
            if self.user_token != "":
                ## 헤더 설정
                headers = {
                    "Authorization": f"Bearer {self.user_token}"
                }
                res = requests.get(f'{url}/project/my', headers=headers)
            else:
                self.ui.label_2.setText('권한이 없습니다. 체크 표시를 없애주세요.')
        else : 
            keyword = self.search_keyword
            ##search btn 클릭 > 한번 조회 > 다시 검색 키워드 없는 걸로 세팅
            if self.search_keyword != "":
                self.search_keyword = ""
            res = requests.get(f'{url}/project/search', params={"name":keyword})
        if res != None :
            proj_list = res.json()
            table_widget.setRowCount(len(proj_list))
            table_widget.setColumnCount(7)
            for row in range(len(proj_list)) :
                ##issue 버튼 생성
                button_name = 'issue_btn_' + str(row)
                self.button = QPushButton("Open", self)
                self.button.setAccessibleName(button_name)
                self.button.clicked.connect(lambda _, id = proj_list[row]['id'], isPrivate = proj_list[row]['isPrivate']: self.go_to_projdetail(id, isPrivate))
    
                ##setting table data
                table_widget.setItem(row,0,QTableWidgetItem(str(proj_list[row]['id'])))
                table_widget.setItem(row,1,QTableWidgetItem(proj_list[row]['name']))
                if proj_list[row]['isPrivate'] :
                    table_widget.setItem(row,2,QTableWidgetItem("private"))
                else :
                    table_widget.setItem(row,2,QTableWidgetItem("public"))
                table_widget.setItem(row,3,QTableWidgetItem(proj_list[row]['description']))        
                table_widget.setCellWidget(row,4,self.button) 
                
                ##formatted datetime
                moddate = formatted_datetime(proj_list[row]['moddate'])
                initdate = formatted_datetime(proj_list[row]['initdate'])
                table_widget.setItem(row,5,QTableWidgetItem(moddate))
                table_widget.setItem(row,6,QTableWidgetItem(initdate))  
    
    def update_project(self):
        ## 기존 데이터 삭제 
        self.ui.project_table.setRowCount(0)
        ## 새로운 데이터로 다시 목록 생성
        self.setDataTable()
    ## cancel 'create project' 
    def cancel_create_project(self):
        self.ui.project_comment.setText('')
        self.ui.project_name.setText('')
            
    ## create project
    def create_project(self):
        selected_value = self.ui.comboBox.currentText()
        if selected_value == "private":
            selected_value = True
        elif selected_value == "public":
            selected_value = False
        name = self.ui.project_name.text()
        discription = self.ui.project_comment.text()
        
        ##입력 값이 있고 token이 주어진 유저야함
        if name != "" and discription != "" and self.user_token != "":
            headers = {
                "Authorization": self.user_token,
            }
            res = requests.post(f'{url}/project/create', json={"name": name, "description": discription, "isPrivate": selected_value},headers=headers)
            result = res.json()
            if result['isSuccess']:
                projectId = result['result']['id']
                res2 = requests.post(f'{url}/project/team/{projectId}', json={"nickname":self.user_data['data']['user']['nickname'], "isAdmin": True,"role":"PL"}, headers=headers)
                result2 = res2.json()
                if result2['isSuccess']:
                    self.update_project()
            else:
                about_event(self, '프로젝트 생성에 실패했습니다.')
                self.cancel_create_project()
        else:
            about_event(self, '프로젝트를 생성할 권한이 없습니다.')    
            self.cancel_create_project()    
##===========================================================================================

##===========================================================================================
    ## Change QPushButton Checkable status when stackedWidget index changed
    def on_stackedWidget_currentChanged(self, index):
        btn_list = self.ui.icon_only_widget.findChildren(QPushButton) \
                    + self.ui.full_menu_widget.findChildren(QPushButton)
        
        for btn in btn_list:
            if index in [4, 5]:
                btn.setAutoExclusive(False)
                btn.setChecked(False)
            else:
                btn.setAutoExclusive(True)
    ## functions for changing menu page
    def on_homeButton_1_toggled(self):
        self.ui.stackedWidget.setCurrentIndex(0)
    
    def on_homeButton_2_toggled(self):
        self.ui.stackedWidget.setCurrentIndex(0)

    def on_teamButton_1_toggled(self):
        self.ui.stackedWidget.setCurrentIndex(1)

    def on_teamButton_2_toggled(self):
        self.ui.stackedWidget.setCurrentIndex(1)

    def on_projectButton_1_toggled(self):
        self.go_to_project_screen()

    def on_projectButton_2_toggled(self):
        self.go_to_project_screen()

    def on_companyButton_1_toggled(self):
        self.ui.stackedWidget.setCurrentIndex(3)
  
    def on_companyButton_toggled(self):
        self.ui.stackedWidget.setCurrentIndex(3)

    ##HOME
    def on_gotoP_btn_2_clicked(self):
        self.go_to_project_screen()
    def on_gotoT_btn_2_clicked(self):
        self.ui.stackedWidget.setCurrentIndex(1)
    def on_gotoC_btn_2_clicked(self):
        self.ui.stackedWidget.setCurrentIndex(3)
    
    ##TEAM_깃 허브 연결
    def on_member_btn_6_clicked(self):
        webbrowser.open('https://github.com/jeoneunjin')
    def on_member_btn_7_clicked(self):
        webbrowser.open('https://github.com/bowook')
    def on_member_btn_8_clicked(self):
        webbrowser.open('https://github.com/sanghuniolsida')
    def on_member_btn_9_clicked(self):
        webbrowser.open('https://github.com/PROMLEE')
    def on_member_btn_10_clicked(self):
        webbrowser.open('https://github.com/easthee')
    
    ##로그인 안 된 상태에서의 로그인 화면으로 전환
    def go_to_login(self):
        self.hide()
        self.login = LoginScreen()
        
        ##user data 사용하기 위함
        self.login.set_main_window(self)      
        self.login.exec()
        self.show()
    def status_login(self, result_data):
        self.ui.user_name.setText(result_data['data']['user']['nickname'])
        self.ui.loginorout.setText('Logout')
        self.user_data = result_data
        self.user_token = self.user_data['data']['token']

##====================================================================================
    
##로그인 화면
class LoginScreen(QDialog):
    def __init__(self):
        super(LoginScreen, self).__init__()
        self.ui = Ui_Dialog()
        self.ui.setupUi(self)
        self.setWindowIcon(QtGui.QIcon(resource_path('Logo_11.png')))
        self.setWindowTitle('Login')
        self.setWindowFlag(Qt.FramelessWindowHint)
        self.show()
        self.ui.pushButton.clicked.connect(self.login)
        self.signup = False
        self.main_window = None
    
    def set_main_window(self, main_window):
        self.main_window = main_window
    ##로그인 X -> 메인 화면으로
    def go_to_main(self):
        self.close()
    ##로그인 X -> 계정만들기 화면으로
    def go_to_signup(self):
        self.signup = SignupScreen()
        self.signup.exec()
        self.show()
        self.getid =""
        self.getpw = ""
    ##로그인
    def login(self):
        self.getid = str(self.ui.lineEdit.text())
        self.getpw = str(self.ui.lineEdit_2.text())
        if self.getid == "":
            self.ui.label_2.setText('아이디를 입력해주세요.')
        if self.getpw == "":
            self.ui.label_4.setText('비밀번호를 입력해주세요.')
        if self.getid != "" and self.getid != "":
            res = requests.post(f'{url}/user/login', json={"id": self.getid,"pw": self.getpw})
            result = res.json()
            if result['result']:
                self.main_window.status_login(result)
                self.go_to_main()
            else:
                self.ui.label_5.setText('계정이 없거나 올바르지 않은 입력입니다.')

            

##================================================================================
##계정만들기 화면
class SignupScreen(QDialog):
    def __init__(self):
        super(SignupScreen, self).__init__()
        self.ui = uic.loadUi(resource_path("signup.ui"), self)
        self.setWindowIcon(QtGui.QIcon(resource_path('Logo_11.png')))
        self.setWindowTitle('Signup')
        self.setWindowFlag(Qt.FramelessWindowHint)
        self.show()

        self.pushButton.clicked.connect(self.nickname_check)
        self.pushButton_2.clicked.connect(self._id_check)
        self.create_btn.clicked.connect(self.create_account)
        self.canname = False
        self.canid = False
        self.getname = ""
        self.getid = ""
        
        
    ##로그인 화면으로
    def back_to_login(self):
        self.close()
    
    ##닉네임 중복 확인
    def nickname_check(self):
        self.getname = str(self.user_name.text())
        ##이름 작성 안 했을 경우
        if self.getname == "" :
            self.ui.name_check.setText('이름을 입력해주세요')
        else :
            res = requests.post(f'{url}/user/nickname', json={"nickname": self.getname})
            result = res.json()
            if result['result']:
                self.ui.name_check.setText('사용 가능한 이름입니다.')
                self.canname = True
            else :
                self.ui.name_check.setText('이미 존재하는 이름입니다.')
    ##아이디 중복 확인
    def _id_check(self):
        self.getid = str(self.user_id.text())
        ##이름 작성 안 했을 경우
        if self.getid == "" :
            self.ui.id_check.setText('아이디를 입력해주세요.')
        else :
            res = requests.post(f'{url}/user/id', json={"id": self.getid})
            result = res.json()
            if result['result']:
                self.ui.id_check.setText('사용 가능한 아이디입니다.')
                self.canid = True
            else :
                self.ui.id_check.setText('이미 존재하는 아이디입니다.')
    ##유효한 값인지 확인 + 계정 만들기
    def create_account(self):
        getpw = str(self.user_pw.text())
        if getpw != "" and self.canname and self.canid :
            res = requests.post(f'{url}/user/signup', json={"loginId": self.getid,"nickname":self.getname,"loginPw":getpw})
            result  = res.json()
            if result['result'] :
                about_event(self,'가입이 완료되었습니다.')
        elif getpw == "":
            self.ui.pw_check.setText('비밀번호를 입력하세요.')
        else:
            about_event(self,'가입에 실패하였습니다.')
##==============================================================================
##project detail 화면
class ProjDetailScreen(QDialog):
    def __init__(self, data, parent=None):
        super(ProjDetailScreen, self).__init__(parent)
        self.ui = uic.loadUi(resource_path("proj_detail.ui"), self)
        self.setWindowTitle(data['name'])
        self.setWindowIcon(QtGui.QIcon(resource_path('Logo_11.png')))
        self.show()
        self.ui.project_id.setText(str(data['id']))
        self.ui.project_name.setText(data['name'])
        self.ui.description.setText(data['description'])
        self.ui.add_issue.clicked.connect(self.create_issue)
        self.ui.issue_update.clicked.connect(self.update_issue)  
        self.ui.fix_btn.clicked.connect(self.fix_issue)
        
        self.ui.issue_table.cellClicked.connect(self.cell_clicked)
        self.ui.update_btn.clicked.connect(self.update_comment)
        self.main_window = None
        self.proj = data
        self.proj_admin = None
        self.user_token = parent.get_user_token()
        self.user_data = parent.user_data
        
        
        ##PL,TESTER,DEVELOPER 이름 저장할 변수들
        self.pl_list = None
        self.tester_list = None
        self.dev_list = None

        self.project_member()
        
        self.ui.add_member_btn.clicked.connect(self.add_member)
        ##table 값 세팅
        self.set_member_table()
        self.set_issue_data()
        ##Detail tab 비활성화
        self.ui.tabWidget.setTabEnabled(1,False)
        ##issue id 저장
        self.issue_id = None
        self.ui.assign_btn.clicked.connect(self.assigned_dev)
        
        self.ui.resoloved_btn.clicked.connect(self.resolved_issue)
        self.ui.add_comment_btn.clicked.connect(self.create_comment)
        
    ## project 멤버 정보 저장
    def project_member(self):
        headers = {}
        if self.user_token:
            headers = {
                    "Authorization": f"Bearer {self.user_token}"
            } 
        else:
            return
        proj_id = self.proj['id']
        res = requests.get(f'{url}/project/team/member/{proj_id}', headers=headers)
        result = res.json()
        if result :
            p = [] ##pl
            t = [] ##tester
            d = [] ##developer
            for item in result:
                r = item['role']
                n = item['nickname']
                ##Admin 찾기
                if r == "PL":
                    p.append(n)
                    if item['isAdmin']: 
                        self.proj_admin = n
                elif r == "TESTER":
                    t.append(n)
                elif r == "DEVELOPER":
                    d.append(n)
            self.pl_list = p
            self.tester_list = t
            self.dev_list = d
            
        else:
            about_event(self,"project 정보를 가져오지 못했습니다.")  

    ## setting member table data
    def set_member_table(self):
        headers = {}
        member = None
        if self.user_token:
            headers = {
                    "Authorization": f"Bearer {self.user_token}"
            } 
        else:
            return
        proj_id = self.proj['id']
        res = requests.get(f'{url}/project/team/member/{proj_id}', headers=headers)
        member = res.json()
        if member :   
            table = self.ui.member_table
            table.setRowCount(len(member))
            table.setColumnCount(2)
            for row in range(len(member)) :
                role = member[row]['role']
                name = member[row]['nickname']
                table.setItem(row,0,QTableWidgetItem(role))
                table.setItem(row,1,QTableWidgetItem(name))
        else:
            return
    ## update member table
    def update_member_table(self):
        ## 기존 데이터 삭제 
        self.ui.member_table.setRowCount(0)
        ## 다시 세팅
        self.set_member_table()
    ## add project member
    def add_member(self):
        user_name = ""
        if self.user_data != "":
            user_name = self.user_data['data']['user']['nickname'] 
        if self.proj_admin == None or self.user_data =="" :
            about_event(self,"추가 권한이 없습니다.")
        elif self.user_data !="" and user_name != self.proj_admin:
            about_event(self,"추가 권한이 없습니다..")
            return 
        ## proj_admin일 때만 멤버 추가가 가능
        elif self.user_data != "" and user_name == self.proj_admin :
            name = ""
            name = self.ui.member_nickname.text()        
            if name == "":
                about_event(self,"이름을 입력해주세요.")
                return 
            else:
                res = requests.post(f'{url}/user/nickname', json={"nickname":name})
                result = res.json()
            if result['result'] == True:
                about_event(self, '존재하지 않는 이름입니다.')
                return
            else :
                proj_id = self.proj['id']
                role = self.ui.member_role.currentText()
                headers = {}
                if self.user_token != None:
                    headers = {    
                        "Authorization": self.user_token,
                    }
                res2 = requests.post(f'{url}/project/team/{proj_id}', json={"nickname":name, "isAdmin": False, "role":role}, headers=headers)
                result2 = res2.json()
                if result2['isSuccess']:
                    temp = []
                    if role == "PL":
                        if self.pl_list:
                            temp = self.pl_list
                        temp.append(name)
                        self.pl_list = temp
                    if role == "TESTER":
                        if self.tester_list:
                            temp = self.tester_list
                        temp.append(name)
                        self.tester_list = temp
                    if role == "DEVELOPER":
                        if self.dev_list:
                            temp = self.dev_list
                        temp.append(name)
                        self.dev_list = temp
                    self.update_member_table()
                else:
                    about_event(self, '멤버 추가에 실패하였습니다.')
##==================================================================================                
##이슈 관련
    def set_issue_data(self):
        headers = {}
        if self.user_token:
            headers = {
                    "Authorization": f"Bearer {self.user_token}"
            } 
        else:
            return
        proj_id = self.proj['id']
        res = requests.get(f'{url}/issue/search/{proj_id}', headers=headers)
        result = res.json()
        if result:
            table = self.ui.issue_table
            table.setRowCount(len(result))
            table.setColumnCount(6)
            for row in range(len(result)):
                temp = result[row]
                table.setItem(row,0,QTableWidgetItem(str(temp['id'])))    
                table.setItem(row,1,QTableWidgetItem(temp['name']))
                table.setItem(row,2,QTableWidgetItem(temp['status']))
                table.setItem(row,3,QTableWidgetItem("Click!"))
                table.setItem(row,4,QTableWidgetItem(temp['description']))
                table.setItem(row,5,QTableWidgetItem(temp['priority']))
                table.setItem(row,6,QTableWidgetItem(formatted_datetime(temp['modDate'])))
        else:
            return
    ##이슈 목록 업데이트
    def update_issue(self):
        self.ui.issue_table.setRowCount(0)
        self.set_issue_data()
    ##이슈 생성
    def create_issue(self):    
        if self.user_token and self.user_data:
            data = self.user_data['data']['user']
            ##user 이름
            u_name = data['nickname']
            ##배정된 tester인가
            if self.tester_list:
                if u_name in self.tester_list :
                    headers = {    
                        "Authorization": self.user_token,
                    }
                    ##issue 이름, 설명, 중요도
                    name = self.ui.issue_name.text()
                    description = self.ui.issue_description.text()
                    priority = self.ui.priority.currentText()
                    if name != "" and description !="" and priority !="":
                        id = self.proj['id']
                        res = requests.post(f'{url}/issue/create/{id}', json={"name": name, "description": description, "priority": priority}, headers=headers)
                        result = res.json()
                        if result['isSuccess']:
                            i_id = result['result']['id']
                            res1 = requests.post(f'{url}/issue/reporter/{i_id}', json={"nickname": u_name}, headers=headers)
                            result1 = res1.json()   
                            if result1['isSuccess']:
                                ## 상태 NEW로 변경
                                res2 = requests.post(f'{url}/issue/state/{i_id}', json={"status": "NEW"}, headers=headers)
                                result2 = res2.json()   
                                if result2:
                                    self.update_issue()        
                                else:
                                    about_event(self, 'state 설정 실패')
                                    return                            
                            else:
                                about_event(self, '리포터 설정 실패')
                                return                        
                    else:
                        about_event(self,'모두 입력해주세요.')
                        return
                else:
                    about_event(self, 'Tester만 이슈를 추가할 수 있습니다.')
                    return
            else:
                about_event(self,f"tester list가 문제야{self.tester_list}")
                return
        else:
            about_event(self, "추가할 권한이 없습니다.")
            return 
        
        
    ## 이슈 정보 가져오기 !! ==================================
    def issue_info(self,id):
        headers = {}
        if self.user_token:
            headers = {
                    "Authorization": f"Bearer {self.user_token}"
            } 
        else:
            return
        res = requests.get(f'{url}/issue/info/{id}', headers=headers)
        result = res.json()
        return result
   ## =======================================================
   
    ## 셀 클릭 이벤트_1.이슈 상세 정보(Detail)로 이동
    def cell_clicked(self ,row, col):
        id = int(self.ui.issue_table.item(row, 0).text())
        ## detail 클릭
        if col == 3:
            state = self.ui.issue_table.item(row,2).text()
            if state == "CLOSED":
                about_event(self,"이미 닫힌 이슈입니다.") 
                return
            if not self.ui.tabWidget.isTabEnabled(1):
               self.ui.tabWidget.setTabEnabled(1,True) 
            self.set_detail(id)
        elif col == 2:
            self.closed_issue(id)
        else:
            return

    ## set detail screen
    def set_detail(self,id):
        info = self.issue_info(id)
        if info:    
            r = info['result']
            self.ui.issue_name_2.setText(r['name']) ##name
            self.ui.issue_reporter.setText(r['reporter']) ##reporter
            self.ui.issue_des.setText(r['description']) ## description
            self.ui.issue_status.setText(r['status']) ##status
            self.ui.label_18.setText(r['assignee']) ##Assignee
            self.ui.fixer.setText(r['fixer'])##fixer
            self.ui.tabWidget.setCurrentIndex(1)
            self.issue_id = r['id'] ##id 값 저장
            self.set_issue_comment()
        else:
            self.ui.tabWidget.setTabEnabled(1,False)
            about_event(self,"정보를 볼 수 없습니다.")
            return  
        
    ## set issue comment
    def set_issue_comment(self):
        if self.issue_id:
            headers = {}
            if self.user_token:
                headers = {
                        "Authorization": f"Bearer {self.user_token}"
                } 
            else:
                return
            res = requests.get(f'{url}/issue/comment/{self.issue_id}', headers=headers)
            result = res.json()
            if result:
                table = self.ui.comment_table
                table.setRowCount(len(result))
                table.setColumnCount(6)
                for row in range(len(result)):
                    temp = result[row]
                    table.setItem(row,0,QTableWidgetItem(str(temp['id'])))    
                    table.setItem(row,1,QTableWidgetItem(temp['nickname']))
                    table.setItem(row,2,QTableWidgetItem(temp['tag']))
                    table.setItem(row,3,QTableWidgetItem(temp['modDate']))
                    table.setItem(row,4,QTableWidgetItem(temp['comment']))
            else:
                return
        else:
            return
        
    ## update comment
    def update_comment(self):
        self.ui.comment_table.setRowCount(0)
        self.set_issue_comment()
        
    ## add comment
    def create_comment(self):
        if self.user_token and self.user_data:
            data = self.user_data['data']['user']
            ##user 이름
            u_name = data['nickname']
            ##프로젝트 멤버인가
            if u_name in self.tester_list or u_name in self.pl_list or u_name in self.dev_list:
                headers = {    
                    "Authorization": self.user_token,
                }
                ## comment, tag
                comment = self.ui.comment_input.text()
                tag = self.ui.tag.currentText()
                if tag != "" and comment != "":
                    res = requests.post(f'{url}/issue/comment/create/{self.issue_id}', json={"comment": comment, "tag": tag}, headers=headers)
                    result = res.json()
                    if result['isSuccess']:
                        self.update_comment()
                    else:
                        about_event(self, "comment 추가를 실패하였습니다.")
            else:
                about_event(self,"프로젝트 멤버가 아닙니다.")  
        else:
            about_event(self,"권한이 없습니다.")   
                       
    ##devleoper 배정
    def assigned_dev(self):
        ## 로그인을 했고 배정하려는 사람이 PL로 Project member인가
        if self.user_token and self.user_data['data']['user']['nickname'] in self.pl_list:
            if self.issue_id != None:
                issue = self.issue_info(self.issue_id)
                if issue['isSuccess'] and issue['result']['assignee'] == "not assigned":  
                    ## 값 가져오기
                    nickname  = self.ui.assign_name.text()    
                    ## project member에 dev 역할인 사람인가 확인
                    if nickname in self.dev_list:
                        ## 배정
                        headers = {
                            "Authorization": self.user_token,
                        }
                        res = requests.post(f'{url}/issue/assignee/{self.issue_id}', json={"nickname": nickname},headers=headers)
                        result = res.json()
                        if result['isSuccess']:
                            ## status 변경
                            res2 = requests.post(f'{url}/issue/state/{self.issue_id}', json={"status": "ASSIGNED"}, headers=headers)
                            result2 = res2.json()   
                            if result2['isSuccess']:
                                ## ui에서도 status Assigned로 변경 
                                self.update_issue()
                                self.set_detail(self.issue_id)
                            else:
                                about_event(self, "상태 변경에 실패하였습니다.")
                                
                    else :
                        about_event(self, "개발자 멤버가 아닙니다. 추가해주세요")
                        return
                else:
                    about_event(self,"이미 배정되어있습니다.")
                    return    
                
        else:
            about_event(self, "권한이 없습니다.")
    
    ## fix issue
    def fix_issue(self):
        if self.user_token and self.user_data:
            data = self.issue_info(self.issue_id)
            if data:
                nee = data['result']['assignee']
                fixer= data['result']['fixer']
                nickname = self.user_data['data']['user']['nickname']
                ## fixer가 아직 없고 배정된 개발자이어야함
                if fixer =="not fixed" and nee == nickname:
                    headers = {
                        "Authorization": self.user_token,
                    }
                    res = requests.post(f'{url}/issue/fixer/{self.issue_id}', json={"nickname": nickname},headers=headers)
                    result = res.json()
                    if result['isSuccess']:
                        self.ui.fixer.setText(nickname)
                        ## 이슈 상태 FIXED로 변경
                        res2 = requests.post(f'{url}/issue/state/{self.issue_id}', json={"status": "FIXED"}, headers=headers)
                        result2 = res2.json()   
                        if result2:
                            self.update_issue()
                            self.set_detail(self.issue_id)        
                        else:
                            about_event(self, 'state 설정 실패')
                            return    
                    else:
                        return
                else:
                    about_event(self,"권한이 없습니다.")
                    return
        else:
            about_event(self,"권한이 없습니다.")
            return 
    ## state resolved > reporter
    def resolved_issue(self):
        if self.user_token and self.user_data:
            data = self.issue_info(self.issue_id)
            if data:
                reporter= data['result']['reporter']
                state = data['result']['status']
                nickname = self.user_data['data']['user']['nickname']
                ## reporter 본인이어야함 fixed상태이어야함
                if reporter == nickname and state == "FIXED":
                    headers = {
                        "Authorization": self.user_token,
                    }
                    res2 = requests.post(f'{url}/issue/state/{self.issue_id}', json={"status": "RESOLVED"}, headers=headers)
                    result2 = res2.json()   
                    if result2:
                        self.update_issue()
                        self.set_detail(self.issue_id)        
                    else:
                        about_event(self, 'state 설정 실패')
                        return    
                else:
                    if reporter != nickname:
                        about_event(self,"권한이 없습니다.(REPORTER가 아님)")
                    else:
                        about_event(self,"FIXED 상태가 아닙니다.")
                    return
        else:
            about_event(self,"권한이 없습니다.")
            return   
    ## state closed > pl
    def closed_issue(self, id):
        if self.user_token and self.user_data:
            data = self.issue_info(id)
            if data and self.pl_list:
                state = data['result']['status']
                nickname = self.user_data['data']['user']['nickname']
                ## 프로젝트 PL 멤버이어야함, resolved 상태이어야함 
                if state == "RESOLVED" and nickname in self.pl_list:
                    headers = {
                        "Authorization": self.user_token,
                    }
                    res2 = requests.post(f'{url}/issue/state/{id}', json={"status": "CLOSED"}, headers=headers)
                    result2 = res2.json()   
                    if result2:
                        self.update_issue()
                    else:
                        about_event(self, 'state 설정 실패')
                        return    
                else:
                    if state != "RESOLVED":
                        about_event(self,"RESOLVED 상태가 아닙니다.")
                    else:    
                        about_event(self,"권한이 없습니다.")
                    return
        else:
            about_event(self,"권한이 없습니다.")
            return  
        
        
    
if __name__ == "__main__":
    app = QApplication(sys.argv)
    ## loading style file
    """
    with open("style.qss", "r") as style_file:
         style_str = style_file.read()
    app.setStyleSheet(style_str)
    """
    window = MainWindow()
    window.show()
    
    sys.exit(app.exec())
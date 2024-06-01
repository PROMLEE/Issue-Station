import sys
import json
import webbrowser
import requests
from PyQt5 import QtGui, uic
from PyQt5.QtWidgets import *
from PyQt5.QtCore import Qt, QDateTime
from IssueMgmt_ui import Ui_MainWindow
from login_ui import Ui_Dialog
from datetime import datetime


##http 요청 url
url = 'http://43.203.47.111:8080'

##json file 데이터 읽어오기
def getJsonData(local_url):
    with open(local_url, 'r') as file:
        json_data = json.load(file)
    return json_data
##dateTime 변환
def formatted_datetime(str):
    datetime_obj = datetime.fromisoformat(str.replace('Z', '+00:00'))
    formatted = datetime_obj.strftime("%Y-%m-%d %H:%M:%S")
    return formatted
    
class MainWindow(QMainWindow):
    def __init__(self):
        super(MainWindow, self).__init__()

        self.ui = Ui_MainWindow()
        self.ui.setupUi(self)
        self.setFixedSize(1200, 800)
        self.setWindowTitle("SE ISSUE MGMT")
        self.setWindowIcon(QtGui.QIcon('Logo_11.png'))
        self.ui.icon_only_widget.hide()
        self.ui.stackedWidget.setCurrentIndex(0)
        self.ui.homeButton_2.setChecked(True)
        self.ui.add_widget.hide()
        
        ## 유저 데이터,토큰
        self.user_data = ""
        self.user_token=""
        
        self.ui.loginorout.setText('Login')
        self.ui.user_name.setText('Unknown')
        self.ui.userBtn.clicked.connect(self.setUserWidget)
        self.ui.user_widget.hide()
        ## 검색 키워드
        self.search_keyword = ""
        self.ui.update_btn.clicked.connect(self.update_project)
        ## 로그인 화면 
        self.login = False
        ## track of uwer_widget hidden or not
        self.user_widget_hidden = True
        ## project 생성
        self.ui.add_btn_2.clicked.connect(self.create_project)
    
    ##
    def about_event(self,str):
        QMessageBox.about(self,str,str)
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
        if not search_text :
            self.ui.label_31.setText("검색어를 입력해주세요.")
    def on_my_btn_clicked(self):
       self.setDataTable()
##===================Project Screen===============================##
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
                self.about_event('권한이 없습니다.')
                return   
        res = requests.get(f'{url}/project/info/{id}', headers=headers)     
        result = res.json()
        if result['isSuccess']:
            detail = result['result']
            self.detail = ProjDetailScreen(detail,self)
            ##user data 사용하기 위함
            self.detail.set_main_window(self)
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
                res2 = requests.post(f'{url}/project/team/{projectId}', json={"nickname":self.user_data['user']['nickname'], "is_admin": True})
                result2 = res2.json()
                if result2['isSuccess']:
                    self.update_project()
                
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
        print(result_data)
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
        self.setWindowIcon(QtGui.QIcon('Logo_11.png'))
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
        self.ui = uic.loadUi("signup.ui", self)
        self.setWindowIcon(QtGui.QIcon('Logo_11.png'))
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
    ##계정 만들기 X -> 로그인 화면으로
    def back_to_login(self):
        self.close()
    ##계정 만들기 O -> 로그인 화면으로
    
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
                self.ui.label_4.setText('가입이 완료되었습니다.')
        elif getpw == "":
            self.ui.pw_check.setText('비밀번호를 입력하세요.')
        else:
            self.ui.label_4.setText('가입에 실패하였습니다.')
##==============================================================================
##project detail 화면
class ProjDetailScreen(QDialog):
    def __init__(self, dict, parent=None):
        super(ProjDetailScreen, self).__init__(parent)
        self.ui = uic.loadUi("proj_detail.ui", self)
        self.setWindowTitle(dict['name'])
        self.setWindowIcon(QtGui.QIcon('Logo_11.png'))
        self.show()
        self.ui.project_id.setText(str(dict['id']))
        self.ui.project_name.setText(dict['name'])
        self.ui.description.setText(dict['description'])
        self.ui.add_issue.clicked.connect(self.create_issue)
        
    ##user data 가져오는 역할
    def set_main_window(self, main_window):
        self.set_main_window = main_window
    
    ##이슈 목록 업데이트/조회
    def update_issue(self):
        return
    ##이슈 생성
    def create_issue(self):
        data = self.set_main_window.user_data
        ##if 로그인X,이슈 생성 X
        if data != "":
            name = self.ui.issue_name.text()
            description = self.ui.issue_name.text()
            id = data['data']['user']['loginId']
            res = requests.post(f'{url}/isuue/create/{id}', json={"name": name, "description": description})
            result = res.json()
            if result['isSuccess']:
                self.update_issue()
                
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
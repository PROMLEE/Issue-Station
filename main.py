import sys
import json
import webbrowser
from PyQt5 import QtGui, uic
from PyQt5.QtWidgets import *
from PyQt5.QtCore import Qt
from IssueMgmt_ui import Ui_MainWindow
from login_ui import Ui_Dialog

##json file 데이터 읽어오기
def getJsonData(local_url):
    with open(local_url, 'r') as file:
        json_data = json.load(file)
    return json_data

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
        
        self.ui.loginorout.setText('Login')
        self.ui.user_name.setText('Unknown')
        self.ui.userBtn.clicked.connect(self.setUserWidget)
        self.ui.user_widget.hide()
        ## 검색 키워드
        self.search_keyword = None
        ## 로그인 화면 
        self.login = False
        ## track of uwer_widget hidden or not
        self.user_widget_hidden = True
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
        if not search_text :
            self.ui.label_31.setText("검색어를 입력해주세요.")
##===================Project Screen===============================##
    ## project screen 
    def go_to_project_screen(self):
        self.ui.stackedWidget.setCurrentIndex(2)
        self.setDataTable()
            
    ## proj detail screen 
    def go_to_projdetail(self,dict):
        self.detail = ProjDetailScreen(dict,self)
        self.detail.exec()

    ## Fuction for setting project data
    def setDataTable(self):
        ## search keyword가 있을 경우
        if self.search_keyword:
            keyword = self.search_keyword
        table_widget = self.ui.project_table
        json_data = getJsonData('./assets/projectlist_dump.json')
        proj_list = json_data['projectlist']
        table_widget.setRowCount(len(proj_list))
        table_widget.setColumnCount(7)
        for row in range(len(proj_list)) :
            ##issue 버튼 생성
            button_name = 'issue_btn_' + str(row)
            self.button = QPushButton("Open", self)
            self.button.setAccessibleName(button_name)
            self.button.clicked.connect(lambda _, dict = proj_list[row] : self.go_to_projdetail(dict))
 
            ##setting table data
            table_widget.setItem(row,0,QTableWidgetItem(str(proj_list[row]['projectid'])))
            table_widget.setItem(row,1,QTableWidgetItem(proj_list[row]['name']))
            if proj_list[row]['private'] :
                table_widget.setItem(row,2,QTableWidgetItem("private"))
            else :
                table_widget.setItem(row,2,QTableWidgetItem("public"))
            table_widget.setItem(row,3,QTableWidgetItem(proj_list[row]['admin']))            
            table_widget.setCellWidget(row,4,self.button) 
            table_widget.setItem(row,5,QTableWidgetItem(proj_list[row]['initdate']))
            table_widget.setItem(row,6,QTableWidgetItem(proj_list[row]['description']))  
        
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
        self.login.exec()
        self.show()
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
        self.signup = False
    ##로그인 X -> 메인 화면으로
    def go_to_main(self):
        self.close()
    ##로그인 X -> 계정만들기 화면으로
    def go_to_signup(self):
        self.signup = SignupScreen()
        self.signup.exec()
        self.show()

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
    ##계정 만들기 X -> 로그인 화면으로
    def back_to_login(self):
        self.close()
    ##계정 만들기 O -> 로그인 화면으로
##==============================================================================
##project detail 화면
class ProjDetailScreen(QDialog):
    def __init__(self, dict, parent=None):
        super(ProjDetailScreen, self).__init__(parent)
        self.ui = uic.loadUi("proj_detail.ui", self)
        self.setWindowTitle(dict['name'])
        self.setWindowIcon(QtGui.QIcon('Logo_11.png'))
        self.show()
        self.ui.project_id.setText(str(dict['projectid']))
        self.ui.project_name.setText(dict['name'])
        self.ui.description.setText(dict['description'])
        
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
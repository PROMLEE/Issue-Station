import sys
import json
import webbrowser
from PyQt5 import QtGui
from PyQt5.QtWidgets import *
from PyQt5.QtCore import Qt

from IssueMgmt_ui import Ui_MainWindow
from login_ui import Ui_Dialog

##json file 데이터 읽어오기
def getJsonData():
    with open('./assets/projectlist_dump.json') as file:
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

    ## Function for searching
    def on_searchBtn_clicked(self):
        search_text = self.ui.searchInput.text().strip()
        if search_text :
            self.ui.label_31.setText('')
            ##self.ui.searchResult.setText(search_text)
            self.ui.stackedWidget.setCurrentIndex(2)
        if not search_text :
            self.ui.label_31.setText("검색어를 입력해주세요.")
            
    ## Function for changing page to user page
    def on_user_btn_clicked(self):
        self.ui.stackedWidget.setCurrentIndex(5)

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
        self.ui.stackedWidget.setCurrentIndex(2)

    def on_projectButton_2_toggled(self):
        self.ui.stackedWidget.setCurrentIndex(2)

    def on_companyButton_1_toggled(self):
        self.ui.stackedWidget.setCurrentIndex(3)

    def on_companyButton_toggled(self):
        self.ui.stackedWidget.setCurrentIndex(3)
    
    ##HOME
    def on_gotoP_btn_2_clicked(self):
        self.ui.stackedWidget.setCurrentIndex(2)
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
    
    ##로그인 안 된 상태에서의 로그인 화면으로 전환(임시)
    def go_to_login(self):
        self.hide()
        self.second = LoginScreen()      
        self.second.exec()
        self.show()
    
#로그인 화면
class LoginScreen(QDialog, QWidget):
    def __init__(self):
        super(LoginScreen, self).__init__()
        self.ui = Ui_Dialog()
        self.ui.setupUi(self)
        self.setWindowFlag(Qt.FramelessWindowHint)
        self.show()
    ## 로그인 없이 메인 화면으로
    def go_to_main(self):
        self.close()

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
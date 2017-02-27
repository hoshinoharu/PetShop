package shop.mgzj.petshop.dto;

/**
 * Created by 星野悠 on 2017/2/24.
 */

public class AjaxResultCode {
    //注册失败
    public static int REGISTER_FAIL = 0;

    //注册成功
    public static int REGISTER_SUCCESS = 1;

    //重复的用户名
    public static int REPEAT_USERNAME = 2;

    //重复的邮箱
    public static int REPEAT_USERMAIL = 3;

    //登录失败
    public static int LOGIN_FAIL = 4;

    //登录成功
    public static int LOGIN_SUCCESS = 5;

    //首页 输入框 内容
    public static int Search_Content = 6;

    //首页 输入框下 点击热词
    public static int HotWord_Click = 7;

    //首页 输入框下 未点击热词
    public static int HotWord_UnClick = 8;

    //首页 击中子模块
    public static int SubjectName_Click = 9;

    //首页 未击中子模块
    public static int SubjectName_UnClick = 10;

    //商品选择界面 导航名 点击
    public static int NavigationName_Click = 11;

    //当前登录的用户ID
    public static int Login_UserID = 12;

    //退出登录的用户ID 清空状态
    public static int Exit_UserID = 13;

    //数据库错误
    public static int DB_ERROR = 1000;
}
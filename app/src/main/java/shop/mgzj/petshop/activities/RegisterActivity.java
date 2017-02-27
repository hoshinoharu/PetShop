package shop.mgzj.petshop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import shop.mgzj.petshop.R;
import shop.mgzj.petshop.dto.AjaxResult;
import shop.mgzj.petshop.dto.AjaxResultCode;
import shop.mgzj.petshop.harubase.base.AutoInjecter;
import shop.mgzj.petshop.harubase.base.HaruActivity;
import shop.mgzj.petshop.harubase.components.HaruToolbarLayout;
import shop.mgzj.petshop.harubase.tools.ScreenTool;
import shop.mgzj.petshop.service.Service;
import shop.mgzj.petshop.service.UserService;
import shop.mgzj.petshop.vo.User;

/**
 * Created by 星野悠 on 2017/2/23.
 */

public class RegisterActivity extends HaruActivity {

    public static final int RESPONSE_REGISTER = 0 ;

    /*视图控件*/
    @AutoInjecter.ViewInject(R.id.toolbarLayout) private HaruToolbarLayout toolbarLayout ;
    @AutoInjecter.ViewInject(R.id.edTxt_userName) private EditText userName ;
    @AutoInjecter.ViewInject(R.id.edTxt_password) private EditText password ;
    @AutoInjecter.ViewInject(R.id.edTxt_firstPwd) private EditText firstPwd ;
    @AutoInjecter.ViewInject(R.id.edTxt_email) private EditText email ;
    @Override
    public int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenTool.noStatusBar(this);
        this.initListener();
    }
    private void initListener(){
        toolbarLayout.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }) ;
    }

    public void preRegister(View view){
        User user = User.obtain() ;
        user.userName = userName.getText().toString() ;
        user.email = email.getText().toString() ;
        user.password = password.getText().toString() ;
        String firstPassword = firstPwd.getText().toString() ;
        realRegister(user, firstPassword);
    }

    private void realRegister(final User user, String firstPassword){
        if(!verifyRegisterInfo(user, firstPassword)){
            return;
        }
        UserService.register(user, new Service.CallBack<AjaxResult>() {
            @Override
            public void onResponse(AjaxResult ajaxResult) {
                if(ajaxResult.Code == AjaxResultCode.REGISTER_SUCCESS){
                    shortToast("注册成功");
                    Intent intent = new Intent() ;
                    intent.putExtra(LoginActivity.USER_INTENT_FLAG, user) ;
                    setResult(RESPONSE_REGISTER,intent);
                    finish();
                }else {
                    shortToast(ajaxResult.Info);
                }
            }

            @Override
            public void onFail(Exception e) {
                errorToast("服务器错误");
            }
        });
    }

    private boolean verifyRegisterInfo(User user, String firstPassword) {
        if(user.userName == null || user.userName.trim().equals("")){
            noneUserName();
            return false;
        }
        if(firstPassword.trim().equals("")){
            noneFirstpwd();
            return false ;
        }
        if(user.password == null || user.password.trim().equals("")){
            nonePassword();
            return false ;
        }
        if(!user.password.equals(firstPassword)){
            pwdNotEqual();
            return false ;
        }
        if(!user.userName.matches("[a-zA-Z0-9]{6,24}?")){
            errorUserName();
            return false ;
        }
        if(!user.email.matches("([a-z0-9]*[-_\\.]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\\.][a-z]{2,3}([\\.][a-z]{2})?")){
            errorEmail();
            return false ;
        }
        if(!firstPassword.matches(".{8,}?")){
            errorFirstPwd();
            return false ;
        }
        if(!user.password.matches(".{8,}?")){
            errorPassword();
            return false ;
        }
        return true ;
    }

    private void errorEmail() {
        errorToast("邮箱格式错误");
    }


    private void errorUserName() {
        errorToast("用户名格式错误");
    }

    private void errorFirstPwd() {
        errorToast("密码格式错误");
    }

    private void errorPassword() {
        errorToast("密码格式错误");
    }

    private void pwdNotEqual() {
        errorToast("两次密码输入不一致");
    }

    private void noneFirstpwd() {
        errorToast("密码不能为空");
    }

    private void nonePassword() {
        errorToast("密码不能为空");
    }
    private void noneUserName(){
        errorToast("用户名不能为空");
    }


}

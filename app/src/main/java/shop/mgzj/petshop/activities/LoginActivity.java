package shop.mgzj.petshop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import shop.mgzj.petshop.R;
import shop.mgzj.petshop.dto.AjaxResult;
import shop.mgzj.petshop.dto.AjaxResultCode;
import shop.mgzj.petshop.harubase.base.AutoInjecter;
import shop.mgzj.petshop.harubase.base.HaruActivity;
import shop.mgzj.petshop.harubase.components.HaruToolbarLayout;
import shop.mgzj.petshop.harubase.tools.HLog;
import shop.mgzj.petshop.harubase.tools.ScreenTool;
import shop.mgzj.petshop.service.Service;
import shop.mgzj.petshop.service.UserService;
import shop.mgzj.petshop.vo.User;

public class LoginActivity extends HaruActivity {

    public static final String USER_INTENT_FLAG = "userIntent" ;
    public static final int REQUEST_REGISTER = 0 ;

    //视图
    @AutoInjecter.ViewInject(R.id.activity_login) private View content ;
    @AutoInjecter.ViewInject(R.id.toolbarLayout) private HaruToolbarLayout toolbarLayout ;
    @AutoInjecter.ViewInject(R.id.edTxt_userName) private EditText userName ;
    @AutoInjecter.ViewInject(R.id.edTxt_password) private EditText paddword ;
    private Toolbar toolbar ;
    //


    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScreenTool.noStatusBar(this);
        super.onCreate(savedInstanceState);
        this.toolbar = toolbarLayout.getToolbar() ;
        this.setSupportActionBar(this.toolbar);
        Intent intent = this.getIntent() ;
        User user = (User) intent.getSerializableExtra(USER_INTENT_FLAG);
        realLogin(user);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.register:
                startOtherHaruAcForResult(RegisterActivity.class, REQUEST_REGISTER);
                return true ;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_REGISTER){
            if(resultCode == RegisterActivity.RESPONSE_REGISTER){
                User user = (User) data.getSerializableExtra(USER_INTENT_FLAG);
                HLog.e("TAG", "result", user);
                this.realLogin(user);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void preLogin(View view){
        User user = User.obtain() ;
        user.userName = userName.getText().toString() ;
        user.password = paddword.getText().toString() ;
        realLogin(user);
    }

    public boolean verifyUserInfo(User user){
        if(user == null){
            return false;
        }
        if(user.userName == null || user.userName.trim().equals("")){
            noneUserName();
            return false;
        }
        if(user.password == null || user.password.trim().equals("")){
            nonePassword();
            return false ;
        }
        return true ;
    }

    private void nonePassword() {
        shortToast("密码不能为空");
    }
    private void noneUserName(){
        shortToast("用户名不能为空");
    }
    public void realLogin(User user){
        if(!verifyUserInfo(user)){
            return;
        }
        HLog.e("TAG", "登录", user);
        UserService.login(user, new Service.CallBack<AjaxResult>() {
            @Override
            public void onResponse(AjaxResult ajaxResult) {
                if(ajaxResult.Code == AjaxResultCode.LOGIN_SUCCESS){
                    shortToast("登录成功");
                }else {
                    errorToast(ajaxResult.Info);
                }
            }

            @Override
            public void onFail(Exception e) {
                errorToast("服务器错误");
            }
        });
    }
}


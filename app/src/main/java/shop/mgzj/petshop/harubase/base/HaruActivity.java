package shop.mgzj.petshop.harubase.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by 星野悠 on 2017/2/23.
 */

public abstract class HaruActivity extends AppCompatActivity implements AutoInjecter.AutoInjectable {

    @Override
    public View findInjectViewById(int resId) {
        return this.findViewById(resId);
    }

    public abstract int getContentViewId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getContentViewId());
        AutoInjecter.autoInjectAllField(this);
    }

    protected void shortToast(String msg) {
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
    }
    protected void longToast(String msg) {
        Toast.makeText(this, msg,Toast.LENGTH_LONG).show();
    }
    protected void errorToast(String msg){
        Toast.makeText(this, msg,Toast.LENGTH_LONG).show();
    }

    protected void startOtherHaruAc(Class<? extends HaruActivity> haruAcClass){
        this.startActivity(new Intent(this, haruAcClass));
    }

    protected void startOtherHaruAcForResult(Class<? extends HaruActivity> haruAcClass, int requestCode){
        this.startActivityForResult(new Intent(this,haruAcClass), requestCode);
    }
}

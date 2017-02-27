package shop.mgzj.petshop.harubase.base;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * Created by 星野悠 on 2017/2/23.
 */

public class AutoInjecter {

    public interface AutoInjectable{
        View findInjectViewById(int resId) ;
    }

    @Target(ElementType.FIELD)//表示用在字段上
    @Retention(RetentionPolicy.RUNTIME)//表示在生命周期是运行时
    public  @interface ViewInject {
        int value() default 0;
    }
    public static void autoInjectAllField(AutoInjectable autoInjectable) {
        try {
            Class<?> clazz = autoInjectable.getClass();
            Field[] fields = clazz.getDeclaredFields();//获得Activity中声明的字段
            for (Field field : fields) {
                // 查看这个字段是否有我们自定义的注解类标志的
                if (field.isAnnotationPresent(ViewInject.class)) {
                    ViewInject inject = field.getAnnotation(ViewInject.class);
                    int id = inject.value();
                    if (id > 0) {
                        field.setAccessible(true);
                        field.set(autoInjectable, autoInjectable.findInjectViewById(id));//给我们要找的字段设置值
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}

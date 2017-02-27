package shop.mgzj.petshop.harubase.tools;

import android.app.Activity;
import android.view.ViewGroup;

/**
 * Created by 星野悠 on 2017/2/2.
 */

public class ActivityTool {

    public static ViewGroup getRootView(Activity activity){
        return (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
    }
}

package shop.mgzj.petshop.harubase.components;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

import java.util.ArrayList;

/**
 * Created by 星野悠 on 2017/2/25.
 */

public class HaruScrollView extends NestedScrollView {

    private ArrayList<OnScrollChangeListener> listenerArrayList ;

    public interface OnScrollChangeListener{
        void onScrollChange(HaruScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) ;
    }

    public HaruScrollView(Context context) {
        super(context);
    }

    public HaruScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HaruScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addOnScrollChangeListener(OnScrollChangeListener listener){
        if(listener == null){
            return;
        }
        if(listenerArrayList == null){
            listenerArrayList = new ArrayList<>() ;
            this.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    for(OnScrollChangeListener l : listenerArrayList){
                        l.onScrollChange(HaruScrollView.this,  scrollX, scrollY,  oldScrollX, oldScrollY);
                    }
                }
            });
        }
        this.listenerArrayList.add(listener) ;
    }

    @Override
    @Deprecated
    public void setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener l) {
        super.setOnScrollChangeListener(l);
    }
}

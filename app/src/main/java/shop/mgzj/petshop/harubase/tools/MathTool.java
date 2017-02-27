package shop.mgzj.petshop.harubase.tools;

import java.util.Random;

/**
 * Created by 星野悠 on 2017/1/4.
 */

public class MathTool {
    /**
     *
     * @param px 原点x坐标
     * @param py 原点y坐标
     * @param mx 当前点x坐标
     * @param my 当前点y坐标
     * @return
     */
    public static double getAngle(float px, float py, float mx, float my){
        double x = Math.abs(px-mx);
        double y = Math.abs(py-my);
        double z = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
        double cos = y/z;
        double radina = Math.acos(cos);//用反三角函数求弧度
        double angle = Math.floor(180/(Math.PI/radina));//将弧度转换成角度

        if(mx>px&&my>py){//鼠标在第四象限
            angle = 180 - angle;
        }

        if(mx==px&&my>py){//鼠标在y轴负方向上
            angle = 180;
        }

        if(mx>px&&my==py){//鼠标在x轴正方向上
            angle = 90;
        }

        if(mx<px&&my>py){//鼠标在第三象限
            angle = 180+angle;
        }

        if(mx<px&&my==py){//鼠标在x轴负方向
            angle = 270;
        }

        if(mx<px&&my<py){//鼠标在第二象限
            angle = 360 - angle;
        }
        return angle;
    }
    public static float[] roate(double x1, double y1, double x0, double y0, double angle){
        float[] point = new float[2];
        angle=new Float(Math.toRadians(angle));
        point[0]=new Float((x1-x0)*Math.cos(angle) +(y1-y0)*Math.sin(angle)+x0);
        point[1]=new Float(-(x1-x0)*Math.sin(angle) + (y1-y0)*Math.cos(angle)+y0);
        return point ;
    }

    public static float getBiggerWithAbs(float a, float b){
        if(Math.abs(a) > Math.abs(b)){
            return  a ;
        }
        return b ;
    }

    private static Random random ;
    public static double randomDouble(){
        if(random == null){
            random = new Random() ;
        }
        return random.nextDouble() ;
    }

    public static double getDistance(int ox, int oy, int px, int py){
        double _x = Math.abs(ox - px);
        double _y = Math.abs(oy - py);
        return Math.sqrt(_x*_x+_y*_y);
    }

    public static float max(float... f){
        if(f.length <= 0){
            return 0 ;
        }
        float max = f[0] ;
        for(int i = 1; i < f.length; i++){
            max = max > f[i]?max:f[i] ;
        }
        return max ;
    }
}

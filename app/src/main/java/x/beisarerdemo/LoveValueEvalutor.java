package x.beisarerdemo;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;

/**
 * Created by xushengfu on 16/9/23.
 */
public class LoveValueEvalutor implements TypeEvaluator<PointF> {

    private PointF pointF1;
    private PointF pointF2;

    public LoveValueEvalutor(PointF pointF1,PointF pointF2){
        this.pointF1=pointF1;
        this.pointF2=pointF2;
    }


    @Override
    public PointF evaluate(float t, PointF startValue, PointF endValue) {
        PointF pointF=new PointF();
        pointF.x=startValue.x*(1-t)*(1-t)*(1-t)+3*pointF1.x*(1-t)*(1-t)*t+pointF2.x*(1-t)*t*t*3+endValue.x*t*t*t;
        pointF.y=startValue.y*(1-t)*(1-t)*(1-t)+3*pointF1.y*(1-t)*(1-t)*t+pointF2.y*(1-t)*t*t*3+endValue.y*t*t*t;
        return pointF;
    }
}

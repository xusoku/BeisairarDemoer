package x.beisarerdemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Created by xushengfu on 16/9/23.
 */
public class LoveLayout extends RelativeLayout {

    private Drawable [] drawables=new Drawable[3];
    private RelativeLayout.LayoutParams params;

    private int drawWidth;
    private int drawHeight;
    private  int width;
    private  int height;

    private Random random=new Random();
    public LoveLayout(Context context) {
        this(context,null);
    }

    public LoveLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        drawables[0]=getResources().getDrawable(R.drawable.ic_launcher);
        drawables[1]=getResources().getDrawable(R.drawable.ic_launcher);
        drawables[2]=getResources().getDrawable(R.drawable.ic_launcher);
        drawWidth=drawables[0].getIntrinsicWidth();
        drawHeight=drawables[0].getIntrinsicHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=this.getMeasuredWidth();
        height=this.getMeasuredHeight();
    }

    private AnimatorSet getObjectAnimator(ImageView imageView,PointF pointF) {
        ObjectAnimator scalex=ObjectAnimator.ofFloat(imageView,"scaleX",0.2f,1.0f);
        ObjectAnimator scaley=ObjectAnimator.ofFloat(imageView,"scaleY",0.2f,1.0f);
        ObjectAnimator alpha=ObjectAnimator.ofFloat(imageView,"alpha",0.2f,1.0f);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(scalex, scaley, alpha);
        animatorSet.setDuration(500);
        ValueAnimator beisarer=getValueAnimator(imageView,pointF);
        AnimatorSet set=new AnimatorSet();
        set.playSequentially(animatorSet,beisarer);
        set.setTarget(imageView);
        return set;
    }

    private ValueAnimator getValueAnimator(final ImageView imageView, PointF pointF) {
        PointF pointF0=new PointF();
        pointF0.x=pointF.x-drawWidth/2;
        pointF0.y=pointF.y-drawHeight/2;

        PointF pointF1=getTogglePoint(1, pointF);
        PointF pointF2=getTogglePoint(2, pointF);
        PointF pointF3=new PointF(random.nextInt(width),0);
        LoveValueEvalutor valueEvalutor=new LoveValueEvalutor(pointF1,pointF2);

        ValueAnimator valueAnimator=ValueAnimator.ofObject(valueEvalutor,pointF0,pointF3);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                PointF point = (PointF) animation.getAnimatedValue();
                imageView.setX(point.x);
                imageView.setY(point.y);
                imageView.setAlpha(1-animation.getAnimatedFraction());
            }
        });
        return valueAnimator;
    }

    private PointF getTogglePoint(int i, PointF pointF) {
        PointF point=new PointF();
        point.x=random.nextInt(width);
        int reacHeight = (int) (pointF.y / 2);
        if(i==1){
            point.y=reacHeight+random.nextInt(reacHeight);
        }else if(i==2){
            point.y=random.nextInt(reacHeight);
        }
        return point;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()==MotionEvent.ACTION_DOWN){
            PointF pointF=new PointF();
            pointF.x=event.getX();
            pointF.y=event.getY();
            addLove(pointF);
        }
        return true;
    }

    private void addLove(PointF pointF) {
        params=new LayoutParams(drawWidth,drawHeight);
        params.leftMargin= (int) (pointF.x-drawWidth/2);
        params.topMargin= (int) (pointF.y-drawHeight/2);
        ImageView imageView=new ImageView(getContext());
        AnimatorSet animatorSet=getObjectAnimator(imageView,pointF);
        imageView.setImageDrawable(drawables[random.nextInt(3)]);
        addView(imageView, params);
        animatorSet.start();
    }
}

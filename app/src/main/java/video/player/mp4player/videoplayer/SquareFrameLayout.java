package video.player.mp4player.videoplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SquareFrameLayout extends FrameLayout {

    public SquareFrameLayout(Context context) {
        super(context);
    }


    public SquareFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SquareFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }





    @Override public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       if(widthMeasureSpec<heightMeasureSpec){
           super.onMeasure(widthMeasureSpec, widthMeasureSpec);
           int size = MeasureSpec.getSize(widthMeasureSpec);
           setMeasuredDimension(size, size);
       } else {
           super.onMeasure(heightMeasureSpec, heightMeasureSpec);
           int size = MeasureSpec.getSize(heightMeasureSpec);
           setMeasuredDimension(size, size);
       }
    }

}

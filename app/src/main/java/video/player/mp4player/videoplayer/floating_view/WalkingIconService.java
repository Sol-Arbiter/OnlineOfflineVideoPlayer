package video.player.mp4player.videoplayer.floating_view;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import video.player.mp4player.videoplayer.R;

public class WalkingIconService extends Service {
    private WindowManager mWindowManager;
    private ImageView image;

    public void onCreate() {
        super.onCreate();
        image = new ImageView(this);

        image.setImageResource(R.color.colorPrimary);

        mWindowManager = (WindowManager)getSystemService(WINDOW_SERVICE);

        final WindowManager.LayoutParams paramsF = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        paramsF.gravity = Gravity.TOP | Gravity.LEFT;
        paramsF.x=0;
        paramsF.y=100;
        mWindowManager.addView(image, paramsF);

        try{

            image.setOnTouchListener(new View.OnTouchListener() {
                WindowManager.LayoutParams paramsT = paramsF;
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch(event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            initialX = paramsF.x;
                            initialY = paramsF.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            paramsF.x = initialX + (int) (event.getRawX() - initialTouchX);
                            paramsF.y = initialY + (int) (event.getRawY() - initialTouchY);
                            mWindowManager.updateViewLayout(v, paramsF);
                            break;
                    }
                    return false;
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}

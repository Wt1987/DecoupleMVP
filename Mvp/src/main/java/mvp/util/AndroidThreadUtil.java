package mvp.util;

import android.os.Looper;

/**
 * author : taowang
 * date :2018/7/21
 * description:
 **/
public class AndroidThreadUtil {


    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}

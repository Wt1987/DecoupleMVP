package mvp.delegate;

/**
 * author : taowang
 * date :2018/7/21
 * description:
 **/
public interface ActivityDelegate {

    void onCreate();

    void onPause();

    void onResume();

    void onStop();

    void onDestroy();

}

package base.util;

/**
 * author : taowang
 * date :2018/7/21
 * description:
 **/
public interface PermissionsResultListener {
    //成功
    void onSuccessful(int[] grantResults);

    //失败
    void onFailure();
}

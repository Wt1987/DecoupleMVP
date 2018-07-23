package mvp.present;


import mvp.iview.IBaseView;

/**
 * author : taowang
 * date :2018/7/21
 * description:
 **/
public interface IBasePresenter<V extends IBaseView> {

    /**
     * 绑定view，一般在初始化中调用该方法
     */
    void attachView(V mvpView);

    /**
     * 断开view，一般在onDestroy中调用
     */
    void detachView();


    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    boolean isViewAttached();

    /**
     * 获取连接的view
     */
    V getView();
}

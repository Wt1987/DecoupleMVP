package mvp.present;



import android.os.Handler;
import android.os.Looper;

import java.lang.ref.WeakReference;

import mvp.iview.IBaseView;
import mvp.util.AndroidThreadUtil;

/**
 * author : taowang
 * date :2018/7/21
 * description:
 **/
public class BasePresenterImpl<V extends IBaseView> implements IBasePresenter<V> {

    private WeakReference<V> mView;

    protected Handler mainHandler = new Handler(Looper.getMainLooper());

    /**
     * 绑定view，一般在初始化中调用该方法
     *
     * @param mvpView
     */
    @Override
    public void attachView(V mvpView) {
        if (mvpView == null) {
            throw new NullPointerException("view can not be null when in attachview() in BasePresenter");

        } else if (mView == null) {

            //将View置为弱引用，当view被销毁回收时，
            // 依赖于view的对象（即Presenter）也会被回收，而不会造成内存泄漏
            this.mView = new WeakReference<>(mvpView);
        }

    }

    /**
     * 断开view，一般在onDestroy中调用
     */
    @Override
    public void detachView() {

        if (mView != null && mView.get() != null) {
            mView.clear();
        }
        mView = null;
    }

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    @Override
    public boolean isViewAttached() {
        return mView != null && mView.get() != null;
    }

    /**
     * 获取连接的view
     */
    @Override
    public V getView() {

        if(isViewAttached()){
            if (mView != null) {
                return mView.get();
            }
        }else {
            throw new NullPointerException("have you ever called attachView() in BasePresenter");
        }


        return null;
    }

    public boolean isUiThread(){
        return AndroidThreadUtil.isMainThread();
    }

    public void uiOperations(Runnable runnable){

        mainHandler.post(runnable);
    }

}

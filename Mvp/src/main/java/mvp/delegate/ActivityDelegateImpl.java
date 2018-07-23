package mvp.delegate;

import mvp.iview.IBaseView;
import mvp.present.IBasePresenter;

/**
 * author : taowang
 * date :2018/7/21
 * description:
 **/
public class ActivityDelegateImpl<P extends IBasePresenter, V extends IBaseView> implements ActivityDelegate {
    private BaseDelegateCallback<P, V> mDelegateCallback;
    private MvpInternalDelegate<P, V> mvpInternalDelegate;

    //传入BaseDelegateCallback 去控制Presenter
    public ActivityDelegateImpl(BaseDelegateCallback<P, V> mDelegateCallback) {
        if (mDelegateCallback == null)
            throw new NullPointerException("the ActivityDelegateImpl is null");
        this.mDelegateCallback = mDelegateCallback;
        mvpInternalDelegate = new MvpInternalDelegate<>(this.mDelegateCallback);

    }

    @Override
    public void onCreate() {
        mvpInternalDelegate.createPresenter();
        mvpInternalDelegate.attachView();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        mvpInternalDelegate.detachView();
    }
}

package mvp.delegate;

import mvp.iview.IBaseView;
import mvp.present.IBasePresenter;

/**
 * author : taowang
 * date :2018/7/21
 * description:
 **/
public class MvpInternalDelegate<P extends IBasePresenter, V extends IBaseView> {

    private BaseDelegateCallback<P, V> callback;

    protected MvpInternalDelegate(BaseDelegateCallback<P, V> callback) {
        this.callback = callback;
    }


    public P createPresenter() {
        P p = callback.getPresenter();
        if (p == null){
            p = callback.createPresenter();
        }

        if (p == null){
            throw new NullPointerException("callback.createPresenter() is null in MvpInternalDelegate");
        }
        return p;
    }

    @SuppressWarnings("unchecked")
    public void attachView() {

        callback.getPresenter().attachView(callback.getMvpView());
    }

    public void detachView() {
        callback.getPresenter().detachView();
    }

}

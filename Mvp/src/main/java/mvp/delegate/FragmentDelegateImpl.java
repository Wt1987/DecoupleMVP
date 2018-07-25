package mvp.delegate;

import mvp.iview.IBaseView;
import mvp.present.IBasePresenter;

/**
 * author : taowang
 * date :2018/7/21
 * description:
 **/
public class FragmentDelegateImpl<P extends IBasePresenter, V extends IBaseView> implements FragmentDelegate {
    private BaseDelegateCallback<P, V> baseMvpDelegateCallback;
    private MvpInternalDelegate<P, V> mvpInternalDelegate;

    public FragmentDelegateImpl(BaseDelegateCallback<P, V> basemvpDelegateCallback) {
        if (basemvpDelegateCallback == null){
            throw new NullPointerException("the baseDelegateCallback in FragmentDelegate is null");
        }
        this.baseMvpDelegateCallback = basemvpDelegateCallback;
        mvpInternalDelegate = new MvpInternalDelegate<>(this.baseMvpDelegateCallback);

    }

    @Override
    public void onCreateView() {

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
    public void onDestroyView() {

    }
}

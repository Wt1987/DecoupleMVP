package mvp.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import base.eventBus.BusImpl;
import base.butterKnife.KnifeCommand;
import mvp.delegate.FragmentDelegate;
import mvp.delegate.FragmentDelegateImpl;
import mvp.delegate.FragmentMvpDelegateCallback;
import mvp.iview.IBaseView;
import mvp.present.IBasePresenter;

/**
 * author : taowang
 * date :2018/7/21
 * description:
 **/
public abstract class StudyLazyRxFragment<P extends IBasePresenter, V extends IBaseView>
        extends LazyFragment
        implements FragmentMvpDelegateCallback<P, V>, IBaseView {
    protected LayoutInflater layoutInflater;
    private View mRootView;
    private P mPresenter;
    protected Activity mContext;
    private FragmentDelegate mFragmentDelegate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater;
        mFragmentDelegate = new FragmentDelegateImpl<>(this);
        mFragmentDelegate.onCreateView();

        if (mRootView == null && getLayoutId() > 0) {
            mRootView = inflater.inflate(getLayoutId(), null);
            bindUiCommand(mRootView);
        } else {
            ViewGroup viewGroup = (ViewGroup) mRootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(mRootView);
            }
        }

        return mRootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.mContext = (Activity) context;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (useEventBus()) {
            BusImpl.getInstance().register(this);
        }
        bindEvent();
        initSaveInstanceState(savedInstanceState);
        initData();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (useEventBus()) {
            BusImpl.getInstance().register(this);
        }
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mPresenter = null;
    }


    @Override
    public void onPause() {
        super.onPause();
        mFragmentDelegate.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentDelegate.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        mFragmentDelegate.onStop();
    }

    /**
     * 使用butternifer绑定控件
     *
     * @param rootView
     */
    public void bindUiCommand(View rootView) {
        KnifeCommand.bind(this);
    }

    /**
     * 是否使用EventBus
     *
     * @return
     */
    public boolean useEventBus() {
        return false;
    }

    //暴露一个创建的方法用于创建presenter
    protected abstract P InitPresenter();

    @Override
    //这个方法由MvpInternalDelegate 调用BaseDelegateCallback 来创建presenter
    public P createPresenter() {
        mPresenter = InitPresenter();
        return mPresenter;
    }

    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public V getMvpView() {
        return (V) this;
    }


    /**
     * 初始化bundle数据
     *
     * @param savedInstanceState
     */
    public void initSaveInstanceState(Bundle savedInstanceState) {

    }

    /**
     * 初始化菜单布局
     *
     * @return
     */
    public int getOptionsMenuId() {
        return 0;
    }

    public void bindEvent() {

    }


    /**
     * 获取布局id
     *
     * @return
     */
    protected abstract int getLayoutId();

    protected abstract void initData();
}

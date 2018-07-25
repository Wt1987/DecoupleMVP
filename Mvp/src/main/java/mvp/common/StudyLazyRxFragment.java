package mvp.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        extends BaseLazyFragment
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
            //复用之前的view，ViewPager+Fragment的使用过程
            // ，可以用到避免重复创建view
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

        initSaveInstanceState(savedInstanceState);
        initData();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
     * @param rootView
     * 确定使用butternife绑定view
     */
    public void bindUiCommand(View rootView) {
        KnifeCommand.bind(this);
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


    @Override
    protected void onVisible(boolean isVisible) {
        if (isVisible) {
            //更新界面数据，如果数据还在下载中，就显示加载框

        } else {
            //关闭加载框

        }
    }

    @Override
    protected void onFirstVisible() {
        //去服务器获取数据数据

    }



    protected abstract void initData();
}

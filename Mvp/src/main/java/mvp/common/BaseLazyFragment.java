package mvp.common;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.trello.rxlifecycle2.components.RxFragment;

/**
 * author : taowang
 * date :2018/7/24
 * description:
 **/
public abstract class BaseLazyFragment extends RxFragment{


    private boolean isFragmentVisible;
    private boolean isReuseView;
    private boolean isFirstVisible;
    private View mRootView;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);
        if (mRootView == null) {
            //避免调用该方法时fragment还没有完成初始化，因为该方法和fragmeng的生命周期可能不同步
            return;
        }
        if (isFirstVisible && isVisibleToUser) {
            onFirstVisible();
            isFirstVisible = false;
        }
        if (isVisibleToUser) {
            onVisible(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            isFragmentVisible = false;
            onVisible(false);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //如果setUserVisibleHint()在rootView创建前调用时，那么
        //就等到rootView创建完后才回调onVisible(true)
        //保证onVisible()的回调发生在rootView创建完成之后，以便支持ui操作
        if (mRootView == null) {
            mRootView = view;
            if (getUserVisibleHint()) {
                if (isFirstVisible) {
                    onFirstVisible();
                    isFirstVisible = false;
                }
                onVisible(true);
                isFragmentVisible = true;
            }
        }
        super.onViewCreated(isReuseView ? mRootView : view, savedInstanceState);
    }

    /**
     * 设置是否使用 view 的复用，默认开启
     * view 的复用是指，ViewPager 在销毁和重建 Fragment 时会不断调用 onCreateView() -> onDestroyView()
     * 之间的生命函数，这样可能会出现重复创建 view 的情况，导致界面上显示多个相同的 Fragment
     * view 的复用其实就是指保存第一次创建的 view，后面再 onCreateView() 时直接返回第一次创建的 view
     *
     * @param isReuse
     */
    protected void reuseView(boolean isReuse) {
        isReuseView = isReuse;
    }

    /**
     * 当fragment可见状态发生变化时,完成的操作
     * @param isVisible
     */
    protected abstract void onVisible(boolean isVisible);

    /**
     * fragment首次可见时回调，可在这里进行加载数据，保证只在第一次打开Fragment时才会加载数据
     */
    protected abstract void onFirstVisible();

    protected  boolean isFragmentVisible() {
        return isFragmentVisible;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        initFragment();
    }

    private void initFragment() {
        isFirstVisible = true;
        isFragmentVisible = false;
        mRootView = null;
        isReuseView = true;
    }

    protected abstract int getLayoutId();

    protected abstract void initData();
}

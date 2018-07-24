package mvp.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.lang.reflect.Field;

/**
 * author : taowang
 * date :2018/7/21
 * description:
 **/

public class LazyFragment extends RxFragment {
    protected LayoutInflater mLayoutInflater;
    protected Activity mContext;

    private View mRootView;
    private ViewGroup mContainer;

    private boolean mIsInitReady = false;
    private int mIsVisibleToUserState = STATE_NO_SET;
    private Bundle mSaveInstanceState;
    private boolean mIsLazyEnable = true;
    private boolean mIsStart = false;
    private FrameLayout mLayout;

    private static final int STATE_VISIBLE = 1; //用户可见
    private static final int STATE_NO_SET = -1; //未设置值
    private static final int STATE_NO_VISIBLE = 0;  //用户不可见

    private static final String TAG_ROOT_FRAMELAYOUT = "tag_root_framelayout";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mLayoutInflater = inflater;
        this.mContainer = container;
        onCreateView(savedInstanceState);
        if (mRootView == null)
            return super.onCreateView(inflater, container, savedInstanceState);

        return mRootView;
    }

    private void onCreateView(Bundle savedInstanceState) {
        this.mSaveInstanceState = savedInstanceState;
        boolean isVisible;
        if (mIsVisibleToUserState == STATE_NO_SET) {
            isVisible = getUserVisibleHint();
        } else {
            isVisible = mIsVisibleToUserState == STATE_VISIBLE;
        }
        if (mIsLazyEnable) {
            if (isVisible && !mIsInitReady) {
                onCreateViewLazy(savedInstanceState);
                mIsInitReady = true;
            } else {
                LayoutInflater mInflater = mLayoutInflater;
                if (mInflater == null && mContext != null) {
                    mInflater = LayoutInflater.from(mContext);
                }
                mLayout = new FrameLayout(mContext);
                mLayout.setTag(TAG_ROOT_FRAMELAYOUT);

                View view = getPreviewLayout(mInflater, mLayout);
                if (view != null) {
                    mLayout.addView(view);
                }
                mLayout.setLayoutParams(
                        new FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
                setContentView(mLayout);
            }
        } else {
            onCreateViewLazy(savedInstanceState);
            mIsInitReady = true;
        }
    }

    protected View getRealRootView() {
        if (mRootView != null) {
            if (mRootView instanceof FrameLayout
                    && TAG_ROOT_FRAMELAYOUT.equals(mRootView.getTag())) {
                return ((FrameLayout) mRootView).getChildAt(0);
            }
        }

        return mRootView;
    }

    protected View getmRootView() {
        return mRootView;
    }

    protected View $(int id) {
        if (mRootView != null) {
            return mRootView.findViewById(id);
        }
        return null;
    }

    protected void setContentView(int layoutResID) {
        if (mIsLazyEnable && getmRootView() != null && getmRootView().getParent() != null) {
            mLayout.removeAllViews();
            View view = mLayoutInflater.inflate(layoutResID, mLayout, false);
            mLayout.addView(view);
        } else {
            mRootView = mLayoutInflater.inflate(layoutResID, mContainer, false);
        }
    }

    protected void setContentView(View view) {
        if (mIsLazyEnable && getmRootView() != null && getmRootView().getParent() != null) {
            mLayout.removeAllViews();
            mLayout.addView(view);
        } else {
            mRootView = view;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUserState = isVisibleToUser ? STATE_VISIBLE : STATE_NO_VISIBLE;
        if (isVisibleToUser
                && !mIsInitReady
                && getmRootView() != null) {
            mIsInitReady = true;
            onCreateViewLazy(mSaveInstanceState);
            onResumeLazy();
        }
        if (mIsInitReady && getmRootView() != null) {
            if (isVisibleToUser) {
                mIsStart = true;
                onStartLazy();
            } else {
                mIsStart = false;
                onStopLazy();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mIsInitReady) {
            onResumeLazy();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mIsInitReady) {
            onPauseLazy();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mIsInitReady
                && !mIsStart
                && getUserVisibleHint()) {
            mIsStart = true;
            onStartLazy();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mIsInitReady
                && mIsStart
                && getUserVisibleHint()) {
            mIsStart = false;
            onStopLazy();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.mContext = (Activity) context;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRootView = null;
        mContainer = null;
        mLayoutInflater = null;
        if (mIsInitReady){
            onDestoryLazy();
        }
        mIsInitReady = false;
    }

    protected View getPreviewLayout(LayoutInflater mInflater, FrameLayout layout) {
        return null;
    }

    protected void onCreateViewLazy(Bundle savedInstanceState) {

    }

    protected void onStartLazy() {

    }

    protected void onStopLazy() {

    }

    protected void onResumeLazy() {

    }

    protected void onPauseLazy() {

    }

    protected void onDestoryLazy() {

    }


}

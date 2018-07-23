package mvp.delegate;

import mvp.iview.IBaseView;
import mvp.present.IBasePresenter;

/**
 * author : taowang
 * date :2018/7/21
 * description:
 **/
public interface FragmentMvpDelegateCallback <P extends IBasePresenter, V extends IBaseView>
        extends BaseDelegateCallback<P, V> {

}

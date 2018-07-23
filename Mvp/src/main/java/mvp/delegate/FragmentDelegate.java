package mvp.delegate;

/**
 * author : taowang
 * date :2018/7/21
 * description:
 **/
public interface FragmentDelegate {

    void onCreateView();

    void onPause();

    void onResume();

    void onStop();

    void onDestroyView();

}

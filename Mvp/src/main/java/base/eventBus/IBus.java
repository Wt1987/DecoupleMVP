package base.eventBus;

/**
 * author : taowang
 * date :2018/7/21
 * description:
 **/
public interface IBus {


    void register(Object object);
    void unregister(Object object);
    void post(IBaseEvent event);
    void postSticky(IBaseEvent event);

}

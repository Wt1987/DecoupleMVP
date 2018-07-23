package base.eventBus;

/**
 * author : taowang
 * date :2018/7/21
 * description:
 **/
public class BusImpl  implements IBus{


    public static BusImpl getInstance() {
        return Holder.instance;
    }


    private BusImpl(){

    }

    @Override
    public void register(Object object) {

    }

    @Override
    public void unregister(Object object) {

    }

    @Override
    public void post(IBaseEvent event) {

    }

    @Override
    public void postSticky(IBaseEvent event) {

    }


    private static class Holder {
        private static final BusImpl instance = new BusImpl();
    }
}

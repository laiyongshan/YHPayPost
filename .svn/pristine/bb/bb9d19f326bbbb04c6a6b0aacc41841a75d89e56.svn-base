package cn.droidlover.xdroidmvp.event;

/**
 * Created by wanglei on 2016/12/22.
 */

public interface IBus {

    void register(Object object);
    void unregister(Object object);
    void post(IEvent event);
    void postSticky(IEvent event);


    interface IEvent {

        String getId();

        void setId(String id);

        Object getObject();

        void setObject(Object object);

        void setMessage(String id,Object object);
    }

}

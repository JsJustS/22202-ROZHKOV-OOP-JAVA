package task5.util.pubsub;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Publisher {
    private final List<ISubscriber> subscribers = new CopyOnWriteArrayList<>();

    protected void notifySubscribers() {
        for (ISubscriber subscriber : subscribers) {
            subscriber.onNotification();
        }
    }

    public void subscribe(ISubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(ISubscriber subscriber) {
        subscribers.remove(subscriber);
    }
}

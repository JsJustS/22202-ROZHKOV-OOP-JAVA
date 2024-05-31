package task3.view.pubsub;

import java.util.ArrayList;
import java.util.List;

public abstract class Publisher {
    private final List<ISubscriber> subscribers = new ArrayList<>();

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

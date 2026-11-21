package listener;

import model.RunEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * CLASS: EventLogListener
 * WHAT IS THIS CLASS?: Stores all RunEvents in order for inspection.
 */
public class EventLogListener implements RunListener {
    private final List<RunEvent> history = new ArrayList<>();

    @Override
    public synchronized void onEvent(RunEvent event) {
        history.add(event);
    }

    public synchronized List<RunEvent> getHistory() {
        return new ArrayList<>(history);
    }
}

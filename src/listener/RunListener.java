package listener;
import model.RunEvent;
/**
* INTERFACE: RunListener
* WHAT IS THIS CLASS?: Receives real-time lifecycle events from JobEngine.
*/
public interface RunListener {
void onEvent(RunEvent event);
}
package controller.gift;

public class GiftController {
    private Action action;
    private Event event;

    public GiftController(Action action, Event event) {
        this.action = action;
        this.event = event;
    }

    public Action getAction() {
        return action;
    }

    public Event getEvent() {
        return event;
    }
}

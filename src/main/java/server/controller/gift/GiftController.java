package server.controller.gift;

public class GiftController {
    private Action action;
    private Event event;

    public GiftController(Action action, Event event) {
        this.action = action;
        this.event = event;
    }

    public void perform() {
        if (event.isEvent()) {
            try {
                action.perform();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

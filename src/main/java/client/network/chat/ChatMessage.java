package client.network.chat;

public class ChatMessage {
    private final String senderUsername;
    private String contest;

    public ChatMessage(String senderUsername, String contest) {
        this.senderUsername = senderUsername;
        this.contest = contest;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getContest() {
        return contest;
    }

    public void setContest(String contest) {
        this.contest = contest;
    }
}

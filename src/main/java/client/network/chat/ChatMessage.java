package client.network.chat;

import client.model.account.AccountType;

public class ChatMessage {
    private final String senderName;
    private String contest;
    private final AccountType type;

    public ChatMessage(String senderName, String contest, AccountType accountType) {
        this.senderName = senderName;
        this.contest = contest;
        this.type = accountType;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getContest() {
        return contest;
    }

    public AccountType getType() {
        return type;
    }

    public void setContest(String contest) {
        this.contest = contest;
    }


    @Override
    public String toString() {
        return "name :         " + senderName + '\n' +
                "account type : " + type + '\n' +
                "contest :      " + contest;
    }
}

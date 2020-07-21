package client.model.account;

import client.network.chat.SupporterChatRoom;
import com.gilecode.yagson.YaGson;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Supporter extends Account{
    private final ArrayList<SupporterChatRoom> chatRooms;

    public Supporter(String name, String lastName, String email,
                     String phoneNumber, String username, String password) {
        super(name, lastName, email, phoneNumber, username, password, AccountType.SUPPORTER);
        this.chatRooms = new ArrayList<>();
    }

    public ArrayList<SupporterChatRoom> getChatRooms() {
        return chatRooms;
    }

    public void addToChatRooms(SupporterChatRoom supporterChatRoom) {
        chatRooms.add(supporterChatRoom);
    }

    public void removeFromChatRooms(SupporterChatRoom supporterChatRoom) {
        for (SupporterChatRoom chatRoom : chatRooms) {
            if (chatRoom.getId().equals(supporterChatRoom.getId()))
                supporterChatRoom = chatRoom;
        }
        chatRooms.remove(supporterChatRoom);
    }


    public static void store() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/aboutSupporter/supporters.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            for (Account account : allAccounts) {
                if (account.getAccountType().equals(AccountType.SUPPORTER))
                    fileWriter.write(yaGson.toJson(account) + "\n");
            }
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }

    public static void load() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutSupporter/supporters.txt");
            Scanner fileScanner = new Scanner(inputStream);
            while (fileScanner.hasNextLine()) {
                Supporter supporter = yaGson.fromJson(fileScanner.nextLine(), Supporter.class);
                supporter.setNetworkState(NetworkState.OFFLINE);
                allAccounts.add(supporter);
            }
        } catch (Exception ignored) {
        }
    }}

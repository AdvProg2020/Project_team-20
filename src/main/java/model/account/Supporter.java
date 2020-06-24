package model.account;

import com.gilecode.yagson.YaGson;

import java.io.*;
import java.util.Scanner;

public class Supporter extends Account{
    public Supporter(String name, String lastName, String email,
                     String phoneNumber, String username, String password, AccountType accountType) {
        super(name, lastName, email, phoneNumber, username, password, 0, accountType);
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
            InputStream inputStream = new FileInputStream("src/main/resources/aboutSupporter/supporter.txt");
            Scanner fileScanner = new Scanner(inputStream);
            while (fileScanner.hasNextLine()) {
                Supporter buyer = yaGson.fromJson(fileScanner.nextLine(), Supporter.class);
                allAccounts.add(buyer);
            }
        } catch (Exception ignored) {
        }
    }}

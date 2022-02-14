package uk.co.batch.skunk.di;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Item[] items = Item.readAll();
        for (Item item : items) {
            System.out.println(item.getUsername() + " / " + item.getPassword());
        }

        if (args.length > 0) {
            for (String arg : args) {
                Item item = new Item();
                String uname = arg.substring(0, arg.indexOf(":"));
                String pword = arg.substring(arg.indexOf(":") + 1);
                item.setUsername(uname);
                item.setPassword(pword);
                item.write();
            }
        }
    }
}

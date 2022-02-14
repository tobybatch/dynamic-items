package uk.co.batch.skunk.di;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;

public class Item {
    private static File settingsDir;

    private File file;
    private String password;
    private String username;

    static {
        String home = System.getProperty("user.home");
        // This should use some logic to choose better location for windoze
        settingsDir = new File(home + File.separator + ".config" + File.separator + "skunkdi");
        System.out.println(settingsDir);
        if (!settingsDir.exists()) {
            settingsDir.mkdir();
        }
    }

    public void write() throws IOException {
        if (this.file == null) {
            this.file = File.createTempFile("item", "pass", Item.settingsDir);
        }

        // TODO use a format TOML, JSON, XML or something else
        String data = "username=" + this.username + "\npassword=" + this.password;
        FileWriter writer = new FileWriter(this.file);
        writer.write(data);
        writer.close();
    }

    public static Item read(File file) throws FileNotFoundException {
        Item item = new Item();
        item.setFile(file);
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            String line = myReader.nextLine();
            if (line.startsWith("username")) {
                item.setUsername(line.substring(line.indexOf("=")).trim());
            } else if (line.startsWith("password")) {
                item.setPassword(line.substring(line.indexOf("=")).trim());
            }
        }
        myReader.close();
        return item;
    }

    public static Item[] readAll() throws FileNotFoundException {
        String[] list = settingsDir.list();
        Item items[] = new Item[list.length];
        for (int i = 0; i < Objects.requireNonNull(list).length; i++) {
            items[i] = Item.read(new File(settingsDir, list[i]));
        }
        return items;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}

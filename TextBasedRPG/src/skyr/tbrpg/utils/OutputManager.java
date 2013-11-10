/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.utils;

import java.util.Arrays;
import java.util.Collection;
import skyr.tbrpg.entities.GameCharacter;
import skyr.tbrpg.entities.Room;
import skyr.tbrpg.entities.YamlEntity;

/**
 *
 * @author User
 */
public class OutputManager {

    public void showIntro() {
    }

    public void showCharacterInfo(GameCharacter gameCharacter) {
    }

    public void showCharacterItems(GameCharacter gameCharacter) {
    }

    public void showRoomInfo(Room room) {
    }

    public void showInputOptions(Collection<? extends YamlEntity> entities) {
        int counter = 1;
        for (YamlEntity yamlEntity : entities) {
            String string = yamlEntity.getId() + ". " + yamlEntity.toString().toUpperCase();
            printInGrid(string, counter);
        }
        System.out.println("");
    }

    public void showInputOptions(Enum... enums) {
        Arrays.sort(enums);
        int counter = 1;
        for (Enum enum1 : enums) {
            String string = enum1.toString();
            printInGrid(string, counter);
        }
        System.out.println("");
    }

    public void showHelp() {
    }

    private void printInGrid(String string, int counter) {
        StringBuilder builder = new StringBuilder(string);
        for (int i = string.length(); i < 15; i++) {
            builder.append(" ");
        }
        if (counter % 4 == 0) {
            System.out.println(builder.toString());
        } else {
            System.out.print(builder.toString());
        }
        counter++;
    }
}

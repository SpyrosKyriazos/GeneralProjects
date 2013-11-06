/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.utils;

import java.util.Arrays;
import java.util.Collection;
import skyr.tbrpg.entities.GameCharacter;
import skyr.tbrpg.entities.Room;

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

    public void showInputOptions(Enum... enums) {
        Arrays.sort(enums);
        for (Enum enum1 : enums) {
            System.out.println(enum1.toString());
        }
    }

    public void showHelp() {
    }
}

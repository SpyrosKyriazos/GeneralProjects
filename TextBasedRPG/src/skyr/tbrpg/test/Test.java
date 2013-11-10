/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.test;

import com.esotericsoftware.yamlbeans.YamlWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import skyr.tbrpg.entities.CharacterClass;
import skyr.tbrpg.entities.Effect;
import skyr.tbrpg.entities.Race;
import skyr.tbrpg.enums.Attribute;

/**
 *
 * @author User
 */
public class Test {

    public static void main(String[] args) {
        Test test = new Test();
//        test.yamlTest();
        test.generateClassesYaml();
        test.generateRacesYaml();
    }

    private void generateClassesYaml() {
        ArrayList<CharacterClass> classes = new ArrayList<CharacterClass>();

        Effect atkEffect = new Effect(5, Attribute.ATTACK, true, true, null);
        Effect powEffect = new Effect(5, Attribute.POWER, true, true, null);
        //warrior
        ArrayList<Effect> warriorEffects = new ArrayList<Effect>();
        warriorEffects.add(atkEffect);
        CharacterClass warriorClass = new CharacterClass(1, "warrior", warriorEffects);
        classes.add(warriorClass);
        //mage
        ArrayList<Effect> mageEffects = new ArrayList<Effect>();
        mageEffects.add(powEffect);
        CharacterClass mageClass = new CharacterClass(2, "mage", mageEffects);
        classes.add(mageClass);

        try {
            YamlWriter yamlWriter = new YamlWriter(new FileWriter("classes.yml"));
            for (CharacterClass characterClass : classes) {
                yamlWriter.write(characterClass);
            }
            yamlWriter.close();
        } catch (IOException iOException) {
            iOException.printStackTrace(System.err);
        }
    }

    private void generateRacesYaml() {
        ArrayList<Race> races = new ArrayList<Race>();

        Effect atkEffect = new Effect(5, Attribute.ATTACK, true, true, null);
        Effect powEffect = new Effect(5, Attribute.POWER, true, true, null);
        Effect hltEffect = new Effect(5, Attribute.HEALTH, true, true, null);
        Effect defEffect = new Effect(5, Attribute.DEFENCE, true, true, null);

        //human
        Race human = new Race(1, "human", null);
        races.add(human);
        //elf
        Race elf = new Race(2, "elf", powEffect);
        races.add(elf);
        //dwarf
        Race dwarf = new Race(3, "dwarf", atkEffect);
        races.add(dwarf);
        //tortoiseman
        Race tortoiseman = new Race(4, "tortoiseman", defEffect);
        races.add(tortoiseman);
        try {
            YamlWriter yamlWriter = new YamlWriter(new FileWriter("races.yml"));
            for (Race race : races) {
                yamlWriter.write(race);
            }
            yamlWriter.close();
        } catch (IOException iOException) {
            iOException.printStackTrace(System.err);
        }
    }

    private void generateWeaponTypesYaml() {
    }

    private void yamlTest() {
        try {
            final Effect effect1 = new Effect();
            effect1.setAttribute(Attribute.ATTACK);
            effect1.setModifier(1);
            final Effect effect2 = new Effect();
            effect2.setAttribute(Attribute.DEFENCE);
            effect2.setModifier(5);
            ArrayList<Effect> al = new ArrayList<Effect>();
            al.add(effect1);
            al.add(effect2);
            CharacterClass characterClass = new CharacterClass(1, "testclass", al);
            YamlWriter yamlWriter = new YamlWriter(new FileWriter("testyaml.yml"));
            yamlWriter.write(characterClass);
            yamlWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

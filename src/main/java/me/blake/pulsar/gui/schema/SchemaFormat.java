package me.blake.pulsar.gui.schema;

import java.util.ArrayList;
import java.util.List;

public class SchemaFormat {
    private Character[][] schema;

    public SchemaFormat(String... schemaList) {
        int schemaSize = schemaList.length;
        schema = schemaSize > 5 ? new Character[9][9] : new Character[schemaSize][9];

        for (int i = 0; i < Math.min(schemaSize, 5); i++) {
            char[] schemaChars = schemaList[i].toCharArray();
            for (int j = 0; j <= 8; j++) {
                schema[i][j] = schemaChars.length <= j ? ' ' : schemaChars[j];
            }
        }
    }

    public SchemaFormat(List<String> schemaList) {
        this(schemaList.toArray(new String[0]));
    }

    public int getLines() {
        return schema.length;
    }
    
    public List<Integer> getPositions(Character character) {
        int counter = 0;
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < schema.length; i++) {
            for (int j = 0; j < schema[i].length; j++) {
                if (schema[i][j].equals(character)) positions.add(counter);
                counter++;
            }
        }

        return positions;
    }

    public String[] getItemSlots() {
        List<String> itemSlots = new ArrayList<>();

        for (Character[] characters : schema) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Character character : characters) {
                if (character == '$') {
                    stringBuilder.append('1');
                } else {
                    stringBuilder.append('0');
                }
            }

            itemSlots.add(stringBuilder.toString());
        }

        return itemSlots.toArray(String[]::new);
    }
}

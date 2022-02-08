import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class Main {
    private static final HashMap<String, String> KINGDOM_VS_EMBLEM = new HashMap<>();
    private static final int ALPHABETS_COUNT = 26, MIN_ALLIES_SUPPORT = 3;

    static {
        KINGDOM_VS_EMBLEM.put("LAND", "PANDA");
        KINGDOM_VS_EMBLEM.put("WATER", "OCTOPUS");
        KINGDOM_VS_EMBLEM.put("ICE", "MAMMOTH");
        KINGDOM_VS_EMBLEM.put("AIR", "OWL");
        KINGDOM_VS_EMBLEM.put("FIRE", "DRAGON");
    }

    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                System.out.println("No input file");
                System.out.println("Usage: java Geektrust 'input.txt'");
                return;
            }

            File file = new File(args[0]);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String inputLineStr;
            Set<String> allies = null;
            while ((inputLineStr = br.readLine()) != null)
            {
                String[] inputLineStrArr = inputLineStr.split(" ", 2);
                int cipherKey = KINGDOM_VS_EMBLEM.get(inputLineStrArr[0]).length();

                StringBuilder decryptedMessage = new StringBuilder();
                String secretMessage = inputLineStrArr[1];
                for (int i = 0; i < secretMessage.length(); i++)
                {
                    if (secretMessage.charAt(i) >= 'A' && secretMessage.charAt(i) <= 'Z') {
                        char c = (char) ((int) secretMessage.charAt(i) - cipherKey);
                        if (c < 'A') {
                            c = (char) ((int) c + ALPHABETS_COUNT);
                        }
                        else if (c > 'Z') {
                            c = (char) ((int) c - ALPHABETS_COUNT);
                        }
                        decryptedMessage.append(c);
                    }
                }
                String emblem = KINGDOM_VS_EMBLEM.get(inputLineStrArr[0]);
                boolean isWonKingdom = true;
                for (int i = 0; i < emblem.length(); i++) {
                    int charIndex = decryptedMessage.indexOf(String.valueOf(emblem.charAt(i)));
                    if (charIndex != -1)
                    {
                        decryptedMessage = decryptedMessage.deleteCharAt(charIndex);
                    } else {
                        isWonKingdom = false;
                        break;
                    }
                }
                if (isWonKingdom) {
                    if (allies == null) {
                        allies = new LinkedHashSet<>();
                    }
                    allies.add(inputLineStrArr[0]);
                }
            }
            if (allies.size() < MIN_ALLIES_SUPPORT) {
                System.out.print("NONE");
            } else
            {
                System.out.print("SPACE ");
                for (String ally : allies) {
                    System.out.print(ally + " ");
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception = " + ex);

        }
    }
}
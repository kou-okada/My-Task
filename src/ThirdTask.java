import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ThirdTask {
    public static void main(String[] args) {
        File file = new File("./data/input/prefs.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String text;
            Map<String, Integer> textMap = new HashMap<>();

            while ((text = br.readLine()) != null) {

                if (!(textMap.containsKey(text))) {
                    textMap.put(text, 1);
                } else {
                    int value = textMap.get(text);
                    value += 1;
                    textMap.put(text, value);
                }

            }

            for (Map.Entry<String, Integer> entry : textMap.entrySet()) {
                System.out.println(entry.getKey() + "は、" + entry.getValue() + "件です。");
            }

            
            
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    
    }
    
}
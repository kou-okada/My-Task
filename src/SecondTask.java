public class SecondTask {
    public static void main(String[] args) {
        String[][] words = {
            {"さるが", "とりが", "いぬが", "桃太郎が"},
            {"山で", "川で", "鬼ヶ島で"},
            {"洗濯をした", "芝刈りをした", "鬼退治をした"}
        };

    
        for (int i=0; i<words.length; i++) {
            for (int j=0; j<words.length; j++) {
                for (int k=0; k<words.length; k++) {
                    System.out.println(words[0][i] + words[1][j] + words[2][k]);
                }
            }
        }
    }
    
}

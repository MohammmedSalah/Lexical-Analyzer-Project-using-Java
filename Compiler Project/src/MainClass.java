import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * Created by Mohammed Salah on 29/12/2016.
 */
public class MainClass {
    public static void main(String[] args) {
        //String inFile = "C:\Users\Mohammed Salah\IdeaProjects\Compiler Project\src\\input.txt";

        URL Input_url = ClassLoader.getSystemResource("input.txt");
        File file = null;

        try {
            file = new File(Input_url.toURI());
            LexerClass lexerClass = new LexerClass(file);
            List<Token> tokenList = lexerClass.generateTokens();
            for (int i = 0; i < tokenList.size(); i++) {
                System.out.println(tokenList.get(i).toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

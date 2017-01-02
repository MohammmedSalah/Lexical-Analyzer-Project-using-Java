import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mohammed Salah on 29/12/2016.
 */
public class LexerClass {

    BufferedReader reader;
    char current;
    List<Token> tokenList = new ArrayList<>();
    public static final String KEY_WORDS[] = new String[]{
            "import", "class", "while", "if", "else", "public",
            "private", "protected", "switch", "case", "super",
            "static", "implements", "interface", "package", "new",
            "continue", "try", "this", "final", "byte", "int", "char",
            "String", "float", "double", "boolean", "return"};

    public LexerClass(File file) {

        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        current = readNextChar();


    }


    List<Token> generateTokens() {
        Token token = readNextToken();
        while (token != null) {
            tokenList.add(token);
            token = readNextToken();
        }
        return tokenList;
    }

    Token readNextToken() {
        int state = 1;

        while (true) {
            if (current == (char) (-1)) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            switch (state) {
                case 1: {
                    if (current == ' ' || current == '\n' || current == '\t' ||
                            current == '\f' || current == '\b' || current == '\r') {
                        current = readNextChar();
                        continue;
                    } else if (current == ';') {
                        current = readNextChar();
                        return new Token("Semicolon", ";");
                    } else if (current == '+') {
                        current = readNextChar();
                        return new Token("Plus Operator", "+");
                    } else if (current == '-') {
                        current = readNextChar();
                        return new Token("Minus Operator", "-");
                    } else if (current == '*') {
                        current = readNextChar();
                        return new Token("Multiplication Operator", "*");
                    } else if (current == '/') {
                        current = readNextChar();
                        return new Token("Division Operator", "/");
                    } else if (current == '%') {
                        current = readNextChar();
                        return new Token("Remainder Operator", "%");
                    } else if (current == '{') {
                        current = readNextChar();
                        return new Token("Left Bracket", "{");
                    } else if (current == '}') {
                        current = readNextChar();
                        return new Token("right Bracket", "}");
                    } else if (current == '(') {
                        current = readNextChar();
                        return new Token("Left Parenth", "(");
                    } else if (current == ')') {
                        current = readNextChar();
                        return new Token("right Parenth", ")");
                    } else if (current == ',') {
                        current = readNextChar();
                        return new Token("Comma", ",");
                    } else if (current == '=') {
                        current = readNextChar();
                        if (current == '=') {
                            current = readNextChar();
                            return new Token("Equal Operator", "==");
                        } else {
                            return new Token("Assign Operator", "=");
                        }
                    } else if (current == '!') {
                        current = readNextChar();
                        if (current == '=') {
                            current = readNextChar();
                            return new Token("Not Equal Operator ", "!=");
                        } else return new Token("Not Defined", "!");
                    }else if(current == '&'){
                        current = readNextChar();
                        if(current == '&'){
                            current = readNextChar();
                            return new Token("Conditional And","&&");
                        }else return new Token("Not Defined","&");
                    }else if(current == '|') {
                        current = readNextChar();
                        if (current == '|') {
                            current = readNextChar();
                            return new Token("Conditional Or", "||");
                        } else return new Token("Not Defined", "|");
                    } else {
                        state = 2;
                        continue;
                    }

                }
                case 2: {
                    if (isNumber(current)) {
                        String num = String.valueOf(current);
                        for (; ; ) {
                            current = readNextChar();
                            if (isNumber(current) || current == '.') {
                                num += String.valueOf(current);
                            } else {
                                if (num.contains("."))
                                    return new Token("Decimal", num);
                                else return new Token("Integer", num);
                            }
                        }
                    } else state = 3;
                }
                case 3: {
                    if (isLetter(current) || current == '_') {
                        String word = String.valueOf(current);
                        for (; ; ) {
                            current = readNextChar();
                            if (isLetter(current) || current == '_' || isNumber(current)) {
                                word += String.valueOf(current);
                            } else {
                                List key_words = Arrays.asList(KEY_WORDS);

                                if (key_words.contains(word))
                                    return new Token("Keyword", word);
                                else return new Token("Identifier", word);
                            }
                        }
                    } else {
                        current = readNextChar();
                        return new Token("Error", "Not Defined " + current);
                    }
                }
            }
        }


    }

    char readNextChar() {
        try {
            return (char) reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (char) (-1);
    }

    boolean isNumber(char c) {
        if (c >= '0' && c <= '9')
            return true;

        return false;
    }

    boolean isLetter(char c) {
        if (c >= 'a' && c <= 'z')
            return true;
        if (c >= 'A' && c <= 'Z')
            return true;

        return false;

    }


}

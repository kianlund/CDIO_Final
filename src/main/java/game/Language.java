package game;

import java.io.*;

public class Language {
    private int counter;
    private BufferedReader reader;
    private String[] strContainer;
    private String contentLine;

    public Language(String textFile) {
        try {
            InputStream is = Language.class.getResourceAsStream("/"+textFile);
            InputStreamReader isReader = new InputStreamReader(is);
            reader = new BufferedReader(isReader);
            //reader = new BufferedReader(new FileReader(textFile));
            contentLine = reader.readLine();
            counter = 0;

            //Set up array dimensions first
            while (contentLine != null) { contentLine = reader.readLine(); counter++; }
            strContainer = new String[counter];
            strContainer[0] = "";
            //reader.close();

            //Repeat read (so it starts from the beginning)
            is = Language.class.getResourceAsStream("/"+textFile);
            isReader = new InputStreamReader(is);
            reader = new BufferedReader(isReader);
            contentLine = reader.readLine();
            counter = 0;

            while (contentLine != null) {
                strContainer[counter] = contentLine;
                counter++;
                contentLine = reader.readLine();
            }

            //reader.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found error.");
        } catch (IOException e) {
            System.out.println("IOException error.");
        }

    }

    public String getLine(int a) {
        return strContainer[a];
    }
}
import java.io.*;
import java.util.ArrayList;
/**
 * This reads and writes the score history file "SCOREHISTORY.DAT".
 */
public class ScoreHistoryFile {

    private static String SCOREHISTORY_DAT = "SCOREHISTORY.DAT";

    /**
     * Adds a score to the Score History File
     * @param data: The score to be added
     * @throws IOException If something goes wrong with the file reading or writing.
     */
    public static void addScore(Score data) throws IOException{

        RandomAccessFile out = new RandomAccessFile(SCOREHISTORY_DAT, "rw");
        out.skipBytes((int) out.length());
        out.writeBytes(data.toString() + "\n");
        out.close();
    }

    public static ArrayList<Score> getScores(String nick) throws IOException {
        ArrayList<Score> scores = new ArrayList<>();

        BufferedReader in = new BufferedReader(new FileReader(SCOREHISTORY_DAT));
        String data;
        while ((data = in.readLine()) != null) {
            // File format is nick\tfname\te-mail
            String[] scoredata = data.split("\t");
            // "Nick: scoredata[0] Date: scoredata[1] Score: scoredata[2]
            if (nick.equals(scoredata[0])) {
                scores.add(new Score(scoredata[0], scoredata[1], scoredata[2]));
            }
        }
        return scores;
    }
}

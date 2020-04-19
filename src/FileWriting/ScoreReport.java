package FileWriting;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is responsible for creating and sending score reports.
 * 
 * SMTP implementation based on code by R�al Gagnon mailto:real@rgagnon.com
 */
public class ScoreReport {

    private String content;

    public ScoreReport(Bowler bowler, int[] scores, int games) {
        String nick = bowler.getNickName();
        String full = bowler.getFullName();
        ArrayList<Score> v = null;
        try {
            v = ScoreHistoryFile.getScores(nick);
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }

        Iterator<Score> scoreIt = v.iterator();

        content = "";
        content += "--Lucky Strike Bowling Alley FileWriting.Score Report--\n";
        content += "\n";
        content += "Report for " + full + ", aka \"" + nick + "\":\n";
        content += "\n";
        content += "Final scores for this session: ";
        content += scores[0];
        for (int i = 1; i < games; i++) {
            content += ", " + scores[i];
        }
        content += ".\n\n\n";
        content += "Previous scores by date: \n";
        while (scoreIt.hasNext()) {
            Score score = (Score) scoreIt.next();
            content += "  " + score.getDate() + " - " + score.getScore();
            content += "\n";
        }
        content += "\n\n";
        content += "Thank you for your continuing patronage.";

    }

    /**
     * Sends email of score report to recipient
     * 
     * @param recipient the email address to email the report to
     */
    public void sendEmail(String recipient) {
        try {
            Socket s = new Socket("osfmail.rit.edu", 25);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), "8859_1"));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "8859_1"));

            // here you are supposed to send your username
            sendln(in, out, "HELO world");
            sendln(in, out, "MAIL FROM: <abc1234@rit.edu>");
            sendln(in, out, "RCPT TO: <" + recipient + ">");
            sendln(in, out, "DATA");
            sendln(out, "Subject: Bowling FileWriting.Score Report ");
            sendln(out, "From: <Lucky Strikes Bowling Club>");

            sendln(out, "Content-Type: text/plain; charset=\"us-ascii\"\r\n");
            sendln(out, content + "\n\n");
            sendln(out, "\r\n");

            sendln(in, out, ".");
            sendln(in, out, "QUIT");
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints the score report
     */
    public void sendPrintout() {
        PrinterJob job = PrinterJob.getPrinterJob();

        PrintableText printobj = new PrintableText(content);

        job.setPrintable(printobj);

        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException e) {
                System.out.println(e);
            }
        }

    }

    /**
     * Adds a newline character to a string and sends it 
     * to a given out. Also reads a line from the input
     * but who knows why, it serves no purpose here and
     * where it's called.
     * 
     * @param in where to read a line from
     * @param out where to write the out the string
     * @param s the string to add a newline and send
     */
    public void sendln(BufferedReader in, BufferedWriter out, String s) {
        try {
            out.write(s + "\r\n");
            out.flush();
            s = in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a newline character to a string and sends it 
     * to a given out.
     * 
     * @param out where to write the out the string
     * @param s the string to add a newline and send
     */
    public void sendln(BufferedWriter out, String s) {
        try {
            out.write(s + "\r\n");
            out.flush();
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

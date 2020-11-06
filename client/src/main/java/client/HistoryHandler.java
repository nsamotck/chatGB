package client;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class HistoryHandler {
    private PrintStream writer;
    private InputStreamReader reader;
    private File historyFile;

    HistoryHandler(String nick, DataInputStream in) {
        historyFile = new File(String.format("client/history_%s.txt", nick));
        try {
            writer = new PrintStream(new FileOutputStream(historyFile, true));
            reader = new InputStreamReader(new FileInputStream(historyFile), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PrintStream getWriter() {
        return writer;
    }

    public InputStreamReader getReader() {
        return reader;
    }

    public void close() throws IOException {
        this.writer.close();
        this.reader.close();
    }

    public List<String> getAllHistoryList() throws IOException {
        List<String> allHistory = Files.readAllLines(historyFile.toPath());
        return allHistory;
    }

    public String getLast100LinesFromHistory() throws IOException {
        StringBuilder history = new StringBuilder();
        List<String> allHistory = Files.readAllLines(historyFile.toPath());
        if (allHistory.size() > 100) {
            int firstIndex = allHistory.size() - 100;
            List<String> last100Lines = allHistory.subList(firstIndex, allHistory.size());
            for (String s : last100Lines) {
                history.append(s + "\n");
            }
            return history.toString();
        } else {
            for (String s : allHistory) {
                history.append(s + "\n");
            }
            return history.toString();
        }
    }


}

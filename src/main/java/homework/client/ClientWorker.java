package homework.client;

import homework.utils.Logger;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ClientWorker {
    private Logger log = new Logger(ClientWorker.class);
    private final int limitView = 100;
    private final String path = ClientWorker.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    List<String> checkHistory() throws Exception {
        List<String> historyMessages = new ArrayList<>();
        File history = new File(path + "history.log");
        if (history.exists() || history.createNewFile()) {
            try (Stream<String> stream = Files.lines(history.toPath())) {
                historyMessages = stream.collect(Collectors.toList());
                int skipLines = Objects.equals(Integer.compare(limitView, historyMessages.size()), 1) ? 0 : historyMessages.size() - limitView;
                log.appInfo("checkHistory", "Считан файл истории");
                return historyMessages.stream().skip(skipLines).map((e) -> e.concat("\n")).collect(Collectors.toList());
            } catch (Exception e) {
                log.appError("checkHistory", "Ошибка считывания файла, " + e.getMessage());
            }
        }
        return historyMessages;
    }

    void writeHistory(String message) {
        File history = new File(path + "history.log");
        try {
            Files.write(history.toPath(), message.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            log.appError("writeHistory", "Ошибка записи сообщения, " + e);
        }
    }
}

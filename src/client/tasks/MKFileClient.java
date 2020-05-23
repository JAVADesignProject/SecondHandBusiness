package client.tasks;

import java.io.File;
import java.io.IOException;

public class MKFileClient {
    private static MKFileClient instance;

    public static MKFileClient getInstance() {
        return instance;
    }

    public MKFileClient() throws IOException {
        instance = this;
    }

    public static boolean createDirs(String path) {
        return new File(path).mkdirs();
    }
}

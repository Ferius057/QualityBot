package kz.ferius_057.qualitybot.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * @author Charles_Grozny
 */
public final class Config {
    private final int groupId;
    private final String token;

    private Config(final int groupId, final String token) {
        this.groupId = groupId;
        this.token = token;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getToken() {
        return token;
    }

    public static Config load(final Path path) throws IOException {
        Properties properties = new Properties();

        if (Files.exists(path)) {
                try (final InputStream input = Files.newInputStream(path)) {
                    properties.load(input);
                }
        } else {
            properties.setProperty("id_group", "1");
            properties.setProperty("token", "");

            try (OutputStream os = Files.newOutputStream(path)) {
                properties.store(os, "Config Data");
            }
        }

        return new Config(
                Integer.parseInt(properties.getProperty("id_group")),
                properties.getProperty("token")
        );
    }
}
package edu.project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileConvertor {
    private Format format;

    public FileConvertor(Format format) {
        this.format = format;
    }

    @SuppressWarnings("multiplestringliterals")
    public void doFile(String mainInformation, String resources, String code, String connection, String version)
        throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mainInformation)
        .append("\n")
        .append(resources)
        .append("\n")
        .append(code)
        .append("\n")
        .append(connection)
        .append("\n")
        .append(version);
        if (format.equals(Format.markdown)) {
            Files.writeString(Path.of("src", "main", "resources", "report.md"), stringBuilder.toString());
        } else if (format.equals(Format.adoc)) {
            Files.writeString(Path.of("src", "main", "resources", "report.adoc"),
                "```\n" + stringBuilder + "```");
        }
    }
}

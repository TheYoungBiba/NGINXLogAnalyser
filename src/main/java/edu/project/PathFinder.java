package edu.project;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class PathFinder {
    private PathFinder() {}

    private static boolean isURl(String path) {
        return path.matches("^http(s)?://.+");
    }

    private static Path getRemote(String url) {
        try (InputStream inputStream = new URL(url).openStream()) {
            String[] splitedPath = url.split("/");
            Path path = Path.of("src", "main", "resources", splitedPath[splitedPath.length - 1]);
            Files.copy(inputStream, path);
            return path;
        } catch (IOException e) {
            return null;
        }
    }

    public static List<Path> config(String[] args) throws IOException {
        if (!args[0].equals("--path")) {
            throw new IllegalArgumentException("Incorrect console input.");
        }
        List<Path> listOfPaths = new LinkedList<>();
        for (int i = 1; i < args.length && !args[i].startsWith("--"); i++) {
            if (isURl(args[i])) {
                Path temp = getRemote(args[i]);
                if (temp != null) {
                    listOfPaths.add(temp);
                }
            }
            if (Files.exists(Path.of(args[i]))) {
                Path temp = Path.of(args[i]);
                if (Files.isDirectory(temp)) {
                    Files.list(temp).forEach(listOfPaths::add);
                } else {
                    listOfPaths.add(temp);
                }
            }
        }
        return listOfPaths;
    }
}

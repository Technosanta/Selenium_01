package org.example.utils;

import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {
    /**
     * Read a CSV file from the test resources and return data as Object[][] suitable for TestNG DataProvider.
     * The first line is treated as header and skipped. Handles quoted fields with commas.
     */
    public static Object[][] readCSVFromResources(String resourcePath) throws IOException {
        InputStream is = CSVUtils.class.getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }

        List<Object[]> rows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            boolean first = true;
            while ((line = reader.readLine()) != null) {
                if (first) { // skip header
                    first = false;
                    continue;
                }
                // skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                List<String> fields = parseCsvLine(line);
                // skip lines that are all empty
                boolean allEmpty = true;
                for (String f : fields) {
                    if (!f.trim().isEmpty()) { allEmpty = false; break; }
                }
                if (allEmpty) { continue; }
                rows.add(fields.toArray(new Object[0]));
            }
        }

        Object[][] data = new Object[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }
        return data;
    }

    // Simple CSV line parser that supports quoted fields with commas and escaped quotes
    private static List<String> parseCsvLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    // escaped quote
                    cur.append('"');
                    i++; // skip next
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                result.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        result.add(cur.toString());
        // trim surrounding whitespace
        for (int i = 0; i < result.size(); i++) {
            result.set(i, result.get(i).trim());
        }
        return result;
    }

    // Provide a single, static DataProvider for tests. Marked parallel=true to support TestNG parallel execution.
    @DataProvider(name = "users", parallel = true)
    public static Object[][] usersProvider() {
        try {
            Object[][] raw = CSVUtils.readCSVFromResources("testdata/users.csv");
            java.util.List<Object[]> filtered = new java.util.ArrayList<>();
            for (Object[] row : raw) {
                if (row == null) continue;
                if (row.length == 6) {
                    filtered.add(row);
                } else if (row.length > 6) {
                    // trim to expected number of columns
                    Object[] firstSix = new Object[6];
                    System.arraycopy(row, 0, firstSix, 0, 6);
                    filtered.add(firstSix);
                } else {
                    // skip invalid rows and log for debugging
                    System.out.println("Skipping CSV row with unexpected column count: " + java.util.Arrays.toString(row));
                }
            }
            return filtered.toArray(new Object[0][]);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV data", e);
        }
    }
}

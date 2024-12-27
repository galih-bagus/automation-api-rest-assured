package Helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();

    static {
        try {
            // Lokasi file konfigurasi
            FileInputStream fis = new FileInputStream("src/main/resources/application.properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Gagal memuat file konfigurasi", e);
        }
    }

    // Metode untuk mendapatkan nilai properti berdasarkan key
    public static String get(String key) {
        return properties.getProperty(key);
    }
}

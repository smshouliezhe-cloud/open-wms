package com.yiruantong.standalone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.awt.Desktop;
import java.net.URI;
import java.util.Locale;

@SpringBootApplication
public class WaterworksStandaloneApplication {
    public static void main(String[] args) {
        SpringApplication.run(WaterworksStandaloneApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void openBrowserAfterStartup() {
        String os = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
        if (!os.contains("win")) {
            return;
        }

        URI uri = URI.create("http://127.0.0.1:7861/");
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(uri);
                return;
            }
        } catch (Exception ignored) {
        }

        try {
            new ProcessBuilder("cmd", "/c", "start", "", uri.toString()).start();
        } catch (Exception ignored) {
        }
    }
}

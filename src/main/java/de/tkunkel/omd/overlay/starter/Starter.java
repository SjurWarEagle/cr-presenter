package de.tkunkel.omd.overlay.starter;

import de.tkunkel.omd.overlay.Overlay;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Starter {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {
            Overlay overlay = new Overlay();
            if (!Objects.isNull(args) && args.length > 0) {
                overlay.start(args[0]);
            } else {
                if (Files.exists(Paths.get("config.json"))) {
                    overlay.start("config.json");
                } else {
                    overlay.start(null);
                }
            }
        });
    }


}

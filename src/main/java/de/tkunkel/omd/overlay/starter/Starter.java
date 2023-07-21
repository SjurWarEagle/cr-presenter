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
                if (Files.exists(Paths.get("cr-texts.json"))) {
                    overlay.start("cr-texts.json");
                } else {
                    overlay.start(null);
                }
            }
        });
    }


}

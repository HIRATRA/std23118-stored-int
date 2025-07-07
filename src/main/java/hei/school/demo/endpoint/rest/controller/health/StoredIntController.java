package hei.school.demo.endpoint.rest.controller.health;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoredIntController {

    private static final String FILE_PATH = "/tmp/stored-int.txt";

    @GetMapping("/stored-int")
    public ResponseEntity<Map<String, String>> getStoredInt() {
        try {
            File file = new File(FILE_PATH);
            String number;

            if (file.exists()) {
                number = Files.readString(file.toPath());
            } else {
                number = String.valueOf(new Random().nextInt(1000));
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(number);
                }
            }

            return ResponseEntity.ok(Map.of("storedInt", number));

        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Internal Server Error"));
        }
    }
}

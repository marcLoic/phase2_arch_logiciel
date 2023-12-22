package org.example.app;

import org.example.Groupe.GroupeImplementation;
import org.example.IHM.MainWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.awt.*;

@Configuration
public class DesktopAppStarter {
    private static final Logger log = LoggerFactory.getLogger(DesktopAppStarter.class.getName());

    @Bean
    public CommandLineRunner createCommandLineRunner() {
        return args -> {
            EventQueue.invokeLater(() -> {
                try {
                    GroupeImplementation sess = new GroupeImplementation();
                    sess.initDatabase();
                    MainWindow window = new MainWindow(sess);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    log.error("{}", e.getMessage(), e);
                }
            });
        };
    }
}

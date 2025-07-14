#!/bin/bash
cd "/Users/apple/Documents/Java/Video Player/VidPlayer"

# Compile the project
mvn clean compile

# Run with specific JVM arguments for macOS
java -Dprism.order=sw \
     -Dprism.vsync=false \
     -Djava.awt.headless=false \
     --module-path /Users/apple/.m2/repository/org/openjfx/javafx-controls/17.0.6/javafx-controls-17.0.6-mac-aarch64.jar:/Users/apple/.m2/repository/org/openjfx/javafx-fxml/17.0.6/javafx-fxml-17.0.6-mac-aarch64.jar:/Users/apple/.m2/repository/org/openjfx/javafx-media/17.0.6/javafx-media-17.0.6-mac-aarch64.jar:/Users/apple/.m2/repository/org/openjfx/javafx-base/17.0.6/javafx-base-17.0.6-mac-aarch64.jar:/Users/apple/.m2/repository/org/openjfx/javafx-graphics/17.0.6/javafx-graphics-17.0.6-mac-aarch64.jar \
     --add-modules javafx.controls,javafx.fxml,javafx.media \
     -cp target/classes \
     org.example.vidplayer.HelloApplication

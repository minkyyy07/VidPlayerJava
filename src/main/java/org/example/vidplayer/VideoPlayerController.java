package org.example.vidplayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class VideoPlayerController implements Initializable {

    @FXML
    private MediaView mediaView;
    @FXML private Button openButton;
    @FXML private Button playButton;
    @FXML private Button stopButton;
    @FXML private Slider volumeSlider;
    @FXML private Slider timeSlider;
    @FXML private Label timeLabel;

    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private boolean atEndOfMedia = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newValue.doubleValue());
            }
        });

        timeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer != null && timeSlider.isValueChanging()) {
                mediaPlayer.seek(Duration.seconds(newValue.doubleValue()));
            }
        });
    }

    @FXML
    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите видео файл");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.avi", "*.mkv", "*.mov", "*.wmv")
        );

        File selectedFile = fileChooser.showOpenDialog(mediaView.getScene().getWindow());
        if (selectedFile != null) {
            loadMedia(selectedFile);
        }
    }

    private void loadMedia(File file) {
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }

        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

        mediaPlayer.setOnReady(() -> {
            Duration totalDuration = mediaPlayer.getTotalDuration();
            timeSlider.setMax(totalDuration.toSeconds());
            updateTimeLabel();
        });

        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (!timeSlider.isValueChanging()) {
                timeSlider.setValue(newValue.toSeconds());
            }
            updateTimeLabel();
        });

        mediaPlayer.setOnEndOfMedia(() -> {
            atEndOfMedia = true;
            isPlaying = false;
            playButton.setText("Играть");
        });

        mediaPlayer.setVolume(volumeSlider.getValue());
        playButton.setText("Играть");
        isPlaying = false;
    }

    @FXML
    private void playPause() {
        if (mediaPlayer == null) return;

        if (atEndOfMedia) {
            mediaPlayer.seek(Duration.ZERO);
            atEndOfMedia = false;
        }

        if (isPlaying) {
            mediaPlayer.pause();
            playButton.setText("Играть");
        } else {
            mediaPlayer.play();
            playButton.setText("Пауза");
        }
        isPlaying = !isPlaying;
    }

    @FXML
    private void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isPlaying = false;
            atEndOfMedia = false;
            playButton.setText("Играть");
        }
    }

    private void updateTimeLabel() {
        if (mediaPlayer != null) {
            Duration currentTime = mediaPlayer.getCurrentTime();
            Duration totalTime = mediaPlayer.getTotalDuration();

            String current = formatTime(currentTime);
            String total = formatTime(totalTime);

            timeLabel.setText(current + " / " + total);
        }
    }

    private String formatTime(Duration duration) {
        if (duration == null || duration.isUnknown()) {
            return "00:00";
        }

        int minutes = (int) duration.toMinutes();
        int seconds = (int) duration.toSeconds() - minutes * 60;

        return String.format("%02d:%02d", minutes, seconds);
    }
}

package Lab7;

import java.util.ArrayList;
import java.util.List;

public class PatternTest {
    public static void main(String args[]) {
        List<Song> listSongs = new ArrayList<Song>();
        listSongs.add(new Song("first-title", "first-artist"));
        listSongs.add(new Song("second-title", "second-artist"));
        listSongs.add(new Song("third-title", "third-artist"));
        listSongs.add(new Song("fourth-title", "fourth-artist"));
        listSongs.add(new Song("fifth-title", "fifth-artist"));
        MP3Player player = new MP3Player(listSongs);


        System.out.println(player.toString());
        System.out.println("First test");


        player.pressPlay();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Second test");


        player.pressStop();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Third test");


        player.pressFWD();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
    }
}

class Song {
    String title;
    String artist;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Song{");
        sb.append("title=").append(title);
        sb.append(", artist=").append(artist);
        sb.append('}');
        return sb.toString();
    }
}


class MP3Player {
    List<Song> songs;
    int currentSong;
    boolean isPlaying = false;

    public MP3Player(List<Song> songs) {
        this.songs = new ArrayList<>(songs);
        this.currentSong = 0;


    }

    public void pressPlay() {
        if (isPlaying) {
            System.out.println("Song is already playing");
        } else {
            isPlaying = true;
            System.out.println(String.format("Song %d is playing", currentSong));

        }

    }

    public void pressStop() {
        if (isPlaying) {
            isPlaying = false;
            System.out.println(String.format("Song %d is paused", currentSong));
        } else if (!isPlaying) {
            currentSong = 0;
            System.out.println("Songs are stopped");
        } else {
            System.out.println("Songs are already stopped");
        }


    }

    public void pressFWD() {
        if (currentSong + 1 == songs.size()) {
            currentSong = 0;
            isPlaying = true;
        } else {
            currentSong++;
            isPlaying = true;
        }
        System.out.println("Forward...");
    }

    public void pressREW() {
        if (currentSong == 0) {
            currentSong = songs.size() - 1;
            isPlaying = true;
        } else {
            currentSong--;
            isPlaying = true;
        }
        System.out.println("Reward...");
    }

    public void printCurrentSong() {
        System.out.println(songs.get(currentSong).toString());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MP3Player{");
        sb.append("currentSong = ").append(currentSong);
        sb.append(", songList = ").append(songs);
        sb.append('}');
        return sb.toString();
    }
}


public class bridge {
    public interface MusicPlayer {
        void play(String fileName);
        void decode(String fileName);
    }
    public class JazzMusicPlayerMP3Codec implements MusicPlayer {
        public void play(String fileName) {
            System.out.println("Playing Jazz music...");
            decode(fileName);
        }

        public void decode(String fileName) {
            System.out.println("Playing " + fileName + " using MP3 codec.");
        }
    }
    public class JazzMusicPlayerWAVCodec implements MusicPlayer {
        public void play(String fileName) {
            System.out.println("Playing Jazz music...");
            decode(fileName);
        }

        public void decode(String fileName) {
            System.out.println("Playing " + fileName + " using WAV codec.");
        }
    }
    public class PopMusicPlayerMP3Codec implements MusicPlayer {
        public void play(String fileName) {
            System.out.println("Playing Pop music...");
            decode(fileName);
        }

        public void decode(String fileName) {
            System.out.println("Playing " + fileName + " using MP3 codec.");
        }
    }
    public class PopMusicPlayerWAVCodec implements MusicPlayer {
        public void play(String fileName) {
            System.out.println("Playing Pop music...");
            decode(fileName);
        }

        public void decode(String fileName) {
            System.out.println("Playing " + fileName + " using WAV codec.");
        }
    }
    public class MusicStreamingApp {
        public void main(String[] args) {

            MusicPlayer popMusicPlayer1 = new PopMusicPlayerMP3Codec();
            MusicPlayer jazzMusicPlayer = new JazzMusicPlayerMP3Codec();
            MusicPlayer popMusicPlayer2 = new PopMusicPlayerWAVCodec();

            popMusicPlayer1.play("pop_song_1.mp3");
            jazzMusicPlayer.play("jazz_song.mp3");
            popMusicPlayer2.play("pop_song_2.wav");
        }
    }
}

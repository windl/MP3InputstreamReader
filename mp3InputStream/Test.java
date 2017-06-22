package mediaGate.generalFrontend.mp3InputStream;

import java.io.File;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Test {

	public static void main(String[] args) {
		
		try{
			File fi = new File("C:\\Ablage\\privat\\Musik\\Beste Lage (Das Beste von Klaus Lage)\\Beste Lage (Das Beste von Klaus Lage)\\CD1\\17 - Der alte Wolf.mp3");
			MP3File file = new MP3File(new FileInputStream(fi),(int)fi.length());
			
			System.out.println("ALBUM : " + file.getAlbum());
			System.out.println("ARTIST: " + file.getArtist());
			System.out.println("TITLE : " + file.getTitle());
			System.out.println("TRACK : " + file.getTrack());
			System.out.println("YEAR  : " + file.getYear());
			System.out.println("GENRE : " + file.getGenre());
			System.out.println("TIME  : " + file.getPlayingTime());
			System.out.println("STIME : " + file.getPlayingTimeString());
			System.out.println("FILESIZE : " + file.getFileSize());
			System.out.println("IS VAR   : " + file.isVBR());
			System.out.println("IS MP3   : " + file.isMP3());
			
			JFrame fr = new JFrame();
			fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fr.add(new JLabel(file.getImage()));
			fr.setSize(500, 500);
			fr.setVisible(true);
		}catch(Exception error){
			error.printStackTrace();
		}
	}

}

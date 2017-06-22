/*
 * Created on 25.03.2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package mediaGate.generalFrontend.mp3InputStream;

/**
 * @author heidiuwe
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public final class NullsoftID3GenreTable {

  /**
   * Description of the Field
   */
  private static final String[] genres = {
	"Blues"
	, "Classic Rock"
	, "Country"
	, "Dance"
	, "Disco"
	, "Funk"
	, "Grunge"
	, "Hip-Hop"
	, "Jazz"
	, "Metal"
	, "New Age"
	, "Oldies"
	, "Other"
	, "Pop"
	, "R&B"
	, "Rap"
	, "Reggae"
	, "Rock"
	, "Techno"
	, "Industrial"
	, "Alternative"
	, "Ska"
	, "Death Metal"
	, "Pranks"
	, "Soundtrack"
	, "Euro-Techno"
	, "Ambient"
	, "Trip-Hop"
	, "Vocal"
	, "Jazz+Funk"
	, "Fusion"
	, "Trance"
	, "Classical"
	, "Instrumental"
	, "Acid"
	, "House"
	, "Game"
	, "Sound Clip"
	, "Gospel"
	, "Noise"
	, "AlternRock"
	, "Bass"
	, "Soul"
	, "Punk"
	, "Space"
	, "Meditative"
	, "Instrumental Pop"
	, "Instrumental Rock"
	, "Ethnic"
	, "Gothic"
	, "Darkwave"
	, "Techno-Industrial"
	, "Electronic"
	, "Pop-Folk"
	, "Eurodance"
	, "Dream"
	, "Southern Rock"
	, "Comedy"
	, "Cult"
	, "Gangsta"
	, "Top 40"
	, "Christian Rap"
	, "Pop/Funk"
	, "Jungle"
	, "Native American"
	, "Cabaret"
	, "New Wave"
	, "Psychadelic"
	, "Rave"
	, "Showtunes"
	, "Trailer"
	, "Lo-Fi"
	, "Tribal"
	, "Acid Punk"
	, "Acid Jazz"
	, "Polka"
	, "Retro"
	, "Musical"
	, "Rock & Roll"
	, "Hard Rock"
	, "Folk"
	, "Folk-Rock"
	, "National Folk"
	, "Swing"
	, "Fast Fusion"
	, "Bebob"
	, "Latin"
	, "Revival"
	, "Celtic"
	, "Bluegrass"
	, "Avantgarde"
	, "Gothic Rock"
	, "Progressive Rock"
	, "Psychedelic Rock"
	, "Symphonic Rock"
	, "Slow Rock"
	, "Big Band"
	, "Chorus"
	, "Easy Listening"
	, "Acoustic"
	, "Humour"
	, "Speech"
	, "Chanson"
	, "Opera"
	, "Chamber Music"
	, "Sonata"
	, "Symphony"
	, "Booty Brass"
	, "Primus"
	, "Porn Groove"
	, "Satire"
	, "Slow Jam"
	, "Club"
	, "Tango"
	, "Samba"
	, "Folklore"
	, "Ballad"
	, "Power Ballad"
	, "Rhythmic Soul"
	, "Freestyle"
	, "Duet"
	, "Punk Rock"
	, "Drum Solo"
	, "A Capela"
	, "Euro-House"
	, "Dance Hall"
	, "Goa"
	, "Drum & Bass"
	, "Club-House"
	, "Hardcore"
	, "Terror"
	, "Indie"
	, "BritPop"
	, "Negerpunk"
	, "Polsk Punk"
	, "Beat"
	, "Christian Gangsta Rap"
	, "Heavy Metal"
	, "Black Metal"
	, "Crossover"
	, "Contemporary Christian"
	, "Christian Rock"
	, "Merengue"
	, "Salsa"
	, "Thrash Metal"
	, "Anime"
	, "JPop"
	, "SynthPop"
	};


  /**
   * Return the corresponding String for the integer coded provided. Returns
   * empty String if the code returned is invalid (less than 0 or greater than
   * 125).
   *
   *@param i  the genre code
   *@return   the genre String or null if the genre code is invalid
   */
  public static String getGenre(int i) {
	String str = new String();

	if ((i < genres.length) && (i >= 0)) {
	  str = genres[i];
	}

	return str;
  }


  /**
   * Tries to find the string provided in the table and returns the
   * corresponding int code if successful. Returns -1 if the genres is not found
   * in the table.
   *
   *@param str  the genre to search for
   *@return     the integer code for the genre or -1 if the genre is not found
   */
  public static int getGenre(String str) {
	int retval = -1;

	for (int i = 0; (i < genres.length) && (retval == -1); i++) {
	  if (genres[i].equalsIgnoreCase(str)) {
		retval = i;
	  }
	}

	return retval;
  }


  /**
   * Returns an array of all the genres which can be used to put into an
   * OptionPane or some other component for easy display.
   *
   *@return   an array of genres
   */
  public static String[] getGenres() {
	return genres;
  }

}

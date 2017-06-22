package mediaGate.generalFrontend.mp3InputStream;

import java.io.IOException;

/**
 * @author heidiuwe
 *
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ID3v1Tag implements ID3Tag {

	/**
	 * The size of an ID3v1 Tag
	 */
	private final int TAG_SIZE = 128;
	/**
	 * The size of the Title
	 */
	private final int TITLE_SIZE = 30;
	/**
	 * The size of the Artist
	 */
	private final int ARTIST_SIZE = 30;
	/**
	 * The size of the Album
	 */
	private final int ALBUM_SIZE = 30;
	/**
	 * The size of the Year
	 */
	private final int YEAR_SIZE = 4;
	/**
	 * The size of the Comment
	 */
	private final int COMMENT_SIZE = 28;
	/**
	 * The Track location
	 */
	private final int TRACK_LOCATION = 126;
	/**
	 * The Genre location
	 */
	private final int GENRE_LOCATION = 127;
	/**
	 * Tag Start of ID3v1
	 */
	private final String TAG_START = "TAG";
	/**
	 * Gives Information if header Exists ?
	 */
	private boolean headerExists = false;
	/**
	 * The title
	 */
	private String title = null;
	/**
	 * The artist
	 */
	private String artist = null;
	/**
	 * The album
	 */
	private String album = null;
	/**
	 * The year
	 */
	private String year = null;
	/**
	 * The Comment
	 */
	private String comment = null;
	/**
	 * The Genre
	 */
	private int genre;
	/**
	 * The Track Number
	 */
	private int track;

	/**
	 * Create an id3v1tag from the file specified. If the file contains a tag,
	 * the information is automatically extracted. Supports only id3v1.1
	 * directly but id3v1.0 tags will most likely work correctly too.
	 * 
	 * @exception FileNotFoundException
	 *                if an error occurs
	 * @exception IOException
	 *                if an error occurs
	 * @param mp3
	 *            the file to read/write the tag to
	 */
	public ID3v1Tag(MP3InputStream mp3) throws IOException {
		// this.mp3 = mp3;

		title = new String();
		artist = new String();
		album = new String();
		year = new String();
		comment = new String();
		genre = -1;
		track = -1;

		mp3.readStreamTotaly();

		headerExists = checkHeader(mp3);

		if (headerExists) {
			readTag(mp3);
		}
	}

	/**
	 * Checks whether a header for the id3 tag exists yet
	 * 
	 * @exception FileNotFoundException
	 *                if an error occurs
	 * @exception IOException
	 *                if an error occurs
	 * @param raf
	 *            the open file to read from
	 * @return true if a tag is found
	 */
	private boolean checkHeader(MP3InputStream raf) throws IOException {

		boolean retval = false;

		if (raf.length() > TAG_SIZE) {
			raf.seek(raf.length() - TAG_SIZE);
			byte[] buf = new byte[3];

			if (raf.read(buf) != 3) {
				throw new IOException("Error encountered reading ID3 header");
			} else {
				String result = new String(buf, 0, 3);
				retval = result.equals(TAG_START);
			}
		}

		return retval;
	}

	/**
	 * Reads the data from the id3v1 tag
	 * 
	 * @exception FileNotFoundException
	 *                if an error occurs
	 * @exception IOException
	 *                if an error occurs
	 * @param raf
	 *            the open file to read from
	 */
	private void readTag(MP3InputStream raf) throws IOException {

		raf.seek(raf.length() - TAG_SIZE);
		byte[] buf = new byte[TAG_SIZE];
		raf.read(buf, 0, TAG_SIZE);
		String tag = new String(buf, 0, TAG_SIZE);
		int start = TAG_START.length();
		title = chopSubstring(tag, start, start += TITLE_SIZE);
		artist = chopSubstring(tag, start, start += ARTIST_SIZE);
		album = chopSubstring(tag, start, start += ALBUM_SIZE);
		year = chopSubstring(tag, start, start += YEAR_SIZE);
		comment = chopSubstring(tag, start, start += COMMENT_SIZE);
		track = (int) (buf[TRACK_LOCATION] & 0xff); // reed - access genre ids >
													// 127
		genre = (int) (buf[GENRE_LOCATION] & 0xff);
	}

	/**
	 * Finds the substring of the String parameter but ends the string with the
	 * first null byte encountered.
	 * 
	 * @param s
	 *            the string chop
	 * @param start
	 *            where to start the string
	 * @param end
	 *            where to end the string if a null byte isn't found
	 * @return the chopped string
	 */
	private String chopSubstring(String s, int start, int end) {
		String str = s.substring(start, end);
		int loc = str.indexOf('\0');

		if (loc != -1) {
			str = str.substring(0, loc);
		}

		return str;
	}


	/**
	 * Returns a binary representation of this id3v1 tag. It is in the correct
	 * format according to the id3v1.1 specifications.
	 * 
	 * @return a binary representation of this id3v1 tag
	 */
	public byte[] getBytes() {
		byte[] tag = new byte[TAG_SIZE];
		int bytesCopied = 0;

		System.arraycopy(TAG_START.getBytes(), 0, tag, bytesCopied, TAG_START.length());
		bytesCopied += TAG_START.length();
		System.arraycopy(title.getBytes(), 0, tag, bytesCopied, title.length());
		bytesCopied += TITLE_SIZE;
		System.arraycopy(artist.getBytes(), 0, tag, bytesCopied, artist.length());
		bytesCopied += ARTIST_SIZE;
		System.arraycopy(album.getBytes(), 0, tag, bytesCopied, album.length());
		bytesCopied += ALBUM_SIZE;
		System.arraycopy(year.getBytes(), 0, tag, bytesCopied, year.length());
		bytesCopied += YEAR_SIZE;
		System.arraycopy(comment.getBytes(), 0, tag, bytesCopied, comment.length());
		tag[TRACK_LOCATION] = (byte) track;
		tag[GENRE_LOCATION] = (byte) genre;

		return tag;
	}

	/**
	 * Return the genre name based on the ID3/Nullsoft standards. If the genre
	 * value is not valid, an empty String is returned.
	 * 
	 * @return return the genre name or null if the genre value is not valid
	 */
	public String getGenreString() {
		return NullsoftID3GenreTable.getGenre(genre);
	}

	/**
	 * Checks if a tag exists
	 * 
	 * @return true if a tag exists
	 */
	public boolean tagExists() {
		return headerExists;
	}

	/**
	 * Return the title field of the tag
	 * 
	 * @return the title field of the tag
	 */
	public String getTitle() {
		return title.trim();
	}

	/**
	 * Return the artist field of the tag
	 * 
	 * @return the artist field of the tag
	 */
	public String getArtist() {
		return artist.trim();
	}

	/**
	 * Return the album field of the tag
	 * 
	 * @return the album field of the tag
	 */
	public String getAlbum() {
		return album.trim();
	}

	/**
	 * Return the year field of the tag
	 * 
	 * @return the year field of the tag
	 */
	public String getYear() {
		return year.trim();
	}

	/**
	 * Return the comment field of the tag
	 * 
	 * @return the comment field of the tag
	 */
	public String getComment() {
		return comment.trim();
	}

	/**
	 * Return the track field of the tag, or -1 if the tag does not exist.
	 * 
	 * @return the track field of the tag
	 */
	public int getTrack() {
		return track;
	}

	/**
	 * Return the genre field of the tag, -1 if tag doesn't exist
	 * 
	 * @return the genre field of the tag
	 */
	public int getGenre() {
		return genre;
	}

	/**
	 * Return the size in bytes of the tag. This returns 128 if the tag exists
	 * and 0 otherwise.
	 * 
	 * @return the size of the tag in bytes
	 */
	public int getSize() {
		int retval = 0;

		if (headerExists) {
			retval = TAG_SIZE;
		}

		return retval;
	}

	/**
	 * Returns a String representation of this object. Contains all information
	 * within the tag.
	 * 
	 * @return a String representation of this object
	 */
	public String toString() {
		return "ID3v1.1\nTagSize:\t\t\t" + getSize() + " bytes\nTitle:\t\t\t\t" + getTitle() + "\nArtist:\t\t\t\t"
				+ getArtist() + "\nAlbum:\t\t\t\t" + getAlbum() + "\nYear:\t\t\t\t" + getYear() + "\nComment:\t\t\t"
				+ getComment() + "\nTrack:\t\t\t\t" + getTrack() + "\nGenre:\t\t\t\t" + getGenreString();
	}

} // ID3v1Tag

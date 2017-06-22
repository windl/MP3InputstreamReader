/*
 * Created on 25.03.2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package mediaGate.generalFrontend.mp3InputStream;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author heidiuwe
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ID3v2Frames extends HashMap {

  // Want to know what these fields store?  Go to www.id3.org

  public static String PICTURE = "APIC";
  /** Description of the Field
   * 
   * 
   */
  public static String ALBUM = "TALB";
  /** Description of the Field
   * 
   * 
   */
  public static String BPM = "TBPM";
  /** Description of the Field
   * 
   * 
   */
  public static String COMPOSER = "TCOM";
  /** Description of the Field
   * 
   * 
   */
  public static String CONTENT_TYPE = "TCON";
  /** Description of the Field
   * 
   * 
   */
  public static String COPYRIGHT_MESSAGE = "TCOP";
  /** Description of the Field
   * 
   *  
   */
  public static String ENCODING_TIME = "TDEN";
  /** Description of the Field
   * 
   * 
   */
  public static String PLAYLIST_DELAY = "TDLY";
  /** Description of the Field
   * 
   * 
   */
  public static String ORIGINAL_RELEASE_TIME = "TDOR";
  /** Description of the Field
   * 
   * 
   */
  public static String RECORDING_TIME = "TDRC";
  /** Description of the Field
   * 
   * 
   */
  public static String RELEASE_TIME = "TDRL";
  /** Description of the Field
   * 
   * 
   */
  public static String TAGGING_TIME = "TDTG";
  /** Description of the Field
   * 
   * 
   */
  public static String ENCODED_BY = "TENC";
  /** Description of the Field
   * 
   *  
   */
  public static String LYRICIST = "TEXT";
  /** Description of the Field
   * 
   * 
   */
  public static String FILE_TYPE = "TFLT";
  /** Description of the Field
   * 
   * 
   */
  public static String INVOLVED_PEOPLE = "TIPL";
  /** Description of the Field
   * 
   *  
   */
  public static String CONTENT_GROUP = "TIT1";
  /** Description of the Field
   * 
   * 
   */
  public static String TITLE = "TIT2";
  /** Description of the Field
   * 
   * 
   */
  public static String SUBTITLE = "TIT3";
  /** Description of the Field
   * 
   * 
   */
  public static String INITIAL_KEY = "TKEY";
  /** Description of the Field
   * 
   * 
   */
  public static String LANGUAGE = "TLAN";
  /** Description of the Field
   * 
   * 
   */
  public static String LENGTH = "TLEN";
  /** Description of the Field
   * 
   * 
   */
  public static String MUSICIAN_CREDITS = "TMCL";
  /** Description of the Field
   * 
   *
   */
  public static String MEDIA_TYPE = "TMED";
  /** Description of the Field
   * 
   * 
   */
  public static String MOOD = "TMOO";
  /** Description of the Field
   * 
   * 
   */
  public static String ORIGINAL_ALBUM = "TOAL";
  /** Description of the Field
   * 
   *  
   */
  public static String ORIGINAL_FILENAME = "TOFN";
  /** Description of the Field
   * 
   * 
   */
  public static String ORIGINAL_LYRICIST = "TOLY";
  /** Description of the Field
   * 
   * 
   */
  public static String ORIGINAL_ARTIST = "TOPE";
  /** Description of the Field
   * 
   * 
   */
  public static String FILE_OWNER = "TOWN";
  /** Description of the Field
   * 
   * 
   */
  public static String LEAD_PERFORMERS = "TPE1";
  /** Description of the Field
   * 
   * 
   */
  public static String ACCOMPANIMENT = "TPE2";
  /** Description of the Field
   * 
   * 
   */
  public static String CONDUCTOR = "TPE3";
  /** Description of the Field
   * 
   *
   */
  public static String REMIXED_BY = "TPE4";
  /** Description of the Field
   * 
   *  
   */
  public static String PART_OF_SET = "TPOS";
  /** Description of the Field
   * 
   *  
   */
  public static String PRODUCED_NOTICE = "TPRO";
  /** Description of the Field
   * 
   *  
   */
  public static String PUBLISHER = "TPUB";
  /** Description of the Field
   * 
   *  
   */
  public static String TRACK_NUMBER = "TRCK";
  /** Description of the Field
   * 
   *  
   */
  public static String INTERNET_RADIO_STATION_NAME = "TRSN";
  /** Description of the Field
   * 
   *  
   */
  public static String INTERNET_RADIO_STATION_OWNER = "TRSO";
  /** Description of the Field
   * 
   *
   */
  public static String ALBUM_SORT_ORDER = "TSOA";
  /** Description of the Field
   * 
   * 
   */
  public static String PERFORMER_SORT_ORDER = "TSOP";
  /** Description of the Field
   * 
   * 
   */
  public static String TITLE_SORT_ORDER = "TSOT";
  /** Description of the Field
   * 
   * 
   */
  public static String ISRC = "TSRC";
  /** Description of the Field
   * 
   *  
   */
  public static String SOFTWARE_HARDWARE_SETTINGS = "TSSE";
  /** Description of the Field
   * 
   *  
   */
  public static String SET_SUBTITLE = "TSST";
  /** Description of the Field
   * 
   * 
   */
  public static String USER_DEFINED_TEXT_INFO = "TXXX";
  /** Description of the Field
   * 
   *  
   */
  public static String YEAR = "TYER";
  /** Description of the Field
   * 
   * 
   */
  public static String COMMERCIAL_INFO_URL = "WCOM";
  /** Description of the Field
   * 
   * 
   */
  public static String COPYRIGHT_INFO_URL = "WCOP";
  /** Description of the Field
   * 
   *  
   */
  public static String OFFICIAL_FILE_WEBPAGE_URL = "WOAF";
  /** Description of the Field
   * 
   *  
   */
  public static String OFFICIAL_ARTIST_WEBPAGE_URL = "WOAR";
  /** Description of the Field
   * 
   * 
   */
  public static String OFFICIAL_SOURCE_WEBPAGE_URL = "WOAS";
  /** Description of the Field
   * 
   * 
   */
  public static String OFFICIAL_INTERNET_RADIO_WEBPAGE_URL = "WOAS";
  /** Description of the Field
   * 
   * 
   */
  public static String PAYMENT_URL = "WPAY";
  /** Description of the Field
   * 
   *   
   */
  public static String OFFICIAL_PUBLISHER_WEBPAGE_URL = "WPUB";
  /** Description of the Field
   * 
   *  
   */
  public static String USER_DEFINED_URL = "WXXX";
  /** Description of the Field
   * 
   *   
   */
  public static String AUDIO_ENCRYPTION = "AENC";
  /** Description of the Field
   * 
   *   
   */
  public static String ATTACHED_PICTURE = "APIC";
  /** Description of the Field
   * 
   *   
   */
  public static String AUDIO_SEEK_POINT_INDEX = "ASPI";
  /** Description of the Field
   * 
   *   
   */
  public static String COMMENTS = "COMM";
  /** Description of the Field
   * 
   *   
   */
  public static String COMMERCIAL_FRAME = "COMR";
  /** Description of the Field
   * 
   *   
   */
  public static String ENCRYPTION_METHOD_REGISTRATION = "ENCR";
  /** Description of the Field
   * 
   *   
   */
  public static String EQUALISATION = "EQU2";
  /** Description of the Field
   * 
   *   
   */
  public static String EVENT_TIMING_CODES = "ETCO";
  /** Description of the Field
   * 
   *   
   */
  public static String GENERAL_ENCAPSULATED_OBJECT = "GEOB";
  /** Description of the Field
   * 
   *   
   */
  public static String GROUP_IDENTIFICATION_REGISTRATION = "GRID";
  /** Description of the Field
   * 
   *   
   */
  public static String LINKED_INFORMATION = "LINK";
  /** Description of the Field
   * 
   *   
   */
  public static String MUSIC_CD_IDENTIFIER = "MCDI";
  /** Description of the Field
   * 
   *   
   */
  public static String MPEG_LOCATION_LOOKUP_TABLE = "MLLT";
  /** Description of the Field
   * 
   *   
   */
  public static String OWNERSHIP_FRAME = "OWNE";
  /** Description of the Field
   * 
   *   
   */
  public static String PRIVATE_FRAME = "PRIV";
  /** Description of the Field
   * 
   *  
   */
  public static String PLAY_COUNTER = "PCNT";
  /** Description of the Field
   * 
   *   
   */
  public static String POPULARIMETER = "POPM";
  /** Description of the Field
   * 
   *  
   */
  public static String POSITION_SYNCHRONISATION_FRAME = "POSS";
  /** Description of the Field
   * 
   *   
   */
  public static String RECOMMENDED_BUFFER_SIZE = "RBUF";
  /** Description of the Field
   * 
   *   
   */
  public static String RELATIVE_VOLUME_ADJUSTMENT = "RVA2";
  /** Description of the Field
   * 
   *   
   */
  public static String REVERB = "RVRB";
  /** Description of the Field
   * 
   *   
   */
  public static String SEEK_FRAME = "SEEK";
  /** Description of the Field
   * 
   *   
   */
  public static String SIGNATURE_FRAME = "SIGN";
  /** Description of the Field
   * 
   *   
   */
  public static String SYNCHRONISED_LYRIC = "SYLT";
  /** Description of the Field
   * 
   *   
   */
  public static String SYNCHRONISED_TEMPO_CODES = "SYTC";
  /** Description of the Field
   * 
   *   
   */
  public static String UNIQUE_FILE_IDENTIFIER = "UFID";
  /** Description of the Field
   * 
   *   
   */
  public static String TERMS_OF_USE = "USER";
  /** Description of the Field
   * 
   * 
   */
  public static String UNSYNCHRONISED_LYRIC_TRANSCRIPTION = "USLT";


  /** Returns the length in bytes of all the frames contained in this object.
   * Empty frames are dropped from this calculation.
   * 
   * @return the length of all the frames contained in this object.
   */
  public int getLength() {
	int length = 0;

	Iterator it = this.values().iterator();
	while (it.hasNext()) {
	  ID3v2Frame frame = (ID3v2Frame) it.next();

	  if (!frame.isEmpty()) {
		length += frame.getFrameLength();
	  }
	}

	return length;
  }


  /** Return an array bytes containing all frames contained in this object. This
   * can be used to easily write the frames to a file. Empty frames are dropped
   * to save space.
   * 
   * @return an array of bytes contain all frames contained in this object
   */
  public byte[] getBytes() {
	byte b[] = new byte[getLength()];
	int bytesCopied = 0;

	Iterator it = this.values().iterator();
	while (it.hasNext()) {
	  ID3v2Frame frame = (ID3v2Frame) it.next();

	  if (!frame.isEmpty()) {
		System.arraycopy(frame.getFrameBytes(), 0, b, bytesCopied,
		  frame.getFrameLength());
		bytesCopied += frame.getFrameLength();
	  }
	}

	return b;
  }


  /** Returns a string representation of this object. Returns the toStrings of
   * all the frames contained within seperated by line breaks.
   * 
   * @return a string representation of this object
   */
  public String toString() {
	String str = new String();

	Iterator it = this.values().iterator();
	while (it.hasNext()) {
	  str += it.next().toString() + "\n";
	}

	return str;
  }

}



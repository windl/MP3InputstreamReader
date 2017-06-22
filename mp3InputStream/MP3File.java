package mediaGate.generalFrontend.mp3InputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

/**
 * @author heidiuwe
 *
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MP3File {

	/**
	 * Write ID3v1 and ID3v2 tags whether or not they exist. Precedence for
	 * reading will be given to id3v2 tags.
	 */
	public static final int BOTH_TAGS = 0;

	/**
	 * Write and read from ID3v2 tags only. An ID3v2 tag will be created if an
	 * attempt is made to write.
	 */
	public static final int ID3V2_ONLY = 1;

	/**
	 * Write and read from ID3v1 tags only. An ID3v1 tag will be created if an
	 * attempt is made to write.
	 */
	public static final int ID3V1_ONLY = 2;

	/**
	 * Do not write or read from any id3 tags.
	 */
	public static final int NO_TAGS = 3;

	/**
	 * Only write and read tags that already exist. Existing tags can be updated
	 * but new tags will not be created.
	 */
	public static final int EXISTING_TAGS_ONLY = 4;

	
	private ID3v1Tag id3v1 = null;
	/**
	 * The ID3v2 Tag
	 */
	private ID3v2Tag id3v2 = null;
	/**
	 * The MPEG-Audio frameheader
	 */
	private MPEGAudioFrameHeader head = null;
	/**
	 * The MP3 File
	 */
	private MP3InputStream mp3 = null;

	private Object closeLock = new Object();
	
	/**
	 * Create and MP3File object that reads and writes to the specified file.
	 * The id3 tags that are read from and written are dependant upon the
	 * tagType argument. This could be either: BOTH_TAGS, ID3V2_ONLY,
	 * ID3V1_ONLY, NO_TAGS, or EXISTING_TAGS_ONLY.
	 *
	 * @param mp3
	 *            the file of the mp3
	 * @param tagType
	 *            determines what type of tags to write and read from
	 * @exception FileNotFoundException
	 *                if an error occurs
	 * @exception NoMPEGFramesException
	 *                if an error occurs
	 * @exception IOException
	 *                if an error occurs
	 * @exception ID3v2FormatException
	 *                if an error occurs
	 * @exception CorruptHeaderException
	 *                if an error occurs
	 */
	public MP3File(InputStream in, int len) throws FileNotFoundException, NoMPEGFramesException, IOException,
			ID3v2FormatException, CorruptHeaderException {

		this.mp3 = new MP3InputStream(in, len);
		this.mp3.mark(0);
		// Start looking at the beginning of the file instead of the end of
		// the id3v2 tag because of padding
		head = new MPEGAudioFrameHeader(mp3, 0);
		id3v2 = new ID3v2Tag(mp3, head.getLocation());
		if(!id3v2.tagExists()){
			//If the ID3v2 Header is not available then search the ID1 Header.
			//In this special case - we have to read the whole Stream
			id3v1 = new ID3v1Tag(mp3);
		}
		
		synchronized (closeLock) {
			this.mp3.closeStream();
			in.close();
		}
	}

	public String getAlbum() {
		if (id3v2.tagExists()) {
			try {
				return id3v2.getFrameDataString(ID3v2Frames.ALBUM);
			} catch (Exception error) {
			}
		}

		if(id3v1.tagExists()){
			return id3v1.getAlbum();
		}
		
		return "";
	}

	public String getArtist() {
		if (id3v2.tagExists()) {
			try {
				return id3v2.getFrameDataString(ID3v2Frames.LEAD_PERFORMERS);
			} catch (Exception error) {
			}
		}

		if(id3v1.tagExists()){
			return id3v1.getArtist();
		}
		
		
		return "";
	}

	public String getTitle() {
		if (id3v2.tagExists()) {
			try {
				return id3v2.getFrameDataString(ID3v2Frames.TITLE);
			} catch (Exception error) {
			}
		}

		if(id3v1.tagExists()){
			return id3v1.getTitle();
		}
		
		
		return "";
	}

	public String getTrack() {
		if (id3v2.tagExists()) {
			try {
				return id3v2.getFrameDataString(ID3v2Frames.TRACK_NUMBER);
			} catch (Exception error) {
			}
		}

		if(id3v1.tagExists()){
			return ""+id3v1.getTrack();
		}
		
		
		return "";
	}

	public String getYear() {
		if (id3v2.tagExists()) {
			try {
				return id3v2.getFrameDataString(ID3v2Frames.YEAR);
			} catch (Exception error) {
			}
		}

		if(id3v1.tagExists()){
			return id3v1.getYear();
		}
		
		
		return "";
	}

	public String getGenre() {
		if (id3v2.tagExists()) {
			try {
				return id3v2.getFrameDataString(ID3v2Frames.CONTENT_TYPE);
			} catch (Exception error) {
			}
		}

		if(id3v1.tagExists()){
			return id3v1.getGenreString();
		}
		
		
		return "";
	}

	public ImageIcon getImage() {
		if (id3v2.tagExists()) {
			try {
				return new ImageIcon(id3v2.getFrameData(ID3v2Frames.PICTURE));
			} catch (Exception error) {
			}
		}

		return null;
	}

	/**
	 * Returns the length (in seconds) of the playing time of this mp3. This
	 * will not return an accurate value for VBR files.
	 *
	 * @return the playing time (in seconds) of this mp3
	 */
	public long getPlayingTime() {
		long time = 0;

		if (head.isVBR()) {
		  time = head.getVBRPlayingTime();
		} else {
		  long datasize = (mp3.length() * 8) - id3v2.getSize()
						   - id3v1.getSize();
		  long bps = head.getBitRate() * 1000;

		  // Avoid divide by zero error
		  if (bps == 0) {
			time = 0;
		  } else {
			time = datasize / bps;
		  }
		}

		return time;
	}

	/**
	 * Return a formatted version of the getPlayingTime method. The string will
	 * be formated "m:ss" where 'm' is minutes and 'ss' is seconds.
	 *
	 * @return a formatted version of the getPlayingTime method
	 */
	public String getPlayingTimeString() {
		String str;
		long time = getPlayingTime();
		long mins = time / 60;
		long secs = Math.round((((double) time / 60) - (long) (time / 60)) * 60);

		str = mins + ":";

		if (secs < 10) {
			str += "0" + secs;
		} else {
			str += "" + secs;
		}

		return str;
	}
	/**
	// * Return the filesize of this MP3File (in bytes).
	// *
	// *@return the filesize of this MP3File (in bytes)
	// */
	 public long getFileSize() {
		 return mp3.length();
	 }

	 /**
	 * Returns true if this file is an mp3. This means simply that an
	 * MPEGAudioFrameHeader was found and the layer is 3.
	 *
	 *@return true if this file is an mp3
	 */
	 public boolean isMP3() {
		 return head.isMP3();
	 }
	
	
	 /**
	 * Returns true if this mp3 is a variable bitrate file.
	 *
	 *@return true if this mp3 is a variable bitrate file.
	 */
	 public boolean isVBR() {
		 return head.isVBR();
	 }
	
	
	 /**
	 * Returns the bitrate of this mp3 in kbps. If the file is a VBR file then
	 the
	 * average bitrate is returned.
	 *
	 *@return the bitrate of this mp3 in kbps
	 */
	 public int getBitRate() {
		 return head.getBitRate();
	 }
	
	
	 /**
	 * Returns the sample rate of this mp3 in Hz.
	 *
	 *@return the sample reate of this mp3 in Hz
	 */
	 public int getSampleRate() {
		 return head.getSampleRate();
	 }
	
	
	 /**
	 * Returns the emphasis of this mp3.
	 *
	 *@return the emphasis of this mp3
	 */
	 public String getMPEGEmphasis() {
		 return head.getEmphasis();
	 }
	
	
	 /**
	 * Returns a string specifying the layer of the mpeg. Ex: Layer III
	 *
	 *@return a string specifying the layer of the mpeg
	 */
	 public String getMPEGLayer() {
		 return head.getLayer();
	 }
	
	
	 /**
	 * Returns a string specifying the version of the mpeg. This can either be
	 * 1.0, 2.0, or 2.5.
	 *
	 *@return a string specifying the version of the mpeg
	 */
	 public String getMPEGVersion() {
		 return head.getVersion();
	 }
	
	
	 /**
	 * Return the channel mode of the mpeg. Ex: Stereo
	 *
	 *@return the channel mode of the mpeg
	 */
	 public String getMPEGChannelMode() {
		 return head.getChannelMode();
	 }
	
	
	 /**
	 * Returns true if this mpeg is copyrighted.
	 *
	 *@return true if this mpeg is copyrighted
	 */
	 public boolean isMPEGCopyrighted() {
		 return head.isCopyrighted();
	 }
	
	
	 /**
	 * Returns true if this mpeg is the original.
	 *
	 *@return true if this mpeg is the original
	 */
	 public boolean isMPEGOriginal() {
		 return head.isOriginal();
	 }
	
	
	 /**
	 * Returns true if this mpeg is protected by CRC.
	 *
	 *@return true if this mpeg is protected by CRC
	 */
	 public boolean isMPEGProtected() {
		 return head.isProtected();
	 }
	
	
	 /**
	 * Returns true if the private bit is set in this mpeg.
	 *
	 *@return true if the private bit is set in this mpeg
	 */
	 public boolean isMPEGPrivate() {
		 return head.privateBitSet();
	 }

}

/*
 * Created on 25.03.2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package mediaGate.generalFrontend.mp3InputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author heidiuwe
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ID3v2Header {

  /**
   * Start String
   */
  private final String TAG_START = "ID3";
  /**
   * ???
   */
  private final int HEAD_SIZE = 10;
  /**
   * ???
   */
  private final int HEAD_LOCATION = 0;
  /**
   * ???
   */
  private final int NEW_MAJOR_VERSION = 3;// So winamp will accept it use
  /**
   * ???
   */
  private final int NEW_MINOR_VERSION = 0;// 3 instead of 4

  /**
   * The File
   */
  private MP3InputStream mp3 = null;
  /**
   * ???
   */
  private boolean headerExists;
  /**
   * ???
   */
  private int majorVersion;
  /**
   * ???
   */
  private int minorVersion;
  /**
   * ???
   */
  private boolean unsynchronisation;
  /**
   * ???
   */
  private boolean extended;
  /**
   * ???
   */
  private boolean experimental;
  /**
   * ???
   */
  private boolean footer;
  /**
   * ???
   */
  private int tagSize;


  /**
   * Create an id3v2header linked to the file passed as a parameter. An attempt
   * will be made to read the header from the file. If a header exists, then
   * information in the header will be extracted. If a header doesn't exist,
   * default data will be used.
   *
   *@param mp3                        the file to attempt to read data from
   *@exception FileNotFoundException  if an error occurs
   *@exception IOException            if an error occurs
   */
  public ID3v2Header(MP3InputStream mp3) throws FileNotFoundException, IOException {
	this.mp3 = mp3;

	majorVersion = NEW_MAJOR_VERSION;
	minorVersion = NEW_MINOR_VERSION;
	unsynchronisation = false;
	extended = false;
	experimental = false;
	footer = false;
	tagSize = 0;


	try {
	  headerExists = checkHeader(mp3);

	  if (headerExists) {
		readHeader(mp3);
	  }
	} catch(Exception error){
		error.printStackTrace();
	}
  }


  /**
   * Checks to see if there is an id3v2 header in the file provided to the
   * constructor.
   *
   *@param raf                        the open file to read from
   *@return                           true if an id3v2 header exists in the file
   *@exception FileNotFoundException  if an error occurs
   *@exception IOException            if an error occurs
   */
  private boolean checkHeader(MP3InputStream raf)
	 throws FileNotFoundException, IOException {

	boolean exists = false;
	raf.seek(HEAD_LOCATION);
	byte[] buf = new byte[HEAD_SIZE];

	if (raf.read(buf) != HEAD_SIZE) {
	  throw new IOException("Error encountered finding id3v2 header");
	}

	String result = new String(buf);
	if (result.substring(0, TAG_START.length()).equals(TAG_START)) {
	  if ((buf[3] < 0xff) && (buf[4] < 0xff)) {
		if ((buf[6] < 0x80) && (buf[7] < 0x80)
			&& (buf[8] < 0x80) && (buf[9] < 0x80)) {

		  exists = true;
		}
	  }
	}

	return exists;
  }


  /**
   * Extracts the information from the header.
   *
   *@param raf                        the open file to read from
   *@exception FileNotFoundException  if an error occurs
   *@exception IOException            if an error occurs
   */
  private void readHeader(MP3InputStream raf)
	 throws FileNotFoundException, IOException {

	raf.seek(HEAD_LOCATION);
	byte[] head = new byte[HEAD_SIZE];

	if (raf.read(head) != HEAD_SIZE) {
	  throw new IOException("Error encountered reading id3v2 header");
	}

	majorVersion = (int) head[3];

	if (majorVersion <= NEW_MAJOR_VERSION) {
	  minorVersion = (int) head[4];
	  unsynchronisation = BinaryParser.bitSet(head[5], 7);
	  extended = BinaryParser.bitSet(head[5], 6);
	  experimental = BinaryParser.bitSet(head[5], 5);
	  footer = BinaryParser.bitSet(head[5], 4);

	  byte[] size = {head[6], head[7], head[8], head[9]};
	  tagSize = BinaryParser.convertToSynchsafeInt(size);
	}
  }


  /**
   * Return an array of bytes representing the header. This can be used to
   * easily write the header to a file. When this method is called it
   * automatically updates the header to the newest format.
   *
   *@return   a binary representation of this header
   */
  public byte[] getBytes() {
	byte[] b = new byte[HEAD_SIZE];
	int bytesCopied = 0;

	if (majorVersion < NEW_MAJOR_VERSION) {
	  majorVersion = NEW_MAJOR_VERSION;
	}

	System.arraycopy(TAG_START.getBytes(), 0, b, bytesCopied,
	  TAG_START.length());
	bytesCopied += TAG_START.length();
	b[bytesCopied++] = (byte) majorVersion;
	b[bytesCopied++] = (byte) minorVersion;
	b[bytesCopied++] = getFlagByte();
	System.arraycopy(BinaryParser.convertToSynchsafeBytes(tagSize),
	  0, b, bytesCopied, 4);
	bytesCopied += 4;

	return b;
  }


  /**
   * A helper function for the getBytes function that returns a byte with the
   * proper flags set.
   *
   *@return   the flags byte of this header
   */
  private byte getFlagByte() {
	byte ret = 0;

	if (unsynchronisation) {
	  ret = BinaryParser.setBit(ret, 7);
	}
	if (extended) {
	  ret = BinaryParser.setBit(ret, 6);
	}
	if (experimental) {
	  ret = BinaryParser.setBit(ret, 5);
	}
	if (footer) {
	  ret = BinaryParser.setBit(ret, 4);
	}

	return ret;
  }


  /**
   * Returns true if a header exists
   *
   *@return   true if a header exists
   */
  public boolean headerExists() {
	return headerExists;
  }


  /**
   * Returns the size (in bytes) of this header. This is always 10.
   *
   *@return   the size of this header
   */
  public int getHeaderSize() {
	return HEAD_SIZE;
  }


  /**
   * Returns the size (in bytes) of the frames and/or extended header portion of
   * the id3v2 tag according to the size field in the header.
   *
   *@return   the size field of the header
   */
  public int getTagSize() {
	return tagSize;
  }


  /**
   * Sets the size of the frames and/or extended header. If this function is
   * called, the headerExists function will return true. This is called every
   * time a frame is updated, added, or removed.
   *
   *@param size  a value of type 'int'
   */
  public void setTagSize(int size) {
	if (size > 0) {
	  tagSize = size;
	  headerExists = true;
	}
  }


  /**
   * Returns the major version of this id3v2 tag.
   *
   *@return   the major version of this id3v2 tag.
   */
  public int getMajorVersion() {
	return majorVersion;
  }


  /**
   * Return the minor version/revision of this id3v2 tag.
   *
   *@return   the minor version/revision of this id3v2 tag.
   */
  public int getMinorVersion() {
	return minorVersion;
  }


  /**
   * Returns true if the unsynchronisation bit is set in this header.
   *
   *@return   true if the unsynchronisation bit is set in this header.
   */
  public boolean getUnsynchronisation() {
	return unsynchronisation;
  }


  /**
   * Set the unsynchronisation flag for this header.
   *
   *@param unsynch  the new value of the unsynchronisation flag
   */
  public void setUnsynchronisation(boolean unsynch) {
	unsynchronisation = unsynch;
  }


  /**
   * Returns true if this tag has an extended header.
   *
   *@return   true if this tag has an extended header
   */
  public boolean getExtendedHeader() {
	return extended;
  }


  /**
   * Set the value of the extended header bit of this header.
   *
   *@param extend  the new value of the extended header bit
   */
  public void setExtendedHeader(boolean extend) {
	extended = extend;
  }


  /**
   * Returns true if the experimental bit of this header is set.
   *
   *@return   true if the experimental bit of this header is set
   */
  public boolean getExperimental() {
	return experimental;
  }


  /**
   * Set the value of the experimental bit of this header.
   *
   *@param experiment  the new value of the experimental bit
   */
  public void setExperimental(boolean experiment) {
	experimental = experiment;
  }


  /**
   * Returns true if this tag has a footer.
   *
   *@return   true if this tag has a footer
   */
  public boolean getFooter() {
	return footer;
  }


  /**
   * Sets the value of the footer bit for this header.
   *
   *@param foot  the new value of the footer bit for this header
   */
  public void setFooter(boolean foot) {
	footer = foot;
  }


  /**
   * Return a string representation of this object. Contains all information
   * contained within.
   *
   *@return   a string representation of this object
   */
  public String toString() {
	return "ID3v2." + getMajorVersion() + "." + getMinorVersion() + "\n" 
		   + "TagSize:\t\t\t" + getTagSize() 
		   + " bytes\nUnsynchronisation:\t\t" + getUnsynchronisation() 
		   + "\nExtended Header:\t\t" + getExtendedHeader() 
		   + "\nExperimental:\t\t\t" + getExperimental() 
		   + "\nFooter:\t\t\t\t" + getFooter();
  }

}



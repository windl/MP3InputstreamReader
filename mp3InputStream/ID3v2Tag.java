package mediaGate.generalFrontend.mp3InputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author heidiuwe
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ID3v2Tag implements ID3Tag {

  /**
   * encryption type
   */
  private final String ENC_TYPE = "ISO-8859-1";
  /**
   * MP3 Ext badid
   */
  private final String MP3EXT_BADID = "MP3e";
  /**
   * Padding Change ???
   */
  private final int NEWTAG_LIMIT = 16000;

  /**
   * ???
   */
  private ID3v2Header head = null;
  /**
   * ???
   */
  private ID3v2ExtendedHeader ext_head = null;
  /**
   * ???
   */
  private ID3v2Frames frames = null;
  /**
   * ???
   */
  private ID3v2Footer foot = null;
  /**
   * ???
   */
  private int padding;
  /**
   * ???
   */
  private int writtenTagSize;
  /**
   * ???
   */
  private int writtenPadding;
  /**
   * ???
   */
  private boolean exists;
  /**
   * ???
   */
  private long mpegOffset;


  public ID3v2Tag(MP3InputStream mp3, long mpegOffset)
	 throws FileNotFoundException, IOException, ID3v2FormatException {

	this.mpegOffset = mpegOffset;

	frames = new ID3v2Frames();
	head = new ID3v2Header(mp3);
	padding = 0;
	exists = head.headerExists();

	if (exists) {
	  if (head.getExtendedHeader()) {
		ext_head = new ID3v2ExtendedHeader(mp3);
	  }
	  if (head.getFooter()) {
		foot = new ID3v2Footer(mp3,
		  head.getTagSize() + head.getHeaderSize());
	  }

	  try {
		// For now only support id3v2.3.0 or greater
		if (head.getMajorVersion() >= 3) {
		  parseFrames(mp3);
		}
	  } catch(Exception error){
		  error.printStackTrace();
	  }

	  writtenTagSize = head.getTagSize();
	  writtenPadding = padding;

	  // Check to validate tag size taken out because MusicMatch
	  // has some bugs that causes the check to fail
	}
  }


  /**
   * Read the frames from the file and create ID3v2Frame objects from the data
   * found.
   *
   *@param raf                       the open file to read from
   *@exception IOException           if an error occurs
   *@exception ID3v2FormatException  if an error occurs
   */
  private void parseFrames(MP3InputStream raf)
	 throws IOException, ID3v2FormatException {

	int offset = head.getHeaderSize();
	// Actually length of frames + padding
	int framesLength = head.getTagSize();
	int bytesRead = 0;
	int curLength = 0;
	ID3v2Frame frame = null;
	String id = null;
	byte[] buf;
	byte[] flags;
	boolean done = false;

	if (head.getExtendedHeader()) {
	  framesLength -= ext_head.getSize();
	  offset += ext_head.getSize();
	}

	raf.seek(offset);

	while ((bytesRead < framesLength) && !done) {
	  buf = new byte[4];
	  bytesRead += raf.read(buf);
	  id = new String(buf);

	  if (!(id.equals(MP3EXT_BADID) || id.indexOf(0) != -1)) {
		bytesRead += 4;
		curLength = raf.readInt();

		// Added by Reed
		if (curLength < 0 || curLength > framesLength - bytesRead) {
		  throw new ID3v2FormatException("ID3v2Tag.parseFrames: " 
										 + "Invalid frame size");
		}

		flags = new byte[2];
		bytesRead += raf.read(flags);
		buf = new byte[curLength];
		bytesRead += raf.read(buf);
		
		//Es handelt sich um ein Bild...
		if(id.equalsIgnoreCase("APIC")){
			
			int start = 0;
			for(int i = 0;i<50;i++){
				byte c = buf[i];
				if(Integer.toHexString(c).equalsIgnoreCase("FFFFFFD8")){
					start = i-1;
					break;
				}
			}
			
			byte[] pic = new byte[buf.length-start];
			System.arraycopy(buf, start, pic, 0, pic.length);
			buf = pic;
		}
		
		
		frame = new ID3v2Frame(id, flags, buf);
		frames.put(id, frame);
	  } else {
		// We've hit padding so stop reading
		done = true;
		bytesRead -= buf.length;
	  }
	}

	// Get around the possible precision loss
	Long tmp = new Long(mpegOffset - offset - bytesRead);
	padding = tmp.intValue();
  }




  /**
   * Return a binary representation of this object to be written to a file. This
   * is in the format of the id3v2 specifications. This includes the header,
   * extended header (if it exists), the frames, padding (if it exists), and a
   * footer (if it exists).
   *
   *@return   a binary representation of this id3v2 tag
   */
  public byte[] getBytes() {
	byte[] b = new byte[getTotalSize()];
	int bytesCopied = 0;
	int length = 0;
	padding = getUpdatedPadding();
	length = head.getHeaderSize();
	System.arraycopy(head.getBytes(), 0, b, bytesCopied, length);
	bytesCopied += length;

	if (head.getExtendedHeader()) {
	  length = ext_head.getSize();
	  System.arraycopy(ext_head.getBytes(), 0, b, bytesCopied, length);
	  bytesCopied += length;
	}

	length = frames.getLength();
	System.arraycopy(frames.getBytes(), 0, b, bytesCopied, length);
	bytesCopied += length;

	// Bytes should all be zero's by default
	if (padding > 0) {
	  System.arraycopy(new byte[padding], 0, b, bytesCopied, padding);
	  bytesCopied += padding;
	}

	if (head.getFooter()) {
	  length = foot.getFooterSize();
	  System.arraycopy(foot.getBytes(), 0, b, bytesCopied, length);
	  bytesCopied += length;
	}

	return b;
  }


//  /**
//   * Set the data contained in a text frame. This includes all frames with an id
//   * that starts with 'T' but excludes "TXXX". If an improper id is passed, then
//   * nothing will happen.
//   *
//   *@param id    the id of the frame to set the data for
//   *@param data  the data for the frame
//   */
//  public void setTextFrame(String id, String data) {
//	if ((id.charAt(0) == 'T')
//		&& !id.equals(ID3v2Frames.USER_DEFINED_TEXT_INFO)) {
//
//	  try {
//		byte[] b = new byte[data.length() + 1];
//		b[0] = 0;
//		System.arraycopy(data.getBytes(ENC_TYPE), 0, b, 1,
//		  data.length());
//
//		updateFrameData(id, b);
//	  } catch (UnsupportedEncodingException e) {
//		e.printStackTrace();
//	  }
//	}
//  }
//
//
//  /**
//   * Set the data contained in a URL frame. This includes all frames with an id
//   * that starts with 'W' but excludes "WXXX". If an improper id is passed, then
//   * nothing will happen.
//   *
//   *@param id    the id of the frame to set the data for
//   *@param data  the data for the frame
//   */
//  public void setURLFrame(String id, String data) {
//	if ((id.charAt(0) == 'W')
//		&& !id.equals(ID3v2Frames.USER_DEFINED_URL)) {
//
//	  updateFrameData(id, data.getBytes());
//	}
//  }
//
//
//  /**
//   * Sets the data contained in the user defined text frame (TXXX).
//   *
//   *@param description  a description of the data
//   *@param value        the data for the frame
//   */
//  public void setUserDefinedTextFrame(String description, String value) {
//	try {
//	  byte[] b = new byte[description.length() + value.length() + 2];
//	  int bytesCopied = 0;
//	  b[bytesCopied++] = 0;
//	  System.arraycopy(description.getBytes(ENC_TYPE), 0, b,
//		bytesCopied, description.length());
//	  bytesCopied += description.length();
//	  b[bytesCopied++] = 0;
//	  System.arraycopy(value.getBytes(ENC_TYPE), 0, b, bytesCopied,
//		value.length());
//	  bytesCopied += value.length();
//
//	  updateFrameData(ID3v2Frames.USER_DEFINED_TEXT_INFO, b);
//	} catch (UnsupportedEncodingException e) {
//	  e.printStackTrace();
//	}
//  }
//
//
//  /**
//   * Sets the data contained in the user defined url frame (WXXX).
//   *
//   *@param description  a description of the url
//   *@param value        the url for the frame
//   */
//  public void setUserDefinedURLFrame(String description, String value) {
//	try {
//	  byte[] b = new byte[description.length() + value.length() + 2];
//	  int bytesCopied = 0;
//	  b[bytesCopied++] = 0;
//	  System.arraycopy(description.getBytes(ENC_TYPE), 0, b,
//		bytesCopied, description.length());
//	  bytesCopied += description.length();
//	  b[bytesCopied++] = 0;
//	  System.arraycopy(value.getBytes(), 0, b, bytesCopied,
//		value.length());
//	  bytesCopied += value.length();
//
//	  updateFrameData(ID3v2Frames.USER_DEFINED_URL, b);
//	} catch (UnsupportedEncodingException e) {
//	  e.printStackTrace();
//	}
//  }
//
//
//  /**
//   * Set the data contained in the comments frame (COMM).
//   *
//   *@param description  a description of the comment
//   *@param comment      the comment
//   */
//  public void setCommentFrame(String description, String comment) {
//	try {
//	  byte[] b = new byte[description.length() + comment.length() + 5];
//	  int bytesCopied = 0;
//	  b[bytesCopied++] = 0;
//	  b[bytesCopied++] = 'e';
//	  b[bytesCopied++] = 'n';
//	  b[bytesCopied++] = 'g';
//	  System.arraycopy(description.getBytes(ENC_TYPE), 0, b,
//		bytesCopied, description.length());
//	  bytesCopied += description.length();
//	  b[bytesCopied++] = 0;
//	  System.arraycopy(comment.getBytes(ENC_TYPE), 0, b,
//		bytesCopied, comment.length());
//	  bytesCopied += comment.length();
//
//	  updateFrameData(ID3v2Frames.COMMENTS, b);
//	} catch (UnsupportedEncodingException e) {
//	  e.printStackTrace();
//	}
//  }


  /**
   * Remove the frame with the specified id from the file. If there is no frame
   * with that id nothing will happen.
   *
   *@param id  the id of the frame to remove
   */
  public void removeFrame(String id) {
	frames.remove(id);
  }


  /**
   * Updates the data for the frame specified by id. If no frame exists for the
   * id specified, a new frame with that id is created.
   *
   *@param id    the id of the frame to update
   *@param data  the data for the frame
   */
  public void updateFrameData(String id, byte[] data) {
	if (frames.containsKey(id)) {
	  ((ID3v2Frame) frames.get(id)).setFrameData(data);
	} else {
	  ID3v2Frame frame = new ID3v2Frame(id, data);
	  frames.put(id, frame);
	}
  }


  /**
   * Returns the textual information contained in the frame specified by the id.
   * Not every type of frame has textual information. If an id is specified that
   * will not work, the empty string is returned.
   *
   *@param id                        the id of the frame to get text from
   *@return                          the text information contained in the frame
   *@exception ID3v2FormatException  if an error is encountered parsing data
   */
  public String getFrameDataString(String id) throws ID3v2FormatException {
	String str = new String();

	if (frames.containsKey(id)) {
	  str = ((ID3v2Frame) frames.get(id)).getDataString();
	}

	return str;
  }


  /**
   * Returns the data found in the frame specified by the id. If the frame
   * doesn't exist, then a zero length array is returned.
   *
   *@param id  the id of the frame to get the data from
   *@return    the data found in the frame
   */
  public byte[] getFrameData(String id) {
	byte[] b = new byte[0];

	if (frames.containsKey(id)) {
	  b = ((ID3v2Frame) frames.get(id)).getFrameData();
	}

	return b;
  }


  /**
   * Returns true if an id3v2 tag exists in the file that was passed to the
   * constructor and false otherwise
   *
   *@return   true if an id3v2 tag exists in the file passed to the ctor
   */
  public boolean tagExists() {
	return exists;
  }


  /**
   * Determines the new amount of padding to use. If the user has not changed
   * the amount of padding then existing padding will be overwritten instead of
   * increasing the size of the file. That is only if there is a sufficient
   * amount of padding for the updated tag.
   *
   *@return   the new amount of padding
   */
  private int getUpdatedPadding() {
	int pad = padding;
	int size = frames.getLength();
	int sizeDiff = 0;

	if (head.getExtendedHeader()) {
	  size += ext_head.getSize();
	}

	size += padding;
	sizeDiff = size - writtenTagSize;

	if ((padding == writtenPadding) && (sizeDiff != 0) && exists) {
	  if (sizeDiff < 0) {
		pad += Math.abs(sizeDiff);
	  } else if (sizeDiff <= padding) {
		pad -= sizeDiff;
	  } else {
		// As the id3 team recommends, double the size of the tag
		// if it needs to get bigger
		int newTagSize = 2 * writtenTagSize;
		while (newTagSize < size) {
		  newTagSize += writtenTagSize;
		}

		if (newTagSize <= NEWTAG_LIMIT) {
		  pad = newTagSize - size;
		} else {
		  // Gee if it's over the limit this tag is pretty big,
		  // so screw padding altogether
		  pad = 0;
		}
	  }
	}

	return pad;
  }


  /**
   * Returns the size of this id3v2 tag. This includes only the frames, extended
   * header, and padding. For the size of the entire tag including the header
   * and footer, use getTotalSize method.
   *
   *@return   the size (in bytes) of the id3v2 frames, extended header, footer
   */
  public int getSize() {
	int size = frames.getLength();
	int sizeDiff = 0;

	if (head.getExtendedHeader()) {
	  size += ext_head.getSize();
	}

	size += padding;
	sizeDiff = size - writtenTagSize;

	if ((padding == writtenPadding) && (sizeDiff != 0)) {
	  if ((sizeDiff < 0) || (sizeDiff <= padding)) {
		size = head.getTagSize();
	  }
	}

	return size;
  }


  /**
   * Returns the actual size of the tag when written. Includes the header,
   * extended header, frames, padding, and footer.
   *
   *@return   the size (in bytes) of the entire id3v2 tag
   */
  public int getTotalSize() {
	int size = getSize();

	size += head.getHeaderSize();

	if (head.getFooter()) {
	  size += foot.getFooterSize();
	}

	return size;
  }


  /**
   * Returns the current number of padding bytes in this id3v2 tag.
   *
   *@return   the current number of padding bytes in this id3v2 tag
   */
  public int getPadding() {
	return padding;
  }


  /**
   * Set the amount of padding to use when writing this tag. There cannot be any
   * padding if a footer exists. Nothing will happen if this function is called
   * and a footer exists or if the number is negative.
   *
   *@param pad  the amount of padding to use when writing this tag
   */
  public void setPadding(int pad) {
	if (!head.getFooter() && (pad >= 0)) {
	  padding = pad;
	}
  }


  /**
   * Return a string representation of this object. This includes all data
   * contained in all parts of this tag.
   *
   *@return   a string representation of this object
   */
  public String toString() {
	String str = head.toString();

	str += "\nPadding:\t\t\t" + getPadding() + " bytes"
		   + "\nTotalSize:\t\t\t" + getTotalSize() + " bytes";
	;

	if (head.getExtendedHeader()) {
	  str += "\n" + ext_head.toString();
	}

	str += "\n" + frames.toString();

	if (head.getFooter()) {
	  str += foot.toString();
	}

	return str;
  }


  /**
   * Copies information from the ID3Tag parameter and inserts it into this tag.
   * Previous data will be overwritten. [NOT IMPLEMENTED]
   *
   *@param tag  the tag to copy from
   */
  public void copyFrom(ID3Tag tag) {
	// Not implemented yet
  }

}



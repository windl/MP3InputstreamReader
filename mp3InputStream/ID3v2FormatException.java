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
public class ID3v2FormatException extends ID3Exception {

  /**
   * Create an ID3v2FormatException with a default message
   */
  public ID3v2FormatException() {
	super("ID3v2 tag is not formatted correctly.");
  }


  /**
   * Create an ID3v2FormatException with a specified message
   *
   *@param msg  the message for this exception
   */
  public ID3v2FormatException(String msg) {
	super(msg);
  }

}


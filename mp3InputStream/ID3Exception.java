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
public class ID3Exception extends Exception {

	private static final long serialVersionUID = 8672805037560677518L;

	/**
   * Creates an ID3Exception with a default message
   *
   */
  public ID3Exception() {
	super("ID3Exception");
  }
  
  /**
   * Creates an ID3Exception with a specified message
   *
   * @param msg the message for this exception
   */
  public ID3Exception(String msg) {
	super(msg);
  }

}// ID3Exception


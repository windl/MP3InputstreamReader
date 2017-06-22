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
public class CorruptHeaderException extends ID3Exception {
  
  private static final long serialVersionUID = 1L;

  /**
   * Create a CorruptHeaderException with a default message
   *
   */
  public CorruptHeaderException() {
	super("Header is corrupt");
  }
  
  /**
   * Create a CorruptHeaderException with a specified message
   *
   * @param msg the message for this exception
   */
  public CorruptHeaderException(String msg) {
	super(msg);
  }
  
}// CorruptHeaderException


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
public class ID3FieldDataException extends ID3Exception {
  
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
   * Create an ID3FieldDataException with a default message
   *
   */
  public ID3FieldDataException() {
	super("Invalid data supplied to ID3 tag.");
  }
  
  /**
   * Create an ID3FieldDataException with the specified message
   *
   * @param msg a String specifying the specific problem encountered
   */
  public ID3FieldDataException(String msg) {
	super(msg);
  }
  
} // ID3FieldDataException


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
public class NoMPEGFramesException extends ID3Exception {

  /**
   * Create a NoMPEGFramesException with a default message.
   */
  public NoMPEGFramesException() {
	super("The file specified is not a valid MPEG.");
  }


  /**
   * Create a NoMPEGFramesException with a specified message.
   *
   *@param msg  the message for this exception
   */
  public NoMPEGFramesException(String msg) {
	super(msg);
  }

}
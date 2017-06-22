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
public interface ID3Tag {
    

	/**
	 * Returns a binary representation of the tag as it would appear in
	 * a file.
	 *
	 * @return a binary representation of the tag
	 */
	public byte[] getBytes();

} // ID3Tag


package mediaGate.generalFrontend.mp3InputStream;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class MP3InputStream extends BufferedInputStream{

	/**
	 * hält die Länge der aktuellen Datei
	 * Diese Information muss für das Auslesen des ID2-Tags mit übergeben werden.
	 * Im Falle des ID1-Tags werden diese Informationen dann berichtigt.
	 */
	private int fileLen = 0;
	private Object closeLock = new Object();
	
	public MP3InputStream(InputStream in,int len){
		super(in,len);
		fileLen = len;
	}
	
	/**
	 * Gibt die aktuelle Position des Pointers zurück
	 * @return
	 */
	public int getCurrentPos(){
		return pos;
	}
	
	/**
	 * Springt an eine definierte Position im Buffer
	 * @param seekPos
	 */
	public void seek(long seekPos){
		if(seekPos<0){
			seekPos = 0;
		}
		pos = (int)seekPos;
	}
	
	/**
	 * Liest den Stream komplett ein.
	 * Diese Funktion wird für den ID1-Header benötigt.
	 */
	public void readStreamTotaly(){
		
		int len = buf.length;
		pos = 0;
		try{
			reset();
			int r = read(buf, 0, len);
			
			byte[] b = new byte[r];
			System.arraycopy(buf, 0, b, 0, r);
			buf = b;
			fileLen = r;
			
		}catch(Exception error){
			error.printStackTrace();
		}
	}
	
	/**
	 * Diese Funktion liest einen INT ein bestehend aus 4 Bytes
	 * @return
	 * @throws IOException
	 */
	public int readInt() throws IOException {
	        int ch1 = this.read();
	        int ch2 = this.read();
	        int ch3 = this.read();
	        int ch4 = this.read();
	        if ((ch1 | ch2 | ch3 | ch4) < 0)
	            throw new EOFException();
	        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
	}
	
	/**
	 * Diese Funktion liest ein Byte ein
	 * @return
	 * @throws IOException
	 */
	public byte readByte()throws IOException{
		int ch = this.read();
        if (ch < 0)
            throw new EOFException();
        return (byte)(ch);
	}
	
	/**
	 * Interpretiert das Zeichen als Boolean
	 * @return
	 * @throws IOException
	 */
	public boolean readBoolean() throws IOException {
        int ch = this.read();
        if (ch < 0)
            throw new EOFException();
        return (ch != 0);
    }
	
	/**
	 * Liest ein UnsignedByte aus
	 * @return
	 * @throws IOException
	 */
    public final int readUnsignedByte() throws IOException {
        int ch = this.read();
        if (ch < 0)
            throw new EOFException();
        return ch;
    }
    
    /**
     * Liest einen Short aus
     * @return
     * @throws IOException
     */
    public final short readShort() throws IOException {
        int ch1 = this.read();
        int ch2 = this.read();
        if ((ch1 | ch2) < 0)
            throw new EOFException();
        return (short)((ch1 << 8) + (ch2 << 0));
    }
    
    /**
     * liest einen Unsigned Short aus
     * @return
     * @throws IOException
     */
    public final int readUnsignedShort() throws IOException {
        int ch1 = this.read();
        int ch2 = this.read();
        if ((ch1 | ch2) < 0)
            throw new EOFException();
        return (ch1 << 8) + (ch2 << 0);
    }
    
    /**
     * Liest einen Character aus
     * @return
     * @throws IOException
     */
    public final char readChar() throws IOException {
        int ch1 = this.read();
        int ch2 = this.read();
        if ((ch1 | ch2) < 0)
            throw new EOFException();
        return (char)((ch1 << 8) + (ch2 << 0));
    }
    
    /**
     * Liest einen Long aus
     * @return
     * @throws IOException
     */
    public final long readLong() throws IOException {
        return ((long)(readInt()) << 32) + (readInt() & 0xFFFFFFFFL);
    }
    
    /**
     * Liest einen Double aus
     * @return
     * @throws IOException
     */
    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }
    
    /**
     * Schliesst den Stream
     */
	public void closeStream(){
		synchronized (closeLock) {
			try{
				close(); 
			}catch(Exception error){
				error.printStackTrace();
			}
		}
	}
	
	/**
	 * Gibt die Länge der Datei zurück
	 * @return
	 */
	public long length(){
		return (long)fileLen;
	}
	
}

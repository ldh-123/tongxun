package ldh.util.encrypt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectToByteUtil {

	public static byte[] toBytes(Object o) {
		if (!(o instanceof Serializable)) {
			throw new java.lang.IllegalArgumentException("object must be Serializable");
		}
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
			ObjectOutputStream oout = new ObjectOutputStream(out);
			oout.writeObject(o);
			return out.toByteArray();
		} catch (IOException e) {
			throw new java.lang.IllegalArgumentException("object not be serializable");
		}
	}
	
	public static <T> T toBean(byte[] bytes) {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			ObjectInputStream oin = new ObjectInputStream(in);
			return (T) oin.readObject();
		} catch (Exception e) {
			throw new java.lang.IllegalArgumentException("bytes not be deserializable");
		}
	}
}

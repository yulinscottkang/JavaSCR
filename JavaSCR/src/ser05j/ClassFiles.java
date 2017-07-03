package ser05j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClassFiles {
  public static String classAsFile(final Class<?> clazz) {
    return classAsFile(clazz, true);
  }
  
  public static String classAsFile(final Class<?> clazz, boolean suffix) {
    String str;
    if (clazz.getEnclosingClass() == null) {
      str = clazz.getName().replace(".", "/"); //$NON-NLS-1$ //$NON-NLS-2$
    } else {
      str = classAsFile(clazz.getEnclosingClass(), false) + "$" + clazz.getSimpleName(); //$NON-NLS-1$
    }
    if (suffix) {
      str += ".class";       //$NON-NLS-1$
    }
    return str;  
  }

  public static byte[] classAsBytes(final Class<?> clazz) {
    try {
      final byte[] buffer = new byte[1024];
      final String file = classAsFile(clazz);
      final InputStream in = ClassFiles.class.getClassLoader().getResourceAsStream(file);
      if (in == null) {
        throw new IOException("couldn't find '" + file + "'"); //$NON-NLS-1$ //$NON-NLS-2$
      }
      final ByteArrayOutputStream out = new ByteArrayOutputStream();
      int len;
      while ((len = in.read(buffer)) != -1) {
        out.write(buffer, 0, len);
      }
      return out.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
}
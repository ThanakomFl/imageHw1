import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ipc2 {

   public static void main(String args[]) throws IOException {  
      final BufferedInputStream stream_in = new BufferedInputStream(new FileInputStream("SEM256_256.pgm"));
      final BufferedOutputStream stream_out = new BufferedOutputStream(new FileOutputStream("SEM256_256_out.pgm"));
        try {
            next(stream_in);
            final int row = Integer.parseInt(next(stream_in));
            final int col = Integer.parseInt(next(stream_in));
            final int max = Integer.parseInt(next(stream_in));

            final int[][] image = new int[row][col];
            final float[] HA = new float[max + 1];
            final float[] PA = new float[max + 1];
            final int[] DA = new int[max + 1];
            final float[] DP = new float[max + 1];
            final int[] DB = new int[max + 1];

            for (int i = 0; i < row; ++i) {
                for (int j = 0; j < col; ++j) {
                    final int p = stream_in.read();
                    image[i][j] = p;
                    DA[p]++;
                }
            }

            for (int i = 0; i <= max; i++) {
               HA[i] = (float) DA[i] / (row * col);
               if (i == 0) {
                  PA[i] = HA[i];
               } else {
                  PA[i] = PA[i - 1] + HA[i];
               }
               DP[i] = max * PA[i];
               DB[i] = Math.round(DP[i]);
            }
   
            for (int i = 0; i <= max; i++) {
               System.out.println(i + " " + DA[i] + " " + HA[i] + " " + PA[i] + " " + DP[i] + " " + DB[i]);
            }

            stream_out.write("P5".getBytes());
            stream_out.write("\n".getBytes());
            stream_out.write(Integer.toString(image[0].length).getBytes());
            stream_out.write(" ".getBytes());
            stream_out.write(Integer.toString(image.length).getBytes());
            stream_out.write("\n".getBytes());
            stream_out.write(Integer.toString(255).getBytes());
            stream_out.write("\n".getBytes());
            for (int i = 0; i < image.length; ++i) {
                for (int j = 0; j < image[0].length; ++j) {
                     final int p = image[i][j];
                     image[i][j] = DB[p];
                     stream_out.write(image[i][j]);
                }
            }

        } finally {
            stream_in.close();
            stream_out.close();
        }
   }

   private static String next(final InputStream stream) throws IOException {
      final List<Byte> bytes = new ArrayList<Byte>();
      while (true) {
          final int b = stream.read();

          if (b != -1) {

              final char c = (char) b;
              if (c == '#') {
                  int d;
                  do {
                      d = stream.read();
                  } while (d != -1 && d != '\n' && d != '\r');
              } else if (!Character.isWhitespace(c)) {
                  bytes.add((byte) b);
              } else if (bytes.size() > 0) {
                  break;
              }

          } else {
              break;
          }

      }
      final byte[] bytesArray = new byte[bytes.size()];
      for (int i = 0; i < bytesArray.length; ++i)
          bytesArray[i] = bytes.get(i);
      return new String(bytesArray);
  }

}
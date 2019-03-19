import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ipc3 {

   public static void main(String args[]) throws IOException {  
      final BufferedInputStream stream_in1 = new BufferedInputStream(new FileInputStream("SanFranPeak_red.pgm"));
      final BufferedInputStream stream_in2 = new BufferedInputStream(new FileInputStream("SanFranPeak_green.pgm"));
      final BufferedInputStream stream_in3 = new BufferedInputStream(new FileInputStream("SanFranPeak_blue.pgm"));
      final BufferedOutputStream stream_out1 = new BufferedOutputStream(new FileOutputStream("SanFranPeak_out1.pgm"));
      final BufferedOutputStream stream_out2 = new BufferedOutputStream(new FileOutputStream("SanFranPeak_out2.pgm"));
      final BufferedOutputStream stream_out3 = new BufferedOutputStream(new FileOutputStream("SanFranPeak_out3.pgm"));
        try {
            next(stream_in1);
            final int col = Integer.parseInt(next(stream_in1));
            final int row = Integer.parseInt(next(stream_in1));
            Integer.parseInt(next(stream_in1));

            int loop = 4;
            while(loop > 0){
                loop--;
                next(stream_in2);
                next(stream_in3);
            }

            final int[][] imageR = new int[row][col];
            final int[][] imageG = new int[row][col];
            final int[][] imageB = new int[row][col];
            final int[][] image1 = new int[row][col];
            final int[][] image2 = new int[row][col];
            final int[][] image3 = new int[row][col];

            for (int i = 0; i < row; ++i) {
                for (int j = 0; j < col; ++j) {
                    final int p1 = stream_in1.read();
                            imageR[i][j] = p1;
                    final int p2 = stream_in2.read();
                            imageG[i][j] = p2;
                    final int p3 = stream_in3.read();
                            imageB[i][j] = p3;
                    int equl1 = 2*(p2)-p1-p3;
                            if(equl1 > 255) equl1 = 255;
                            if(equl1 < 0) equl1 = 0;
                            image1[i][j] = equl1;
                            //System.out.println(2*p2 +" " + p1 +" " + p3 +" " +image1[i][j]+" ");
                    int equl2 = p1 - p3;
                            if(equl2 > 255) equl2 = 255;
                            if(equl2 < 0) equl2 = 0;
                            image2[i][j] = equl2;
                    int equl3 = Math.round((p1+p2+p3)/3);
                            if(equl3 > 255) equl3 = 255;
                            if(equl3 < 0) equl3 = 0;
                            image3[i][j] = equl3;
                }
            }

            write(image1,stream_out1);
            write(image2,stream_out2);
            write(image3,stream_out3);

        } finally {
            stream_in1.close();
            stream_in2.close();
            stream_in3.close();
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

  private static void write(final int[][] image,final OutputStream stream) throws IOException {
        stream.write("P5".getBytes());
        stream.write("\n".getBytes());
        stream.write(Integer.toString(image[0].length).getBytes());
        stream.write(" ".getBytes());
        stream.write(Integer.toString(image.length).getBytes());
        stream.write("\n".getBytes());
        stream.write(Integer.toString(255).getBytes());
        stream.write("\n".getBytes());
        for (int i = 0; i < image.length; ++i) {
            for (int j = 0; j < image[0].length; ++j) {
                final int p = image[i][j];
                stream.write(p);
            }
        }
        stream.close();
    }

}
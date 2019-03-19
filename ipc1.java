import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ipc1 {

   public static void main(String args[]) throws IOException {  
      final BufferedInputStream stream_in = new BufferedInputStream(new FileInputStream("scaled_shapes.pgm"));
        try {
            next(stream_in);
            final int row = Integer.parseInt(next(stream_in));
            final int col = Integer.parseInt(next(stream_in));
            final int max = Integer.parseInt(next(stream_in));

            final int[][] image = new int[row][col];
            final int[] D = new int[max+1];
            Stack<Integer> q = new Stack<>();

            for (int i = 0; i < row; ++i) {
                for (int j = 0; j < col; ++j) {
                    final int p = stream_in.read();
                    image[i][j] = p;
                    D[p]++;
                }
            }

            System.out.print("Gray level of Object in the image : ");

            for (int i=0 ; i < max ; i++){
                if(D[i]>1000){
                    if(q.empty()){
                        System.out.println(i);
                    }else{
                        System.out.println("                                    "+i);
                    }
                    q.add(i);
                }
            }
            System.out.println("# of all Object = "+q.size());
            while (q.size() != 0){
                final int[][] m = new int[row][col];
                int removedele = q.pop(); 
                for (int i = 0; i < row; ++i) {
                    for (int j = 0; j < col; ++j) {
                        if(image[i][j]==removedele){
                            m[i][j] = 1;
                        }else{
                            m[i][j] = 0;
                        }
                    }
                }
                double theata = pqN(2, 0, m,row,col) + pqN(0, 2, m,row,col);
                System.out.println("moment of an object Gray level (" + removedele + ") = " + theata);
            }

        } finally {
            stream_in.close();
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

  private static int pqmoment(int p , int q , int[][] image,int row , int col) throws IOException{
      int m = 0;
      for (int x = 0; x < row; ++x) {
        for (int y = 0; y < col; ++y) {
            if(image[x][y] > 0) m += Math.pow(x,p)*Math.pow(y,q)*1;
            else m += 0;
        }
    }
      return m;
  }

  private static double pqHu(int p , int q , int[][] image,int row , int col) throws IOException{
    int u = 0;
    int m10 = (pqmoment(1, 0, image,row,col));
    int m00 = (pqmoment(0, 0, image,row,col));
    int m01 = (pqmoment(0, 1, image,row,col));
    for (int x = 0; x < row; ++x) {
      for (int y = 0; y < col; ++y) {
            if(image[x][y] > 0) u += Math.pow((x-(m10/m00)),p)*Math.pow((y-(m01/m00)),q);
            else u += 0;
      }
    }

    return u;
  }

  private static double pqN(int p , int q , int[][] image,int row,int col) throws IOException{
    double n = 0;
        n = pqHu(p, q, image,row,col) / Math.pow(pqHu(0, 0, image,row,col),((p+q/2)+1));
    return n;
  }

}
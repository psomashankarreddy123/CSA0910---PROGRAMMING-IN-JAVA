import java.io.*;
import java.nio.file.*;

public class task2 {
    static long countUnbuffered(Path file) throws IOException {
        long lines=0;
        try(InputStream in=new FileInputStream(file.toFile())) {
            int value;
            while((value=in.read())!=-1) if(value=='\n') lines++;
        }
        return lines;
    }
    static long countBuffered(Path file) throws IOException {
        long lines=0;
        try(BufferedInputStream in=new BufferedInputStream(Files.newInputStream(file), 64*1024)) {
            byte[] buffer=new byte[64*1024]; int n;
            while((n=in.read(buffer))!=-1)
                for(int i=0;i<n;i++) if(buffer[i]=='\n') lines++;
        }
        return lines;
    }
    static void createFile(Path file, int megabytes) throws IOException {
        byte[] row="attendance-record-123456789\n".getBytes();
        long target=megabytes*1024L*1024L;
        try(OutputStream out=new BufferedOutputStream(Files.newOutputStream(file))) {
            long written=0;
            while(written<target) { int len=(int)Math.min(row.length,target-written); out.write(row,0,len); written+=len; }
        }
    }
    static void measure(Path file) throws IOException {
        long start=System.nanoTime(); long a=countUnbuffered(file); long t1=System.nanoTime()-start;
        start=System.nanoTime(); long b=countBuffered(file); long t2=System.nanoTime()-start;
        System.out.printf("%s | lines=%d | unbuffered=%.2f ms | buffered=%.2f ms%n",
                file.getFileName(),a,t1/1e6,t2/1e6);
        if(a!=b) throw new IllegalStateException("Line counts differ");
    }
    public static void main(String[] args) throws Exception {
        if(args.length>0) {
            Path file=Path.of(args[0]); measure(file); return;
        }
        for(int mb:new int[]{1,10,50}) {
            Path f=Path.of("attendance-"+mb+"MB.log");
            createFile(f,mb);
            measure(f);
        }
    }
}

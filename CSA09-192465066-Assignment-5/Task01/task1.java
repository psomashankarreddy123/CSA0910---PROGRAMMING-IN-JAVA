import java.io.*;
import java.nio.file.*;
import java.util.*;

public class task1 {
    static final Path TEXT = Path.of("passes.txt");
    static final Path BINARY = Path.of("passes.dat");

    static class Pass {
        String id, regNo, name, stop, validity;
        int route;
        double amount;
        Pass(String id, String regNo, String name, int route, String stop, String validity, double amount) {
            this.id=id; this.regNo=regNo; this.name=name; this.route=route;
            this.stop=stop; this.validity=validity; this.amount=amount;
        }
        public String toString() {
            return id+" | "+regNo+" | "+name+" | Route "+route+" | "+stop+" | "+validity+" | "+amount;
        }
    }

    static String encode(Pass p) {
        return String.join("|", esc(p.id), esc(p.regNo), esc(p.name), String.valueOf(p.route),
                esc(p.stop), esc(p.validity), String.valueOf(p.amount));
    }
    static String esc(String s) { return s.replace("\\", "\\\\").replace("|", "\\|"); }
    static String[] decode(String line) {
        List<String> fields=new ArrayList<>(); StringBuilder b=new StringBuilder(); boolean slash=false;
        for(char c:line.toCharArray()) {
            if(slash) { b.append(c); slash=false; }
            else if(c=='\\') slash=true;
            else if(c=='|') { fields.add(b.toString()); b.setLength(0); }
            else b.append(c);
        }
        if(slash) b.append('\\');
        fields.add(b.toString());
        return fields.size()==7 ? fields.toArray(new String[0]) : null;
    }
    static void add(Pass p) throws IOException {
        Files.writeString(TEXT, encode(p)+System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
    static Pass find(String id) throws IOException {
        if(!Files.exists(TEXT)) return null;
        try(BufferedReader r=Files.newBufferedReader(TEXT)) {
            String line;
            while((line=r.readLine())!=null) {
                String[] f=decode(line);
                if(f==null) continue;
                try {
                    if(f[0].equals(id)) return new Pass(f[0],f[1],f[2],Integer.parseInt(f[3]),f[4],f[5],Double.parseDouble(f[6]));
                } catch(NumberFormatException ignored) { }
            }
        }
        return null;
    }
    static void listRoute(int route) throws IOException {
        if(!Files.exists(TEXT)) return;
        try(BufferedReader r=Files.newBufferedReader(TEXT)) {
            String line;
            while((line=r.readLine())!=null) {
                String[] f=decode(line);
                if(f!=null) try { if(Integer.parseInt(f[3])==route) System.out.println(Arrays.toString(f)); }
                catch(NumberFormatException ignored) { }
            }
        }
    }
    static void writeBinary(Pass p) throws IOException {
        try(DataOutputStream out=new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(BINARY,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)))) {
            out.writeUTF(p.id); out.writeUTF(p.regNo); out.writeUTF(p.name); out.writeInt(p.route);
            out.writeUTF(p.stop); out.writeUTF(p.validity); out.writeDouble(p.amount);
        }
    }
    static void readBinary() throws IOException {
        if(!Files.exists(BINARY)) return;
        try(DataInputStream in=new DataInputStream(new BufferedInputStream(Files.newInputStream(BINARY)))) {
            while(true) try {
                Pass p=new Pass(in.readUTF(),in.readUTF(),in.readUTF(),in.readInt(),in.readUTF(),in.readUTF(),in.readDouble());
                System.out.println(p);
            } catch(EOFException end) { break; }
        }
    }
    public static void main(String[] args) throws Exception {
        Files.deleteIfExists(TEXT); Files.deleteIfExists(BINARY);
        Pass p1=new Pass("P1001","192311001","Arun|Kumar",12,"Tambaram","2027-03-31",4500);
        Pass p2=new Pass("P1002","192311002","Divya",7,"Chromepet","2027-03-31",3800);
        add(p1); add(p2); writeBinary(p1); writeBinary(p2);
        System.out.println("Find P1001: "+find("P1001"));
        System.out.println("Passes on route 7:"); listRoute(7);
        System.out.println("Binary records:"); readBinary();
    }
}

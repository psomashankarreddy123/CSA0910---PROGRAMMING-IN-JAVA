import java.io.*;
import java.nio.file.*;
import java.sql.Connection;
import java.util.*;

public class task4 {
    static class Hostel implements Serializable {
        private static final long serialVersionUID=1L;
        List<Room> rooms=new ArrayList<>();
    }
    static class Room implements Serializable {
        private static final long serialVersionUID=1L;
        int roomNumber; List<Resident> residents=new ArrayList<>();
        Room(int roomNumber) { this.roomNumber=roomNumber; }
    }
    static class Resident implements Serializable {
        private static final long serialVersionUID=1L;
        String name, registerNumber, phoneNumber;
        transient String password;
        transient Connection liveConnection;
        Resident(String name,String registerNumber,String password,String phoneNumber) {
            this.name=name; this.registerNumber=registerNumber; this.password=password; this.phoneNumber=phoneNumber;
        }
        public String toString() {
            return name+" | "+registerNumber+" | phone="+phoneNumber+" | password="+password+" | connection="+liveConnection;
        }
    }
    public static void main(String[] args) throws Exception {
        Hostel hostel=new Hostel();
        Room room=new Room(204);
        room.residents.add(new Resident("Arun","192311001","secret123","9876543210"));
        hostel.rooms.add(room);
        Path file=Path.of("hostel.ser");
        try(ObjectOutputStream out=new ObjectOutputStream(Files.newOutputStream(file))) { out.writeObject(hostel); }
        try(ObjectInputStream in=new ObjectInputStream(Files.newInputStream(file))) {
            Hostel restored=(Hostel)in.readObject();
            Resident r=restored.rooms.get(0).residents.get(0);
            System.out.println("Restored resident: "+r);
            System.out.println("Transient fields are null after deserialization.");
        }
    }
}

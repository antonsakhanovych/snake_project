import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static String filePath = "scoreboard.bin";

    private static void writeLengthOfName(FileOutputStream fos, int length) throws IOException {
        fos.write(length);
    }

    private static int readLengthOfName(FileInputStream fis) throws IOException {
        return fis.read();
    }

    private static void writeCharacter(FileOutputStream fos, char charToWrite) throws IOException {
        int firstByte = (charToWrite >> 8) & 0xFF;
        int secondByte =  charToWrite & 0xFF;
        fos.write(firstByte);
        fos.write(secondByte);
    }

    private static char readCharacter(FileInputStream fis) throws IOException {
        int firstByte = (fis.read() << 8);
        int secondByte = fis.read();
        return (char) (firstByte | secondByte);
    }

    private static void writeName(FileOutputStream fos, String name) throws IOException {
        for(char ch: name.toCharArray()){
            writeCharacter(fos, ch);
        }
    }

    private static String readName(FileInputStream fis, int length) throws IOException {
        StringBuilder strBld = new StringBuilder();
        for(int i = 0; i < length; i++){
            char ch = readCharacter(fis);
            strBld.append(ch);
        }
        return strBld.toString();
    }


    private static void writeScore(FileOutputStream fos, long scoreToWrite) throws IOException {
        for (int i = 3; i >= 0; i--){
            int byteToWrite = (int)((scoreToWrite >> (i * 8)) & 0xFF);
            fos.write(byteToWrite);
        }
    }

    private static long readScore(FileInputStream fis) throws IOException {
        long score = 0;
        for(int i = 3; i >= 0; i--){
            int byteRead = fis.read() << (i * 8);
            score |= byteRead;
        }
        return score;
    }

    public static void writeInfo(String name, long score){
        try(FileOutputStream fos = new FileOutputStream(filePath, true)){
            writeLengthOfName(fos, name.length());
            writeName(fos, name);
            writeScore(fos, score);
        } catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }

    public static List<Record> readInfo(){
        List<Record> records = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream(filePath)){
            while(fis.available() > 0){
                int length = readLengthOfName(fis);
                String name = readName(fis, length);
                long score = readScore(fis);
                records.add(new Record(name, score));
            }
        } catch (IOException exception){
            System.out.println(exception.getMessage());
        }
        return records;
    }
}

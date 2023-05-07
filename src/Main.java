import org.w3c.dom.xpath.XPathNamespace;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress save1 = new GameProgress(11, 1, 2, 123);
        GameProgress save2 = new GameProgress(25, 2, 10, 1000);
        GameProgress save3 = new GameProgress(100, 5, 150, 332042);

        saveGame("save1.dat", save1);
        saveGame("save2.dat", save2);
        saveGame("save3.dat", save3);
        zipFiles("save1.dat", "C:/Games/Games/savegames/");
        zipFiles("save2.dat", "C:/Games/Games/savegames/");
        zipFiles("save3.dat", "C:/Games/Games/savegames/");
        delete();

    }

    public static void zipFiles(String fileToZip, String fileToPath) {
        String zipFileName = fileToZip + ".zip";
        try (FileOutputStream fos = new FileOutputStream(fileToPath + zipFileName);
             ZipOutputStream zout = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(fileToPath + fileToZip)){
            ZipEntry entry = new ZipEntry(fileToZip);
            zout.putNextEntry(entry);
            byte[] buffer = new byte[fis.available()];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zout.write(buffer, 0, length);
            }
            zout.closeEntry();
        } catch (IOException ex) {
            System.out.println("Ошибка при архивации файла " + ex.getMessage());
        }
    }
    public static void saveGame(String fileToPath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream("C:/Games/Games/savegames/" + fileToPath);
            ObjectOutputStream oos = new ObjectOutputStream(fos)){
                oos.writeObject(gameProgress);
            }catch(IOException ex){
                System.out.println("Ошибка при сохранении игры: " + ex.getMessage());
            }
        }
        public static void delete(){
            File savesDir = new File("C:/Games/Games/savegames/");
            File[] saves = savesDir.listFiles();
            for (File save : saves) {
                if(!save.getName().endsWith(".zip")) {
                    save.delete();
                }
            }
        }
}

//Если файл сохранения уже был заархивирован, этот код создаст новый архив,
// но с тем же самым именем, что может привести к перезаписи старого архива.
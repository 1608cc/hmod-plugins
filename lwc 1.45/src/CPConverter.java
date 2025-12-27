import com.griefcraft.sql.PhysDB;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ConnectException;

public class CPConverter implements Runnable {
  private String[] CHESTS_FILES = new String[] { "../lockedChests.txt", "lockedChests.txt" };
  
  private int converted = 0;
  
  private Player player;
  
  private PhysDB physicalDatabase;
  
  public static void main(String[] paramArrayOfString) throws Exception {
    new CPConverter();
  }
  
  public CPConverter() {
    (new Thread(this)).start();
    this.physicalDatabase = new PhysDB();
  }
  
  public CPConverter(Player paramPlayer) {
    this();
    this.player = paramPlayer;
  }
  
  public void convertChests() throws FileNotFoundException, IOException {
    File file = null;
    for (String str1 : this.CHESTS_FILES) {
      file = new File(str1);
      if (file != null && file.exists())
        break; 
    } 
    if (file == null || !file.exists())
      throw new FileNotFoundException("No Chest Protect chest database found"); 
    byte b = 0;
    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
    String str;
    while ((str = bufferedReader.readLine()) != null) {
      str = str.trim();
      b++;
      if (str.startsWith("#"))
        continue; 
      String[] arrayOfString1 = str.split(",");
      if (arrayOfString1.length < 5)
        continue; 
      String str1 = arrayOfString1[0];
      int i = Integer.parseInt(arrayOfString1[1]);
      int j = Integer.parseInt(arrayOfString1[2]);
      int k = Integer.parseInt(arrayOfString1[3]);
      int m = Integer.parseInt(arrayOfString1[4]);
      byte b1 = -1;
      String str2 = "";
      if (m == 1) {
        m = 0;
      } else if (m > 1) {
        if (m == 3) {
          b1 = 0;
        } else if (m == 4) {
          b1 = 1;
        } 
        m = 2;
      } 
      if (arrayOfString1.length > 5)
        str2 = arrayOfString1[5].trim(); 
      log(String.format("Registering chest to %s at location {%d,%d,%d}", new Object[] { str1, Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k) }));
      this.physicalDatabase.registerProtectedEntity(m, str1, "", i, j, k);
      this.converted++;
      if (b1 == -1)
        continue; 
      int n = this.physicalDatabase.loadProtectedEntity(i, j, k).getID();
      String[] arrayOfString2 = str2.split(";");
      for (String str3 : arrayOfString2) {
        this.physicalDatabase.registerProtectionRights(n, str3, 0, b1);
        log(String.format("  -> Registering rights to %s on chest %d", new Object[] { str3, Integer.valueOf(n) }));
      } 
    } 
  }
  
  public void log(String paramString) {
    System.out.println(paramString);
    if (this.player != null)
      this.player.sendMessage(paramString); 
  }
  
  public void run() {
    try {
      log("LWC Conversion tool for Chest Protect chests");
      log("");
      log("Initializing sqlite");
      boolean bool = this.physicalDatabase.connect();
      if (!bool)
        throw new ConnectException("Failed to connect to the sqlite database"); 
      this.physicalDatabase.load();
      log("Done.");
      log("Starting conversion of Chest Protect chests");
      log("");
      convertChests();
      log("Done.");
      log("");
      log("Converted >" + this.converted + "< Chest Protect chests to LWC");
      log("LWC database now holds " + this.physicalDatabase.entityCount() + " protected chests!");
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}


/* Location:              C:\Users\1608c\Desktop\LWC.jar!\CPConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
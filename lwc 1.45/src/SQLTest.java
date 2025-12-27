import com.griefcraft.logging.Logger;
import com.griefcraft.sql.MemDB;
import com.griefcraft.sql.PhysDB;
import com.griefcraft.util.Config;

import java.util.Random;

public class SQLTest {
  private Logger logger = Logger.getLogger("SQLTest");
  
  private PhysDB phys;
  
  private MemDB mem;
  
  public static void main(String[] paramArrayOfString) throws Exception {
    new SQLTest();
  }
  
  public SQLTest() throws Exception {
    Config.init();
    Config.getInstance().setProperty("db-path", "E:\\Java\\LWC\\lwc.db");
    this.phys = new PhysDB();
    this.mem = new MemDB();
    this.phys.connect();
    this.phys.load();
    this.mem.connect();
    this.mem.load();
    speedTest();
  }
  
  private void createChests(int paramInt) throws Exception {
    Random random = new Random();
    this.phys.connection.setAutoCommit(false);
    while (paramInt > 0) {
      this.phys.registerProtectedEntity(random.nextInt(1), "Hidendra", "", random.nextInt(255), random.nextInt(255), random.nextInt(255));
      paramInt--;
    } 
    this.logger.info("Commiting");
    this.phys.connection.commit();
    this.phys.connection.setAutoCommit(true);
    this.logger.info("Done. Count : " + this.phys.entityCount());
  }
  
  private void speedTest() throws Exception {
    Random random = new Random();
    long l = System.currentTimeMillis();
    byte b = 0;
    int i = 0;
    while (true) {
      long l1 = System.currentTimeMillis() - l;
      int j = (int)(l1 / 1000L);
      String bool = (String) ((j > 0) ? (b / j) : false);
      if (i != j && l1 % 1000L == 0L) {
        this.logger.info(String.format("[%ds] [Q:%d] QUERIES/SEC:%d", new Object[] { Integer.valueOf(j), Integer.valueOf(b), Integer.valueOf(bool) }));
        i = j;
      } 
      this.mem.getModeData("Hidendra", "m" + random.nextInt(50000));
      b++;
    } 
  }
}


/* Location:              C:\Users\1608c\Desktop\LWC.jar!\SQLTest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
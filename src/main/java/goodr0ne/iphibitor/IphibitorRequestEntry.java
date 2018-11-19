package goodr0ne.iphibitor;

class IphibitorRequestEntry {
  private static final long LIFETIME_LIMIT = 60 * 1000;
  //private String ip;
  private long time;

  IphibitorRequestEntry() {
    //ip = "";
    time = System.currentTimeMillis();
  }

  boolean isOld() {
    return (System.currentTimeMillis() - this.time) > LIFETIME_LIMIT;
  }
}

package goodr0ne.iphibitor;

import java.util.ArrayList;

class AlwaysCheckingIphibitor implements Iphibitionable {
  private static final int REQUEST_LIMIT = 50;
  private static ArrayList<IphibitorRequestEntry> entries;

  AlwaysCheckingIphibitor() {
    entries = new ArrayList<>();
  }


  public synchronized void inhibit() throws IphibitorRequestLimitReachedException {
    if (entries.isEmpty()) {
      entries.add(new IphibitorRequestEntry());
      return;
    }
    ArrayList<IphibitorRequestEntry> toDeleteEntries = new ArrayList<>();
    int requestCount = 1;
    for (IphibitorRequestEntry entry : entries) {
      if (entry.isOld()) {
        toDeleteEntries.add(entry);
      } else {
        requestCount++;
      }
    }
    entries.removeAll(toDeleteEntries);
    entries.add(new IphibitorRequestEntry());
    if (requestCount > REQUEST_LIMIT) {
      throw new IphibitorRequestLimitReachedException();
    }
  }
}

package goodr0ne.iphibitor;

import java.util.ArrayList;

@IphibitorStrategy(type=Iphibitionable.class,
        users={IphibitorStrategyUser.IPHIBITOR_CONTROLLER})
class AlwaysCheckingIphibitor implements Iphibitionable {
  private static final int REQUEST_LIMIT = 50;
  private static ArrayList<IphibitorRequestEntry> entries;

  AlwaysCheckingIphibitor() {
    entries = new ArrayList<>();
  }

  public synchronized void inhibit(String ip) throws IphibitorRequestLimitReachedException {
    if (entries.isEmpty()) {
      entries.add(new IphibitorRequestEntry(ip));
      return;
    }
    ArrayList<IphibitorRequestEntry> toDeleteEntries = new ArrayList<>();
    int requestCount = 1;
    for (IphibitorRequestEntry entry : entries) {
      if (entry.isOld()) {
        toDeleteEntries.add(entry);
      } else {
        if (entry.isMatched(ip)) {
          requestCount++;
          if (requestCount > REQUEST_LIMIT) {
            entries.removeAll(toDeleteEntries);
            throw new IphibitorRequestLimitReachedException();
          }
        }
      }
    }
    entries.removeAll(toDeleteEntries);
    entries.add(new IphibitorRequestEntry(ip));
  }
}

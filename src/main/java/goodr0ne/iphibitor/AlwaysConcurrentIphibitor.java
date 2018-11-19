package goodr0ne.iphibitor;

import java.util.concurrent.ConcurrentHashMap;

@IphibitorStrategy(type=Iphibitionable.class,
        users={IphibitorStrategyUser.IPHIBITOR_CONCURRENT_CONTROLLER})
class AlwaysConcurrentIphibitor implements Iphibitionable {
  private static ConcurrentHashMap<String, AlwaysCheckingIphibitor> iphibitors;

  AlwaysConcurrentIphibitor() {
    iphibitors = new ConcurrentHashMap<>();
  }

  public void inhibit(String ip) throws IphibitorRequestLimitReachedException {
    if (iphibitors.isEmpty() || !iphibitors.containsKey(ip)) {
      iphibitors.put(ip, new AlwaysCheckingIphibitor());
    }
    iphibitors.get(ip).inhibit(ip);
  }
}

package goodr0ne.iphibitor;

public interface Iphibitionable {
  void inhibit(String ip) throws IphibitorRequestLimitReachedException;
}

package goodr0ne.iphibitor;

public class AlwaysBlockingIphibitor {

  void inhibit() throws IphibitorRequestLimitReachedException {
    throw new IphibitorRequestLimitReachedException();
  }
}

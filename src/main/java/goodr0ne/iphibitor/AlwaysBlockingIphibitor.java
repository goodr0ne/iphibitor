package goodr0ne.iphibitor;

class AlwaysBlockingIphibitor implements Iphibitionable {

  public void inhibit() throws IphibitorRequestLimitReachedException {
    throw new IphibitorRequestLimitReachedException();
  }
}

package goodr0ne.iphibitor;

class AlwaysBlockingIphibitor {

  void inhibit() throws IphibitorRequestLimitReachedException {
    throw new IphibitorRequestLimitReachedException();
  }
}

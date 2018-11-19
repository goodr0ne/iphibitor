package goodr0ne.iphibitor;

@IphibitorStrategy(type = Iphibitionable.class,
        users = IphibitorStrategyUser.SOME_BLOCKING_CONTROLLER)
class AlwaysBlockingIphibitor implements Iphibitionable {

  public void inhibit(String ip) throws IphibitorRequestLimitReachedException {
    throw new IphibitorRequestLimitReachedException();
  }
}

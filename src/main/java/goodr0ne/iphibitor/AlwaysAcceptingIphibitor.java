package goodr0ne.iphibitor;

@IphibitorStrategy(type = Iphibitionable.class)
class AlwaysAcceptingIphibitor implements Iphibitionable {

  public void inhibit() {}
}

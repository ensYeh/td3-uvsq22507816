package org.uvsq.dns;

public class CmdResolveName implements Commande {
  private final AdresseIP ip;
  public CmdResolveName(String ip) { this.ip = new AdresseIP(ip); }
  @Override public String execute(Dns dns) {
    DnsItem it = dns.getItem(ip);
    return it == null ? "Non trouvé" : it.nom().fqdn();
  }
}

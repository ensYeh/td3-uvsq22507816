package org.uvsq.dns;

public class CmdResolveIp implements Commande {
  private final NomMachine nom;
  public CmdResolveIp(String fqdn) { this.nom = new NomMachine(fqdn); }
  @Override public String execute(Dns dns) {
    DnsItem it = dns.getItem(nom);
    return it == null ? "Non trouvé" : it.ip().value();
  }
}

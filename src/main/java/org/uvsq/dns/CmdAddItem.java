package org.uvsq.dns;

public class CmdAddItem implements Commande {
  private final AdresseIP ip;
  private final NomMachine nom;
  public CmdAddItem(String ip, String fqdn) {
    this.ip = new AdresseIP(ip);
    this.nom = new NomMachine(fqdn);
  }
  @Override public String execute(Dns dns) throws Exception {
    dns.addItem(ip, nom);
    dns.save(); // persist immediately
    return "";  // no output if success
  }
}

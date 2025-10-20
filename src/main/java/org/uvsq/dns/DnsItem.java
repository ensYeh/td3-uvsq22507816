package org.uvsq.dns;

import java.util.Objects;

/** DNS entry: (fqdn, ip). */
public final class DnsItem {
  private final NomMachine nom;
  private final AdresseIP ip;

  public DnsItem(NomMachine nom, AdresseIP ip) {
    this.nom = Objects.requireNonNull(nom);
    this.ip = Objects.requireNonNull(ip);
  }

  public NomMachine nom() { return nom; }
  public AdresseIP ip() { return ip; }

  @Override public String toString() { return ip + " " + nom; }
}

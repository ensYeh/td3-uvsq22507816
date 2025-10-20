package org.uvsq.dns;

import java.util.Objects;

/** Fully qualified host name like www.uvsq.fr (machine + domain). */
public final class NomMachine implements Comparable<NomMachine> {
  private final String fqdn;

  public NomMachine(String fqdn) {
    Objects.requireNonNull(fqdn, "Nom machine null");
    String s = fqdn.trim();
    int dot = s.indexOf('.');
    if (dot <= 0 || dot == s.length() - 1) {
      throw new IllegalArgumentException("Nom qualifié invalide: " + fqdn);
    }
    this.fqdn = s;
  }

  public String fqdn() { return fqdn; }
  public String machine() { return fqdn.substring(0, fqdn.indexOf('.')); }
  public String domaine() { return fqdn.substring(fqdn.indexOf('.') + 1); }

  @Override public String toString() { return fqdn; }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof NomMachine)) return false;
    return fqdn.equals(((NomMachine)o).fqdn);
  }

  @Override public int hashCode() { return fqdn.hashCode(); }

  @Override public int compareTo(NomMachine o) { return this.fqdn.compareTo(o.fqdn); }
}

package org.uvsq.dns;

public class DnsTUI {

  /** Parse one user line into a Commande. */
  public Commande nextCommande(String line) {
    String s = line == null ? "" : line.trim();
    if (s.isEmpty()) return null;

    if (s.equalsIgnoreCase("quit") || s.equalsIgnoreCase("exit")) return new CmdQuit();

    String[] parts = s.split("\\s+");
    // add <ip> <fqdn>
    if (parts[0].equals("add") && parts.length == 3) {
      return new CmdAddItem(parts[1], parts[2]);
    }

    // ls [-a] domain
    if (parts[0].equals("ls")) {
      boolean byIp = false;
      String domain;
      if (parts.length == 2) {
        domain = parts[1];
      } else if (parts.length == 3 && "-a".equals(parts[1])) {
        byIp = true;
        domain = parts[2];
      } else {
        throw new IllegalArgumentException("Usage: ls [-a] domaine");
      }
      return new CmdListDomain(domain, byIp);
    }

    // If token is an IP → resolve name, else assume fqdn → resolve IP
    if (s.matches("^(25[0-5]|2[0-4]\\d|[01]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[01]?\\d?\\d)){3}$")) {
      return new CmdResolveName(s);
    } else if (s.contains(".")) {
      return new CmdResolveIp(s);
    }

    throw new IllegalArgumentException("Commande inconnue: " + s);
  }

  /** Print output (no-op for empty string). */
  public void affiche(String result) {
    if (result == null || result.isEmpty()) return;
    System.out.println(result);
  }
}

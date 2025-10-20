package org.uvsq.dns;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class DnsApp {
  private final Dns dns;
  private final DnsTUI tui;

  public DnsApp() throws IOException {
    Properties p = Configs.load();
    this.dns = new Dns(p);
    this.dns.load();
    this.tui = new DnsTUI();
  }

  public void run() {
    try (Scanner sc = new Scanner(System.in)) {
      while (true) {
        System.out.print("> ");
        String line = sc.nextLine();
        Commande c;
        try {
          c = tui.nextCommande(line);
          if (c == null) continue;
          String out = c.execute(dns);
          if ("quit".equals(out)) break;
          tui.affiche(out);
        } catch (Exception e) {
          System.out.println("ERREUR : " + e.getMessage());
        }
      }
    }
  }

  public static void main(String[] args) throws IOException {
    new DnsApp().run();
  }
}

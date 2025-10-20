package org.uvsq.dns;

public class CmdQuit implements Commande {
  @Override public String execute(Dns dns) { return "quit"; }
}

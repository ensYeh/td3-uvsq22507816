package org.uvsq.dns;

public interface Commande {
  String execute(Dns dns) throws Exception;
}

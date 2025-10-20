package org.uvsq.dns;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CmdListDomain implements Commande {
  private final String domaine;
  private final boolean sortByIp;
  public CmdListDomain(String domaine, boolean sortByIp) {
    this.domaine = domaine;
    this.sortByIp = sortByIp;
  }
  @Override public String execute(Dns dns) {
    List<DnsItem> items = dns.getItems(domaine);
    Comparator<DnsItem> cmp = sortByIp
        ? Comparator.comparing(i -> i.ip().value())
        : Comparator.comparing(i -> i.nom().machine()); // sort by machine name
    return items.stream().sorted(cmp)
        .map(i -> i.ip().value() + " " + i.nom().fqdn())
        .collect(Collectors.joining(System.lineSeparator()));
  }
}

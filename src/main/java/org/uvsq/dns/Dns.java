package org.uvsq.dns;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/** DNS database service: load/save and queries. */
public class Dns {
  private final Path dbPath;
  private final Map<String, DnsItem> byFqdn = new HashMap<>();
  private final Map<String, DnsItem> byIp = new HashMap<>();

  public Dns(Properties props) {
    Objects.requireNonNull(props);
    String file = props.getProperty("db.file");
    if (file == null) throw new IllegalStateException("db.file manquant dans config.properties");
    this.dbPath = Paths.get(file);
  }

  /** Load DB from file (called by constructor or externally). */
  public void load() throws IOException {
    byFqdn.clear(); byIp.clear();
    List<String> lines = Files.readAllLines(dbPath, StandardCharsets.UTF_8);
    for (String line : lines) {
      String trimmed = line.trim();
      if (trimmed.isEmpty() || trimmed.startsWith("#")) continue;
      String[] parts = trimmed.split("\\s+");
      if (parts.length != 2) continue; // skip malformed
      NomMachine nom = new NomMachine(parts[0]);
      AdresseIP ip = new AdresseIP(parts[1]);
      DnsItem item = new DnsItem(nom, ip);
      byFqdn.put(nom.fqdn(), item);
      byIp.put(ip.value(), item);
    }
  }

  /** Save DB back to file (alphabetical by fqdn). */
  public void save() throws IOException {
    List<String> lines = byFqdn.values().stream()
        .sorted(Comparator.comparing(i -> i.nom().fqdn()))
        .map(i -> i.nom().fqdn() + " " + i.ip().value())
        .collect(Collectors.toList());
    Files.createDirectories(dbPath.getParent());
    Files.write(dbPath, lines, StandardCharsets.UTF_8,
        StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
  }

  public DnsItem getItem(AdresseIP ip) {
    return byIp.get(ip.value());
  }

  public DnsItem getItem(NomMachine nom) {
    return byFqdn.get(nom.fqdn());
  }

  /** Items for a domain (fqdn domain part), unsorted. */
  public List<DnsItem> getItems(String domaine) {
    return byFqdn.values().stream()
        .filter(i -> i.nom().domaine().equals(domaine))
        .collect(Collectors.toList());
  }

  /** Add new entry; error if fqdn or ip already exist. */
  public void addItem(AdresseIP ip, NomMachine nom) {
    if (byFqdn.containsKey(nom.fqdn())) throw new IllegalArgumentException("Le nom existe déjà !");
    if (byIp.containsKey(ip.value())) throw new IllegalArgumentException("L'adresse IP existe déjà !");
    DnsItem item = new DnsItem(nom, ip);
    byFqdn.put(nom.fqdn(), item);
    byIp.put(ip.value(), item);
  }
}

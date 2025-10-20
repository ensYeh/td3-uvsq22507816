package org.uvsq.dns;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Properties;

public class DnsTest {

  @Test
  public void loadAndQuery() throws Exception {
    Properties p = Configs.load();
    Dns dns = new Dns(p);
    dns.load();
    // by name
    DnsItem w = dns.getItem(new NomMachine("www.uvsq.fr"));
    assertNotNull(w);
    assertEquals("193.51.31.90", w.ip().value());
    // by ip
    DnsItem back = dns.getItem(new AdresseIP("193.51.31.90"));
    assertNotNull(back);
    assertEquals("www.uvsq.fr", back.nom().fqdn());
    // by domain
    List<DnsItem> li = dns.getItems("uvsq.fr");
    assertTrue(li.size() >= 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addDuplicateName() throws Exception {
    Dns dns = new Dns(Configs.load());
    dns.load();
    dns.addItem(new AdresseIP("193.51.25.90"), new NomMachine("www.uvsq.fr"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addDuplicateIp() throws Exception {
    Dns dns = new Dns(Configs.load());
    dns.load();
    dns.addItem(new AdresseIP("193.51.31.90"), new NomMachine("new.uvsq.fr"));
  }
}

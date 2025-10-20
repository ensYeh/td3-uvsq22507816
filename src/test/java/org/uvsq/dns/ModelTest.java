package org.uvsq.dns;

import org.junit.Test;
import static org.junit.Assert.*;

public class ModelTest {
  @Test public void adresseIpValide() {
    AdresseIP ip = new AdresseIP("193.51.31.90");
    assertEquals("193.51.31.90", ip.value());
  }

  @Test(expected = IllegalArgumentException.class)
  public void adresseIpInvalide() {
    new AdresseIP("999.1.1.1");
  }

  @Test public void nomMachineOK() {
    NomMachine n = new NomMachine("www.uvsq.fr");
    assertEquals("www", n.machine());
    assertEquals("uvsq.fr", n.domaine());
  }
}

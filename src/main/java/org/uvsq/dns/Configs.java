package org.uvsq.dns;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Configs {
  private Configs() {}

  public static Properties load() {
    try (InputStream in = Configs.class.getResourceAsStream("/config.properties")) {
      Properties p = new Properties();
      if (in == null) throw new IllegalStateException("config.properties introuvable");
      p.load(in);
      return p;
    } catch (IOException e) {
      throw new RuntimeException("Erreur lecture config.properties", e);
    }
  }
}

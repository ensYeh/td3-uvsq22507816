package org.uvsq.dns;

import java.util.Objects;
import java.util.regex.Pattern;

/** Represents an IPv4 address like 193.51.31.90. */
public final class AdresseIP implements Comparable<AdresseIP> {
  private static final Pattern IPV4 =
      Pattern.compile("^(25[0-5]|2[0-4]\\d|[01]?\\d?\\d)"
          + "(\\.(25[0-5]|2[0-4]\\d|[01]?\\d?\\d)){3}$");

  private final String value;

  public AdresseIP(String value) {
    Objects.requireNonNull(value, "IP null");
    if (!IPV4.matcher(value).matches()) {
      throw new IllegalArgumentException("Adresse IP invalide: " + value);
    }
    this.value = value;
  }

  public String value() { return value; }

  @Override public String toString() { return value; }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AdresseIP)) return false;
    AdresseIP that = (AdresseIP) o;
    return value.equals(that.value);
  }

  @Override public int hashCode() { return Objects.hash(value); }

  @Override public int compareTo(AdresseIP o) { return this.value.compareTo(o.value); }
}

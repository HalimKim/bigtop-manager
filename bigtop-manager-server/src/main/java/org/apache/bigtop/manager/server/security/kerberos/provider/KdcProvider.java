package org.apache.bigtop.manager.server.security.kerberos.provider;

import org.apache.bigtop.manager.server.security.kerberos.dto.KdcOptions;

public interface KdcProvider {
    void testConnection();
    void ensurePrincipal(String principal, KdcOptions opts); // idempotent
    byte[] issueKeytab(String principal, KdcOptions opts);   // returns keytab bytes
    void disableOrDelete(String principal, boolean delete);
}
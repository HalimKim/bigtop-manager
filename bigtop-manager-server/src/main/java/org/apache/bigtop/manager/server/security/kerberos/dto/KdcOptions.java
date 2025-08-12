package org.apache.bigtop.manager.server.security.kerberos.dto;

/**
 * KDC connection options containing admin credentials and configuration.
 * Used by KDC providers to authenticate and connect to the Kerberos KDC.
 */
public record KdcOptions(
    String admin,
    String password,
    String realm,
    String kdcHost,
    Integer kdcPort
) {
    
    /**
     * Creates KdcOptions with only admin credentials.
     */
    public static KdcOptions of(String admin, String password) {
        return new KdcOptions(admin, password, null, null, null);
    }
    
    /**
     * Creates KdcOptions with admin credentials and realm.
     */
    public static KdcOptions of(String admin, String password, String realm) {
        return new KdcOptions(admin, password, realm, null, null);
    }
    
    /**
     * Creates KdcOptions with all connection details.
     */
    public static KdcOptions of(String admin, String password, String realm, String kdcHost, Integer kdcPort) {
        return new KdcOptions(admin, password, realm, kdcHost, kdcPort);
    }
    
    /**
     * Returns the KDC connection string if host and port are provided.
     */
    public String getKdcUrl() {
        if (kdcHost != null && kdcPort != null) {
            return kdcHost + ":" + kdcPort;
        }
        return kdcHost;
    }
}

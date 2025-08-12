package org.apache.bigtop.manager.server.security.kerberos.dto;

import java.util.Map;
import java.util.Objects;

/**
 * Configuration DTO for Kerberos settings and parameters.
 */
public class KerberosConfig {
    
    private String realm;
    private String kdcHost;
    private Integer kdcPort;
    private String adminServer;
    private Integer adminPort;
    private String defaultDomain;
    private boolean caseSensitive;
    private Map<String, String> domainRealm;
    private Map<String, Object> libdefaults;
    private Map<String, Object> realms;
    private String krb5ConfPath;
    private String keytabDir;
    
    public KerberosConfig() {}
    
    public KerberosConfig(String realm, String kdcHost) {
        this.realm = realm;
        this.kdcHost = kdcHost;
        this.kdcPort = 88; // Default KDC port
        this.adminPort = 749; // Default admin port
        this.caseSensitive = true; // Default case sensitivity
    }
    
    // Getters and Setters
    public String getRealm() {
        return realm;
    }
    
    public void setRealm(String realm) {
        this.realm = realm;
    }
    
    public String getKdcHost() {
        return kdcHost;
    }
    
    public void setKdcHost(String kdcHost) {
        this.kdcHost = kdcHost;
    }
    
    public Integer getKdcPort() {
        return kdcPort;
    }
    
    public void setKdcPort(Integer kdcPort) {
        this.kdcPort = kdcPort;
    }
    
    public String getAdminServer() {
        return adminServer;
    }
    
    public void setAdminServer(String adminServer) {
        this.adminServer = adminServer;
    }
    
    public Integer getAdminPort() {
        return adminPort;
    }
    
    public void setAdminPort(Integer adminPort) {
        this.adminPort = adminPort;
    }
    
    public String getDefaultDomain() {
        return defaultDomain;
    }
    
    public void setDefaultDomain(String defaultDomain) {
        this.defaultDomain = defaultDomain;
    }
    
    public boolean isCaseSensitive() {
        return caseSensitive;
    }
    
    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }
    
    public Map<String, String> getDomainRealm() {
        return domainRealm;
    }
    
    public void setDomainRealm(Map<String, String> domainRealm) {
        this.domainRealm = domainRealm;
    }
    
    public Map<String, Object> getLibdefaults() {
        return libdefaults;
    }
    
    public void setLibdefaults(Map<String, Object> libdefaults) {
        this.libdefaults = libdefaults;
    }
    
    public Map<String, Object> getRealms() {
        return realms;
    }
    
    public void setRealms(Map<String, Object> realms) {
        this.realms = realms;
    }
    
    public String getKrb5ConfPath() {
        return krb5ConfPath;
    }
    
    public void setKrb5ConfPath(String krb5ConfPath) {
        this.krb5ConfPath = krb5ConfPath;
    }
    
    public String getKeytabDir() {
        return keytabDir;
    }
    
    public void setKeytabDir(String keytabDir) {
        this.keytabDir = keytabDir;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KerberosConfig that = (KerberosConfig) o;
        return caseSensitive == that.caseSensitive &&
               Objects.equals(realm, that.realm) &&
               Objects.equals(kdcHost, that.kdcHost) &&
               Objects.equals(kdcPort, that.kdcPort) &&
               Objects.equals(adminServer, that.adminServer) &&
               Objects.equals(adminPort, that.adminPort) &&
               Objects.equals(defaultDomain, that.defaultDomain) &&
               Objects.equals(domainRealm, that.domainRealm) &&
               Objects.equals(libdefaults, that.libdefaults) &&
               Objects.equals(realms, that.realms) &&
               Objects.equals(krb5ConfPath, that.krb5ConfPath) &&
               Objects.equals(keytabDir, that.keytabDir);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(realm, kdcHost, kdcPort, adminServer, adminPort, defaultDomain, 
                           caseSensitive, domainRealm, libdefaults, realms, krb5ConfPath, keytabDir);
    }
    
    @Override
    public String toString() {
        return "KerberosConfig{" +
               "realm='" + realm + '\'' +
               ", kdcHost='" + kdcHost + '\'' +
               ", kdcPort=" + kdcPort +
               ", adminServer='" + adminServer + '\'' +
               ", adminPort=" + adminPort +
               ", defaultDomain='" + defaultDomain + '\'' +
               ", caseSensitive=" + caseSensitive +
               ", krb5ConfPath='" + krb5ConfPath + '\'' +
               ", keytabDir='" + keytabDir + '\'' +
               '}';
    }
}

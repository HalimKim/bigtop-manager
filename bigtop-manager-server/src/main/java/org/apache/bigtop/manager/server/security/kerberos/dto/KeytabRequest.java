package org.apache.bigtop.manager.server.security.kerberos.dto;

import java.util.List;
import java.util.Objects;

/**
 * Request DTO for keytab generation and management operations.
 */
public class KeytabRequest {
    
    private String principal;
    private List<String> principals;
    private String keytabPath;
    private Integer kvno; // Key Version Number
    private boolean append;
    private String encryptionType;
    
    public KeytabRequest() {}
    
    public KeytabRequest(String principal) {
        this.principal = principal;
    }
    
    public KeytabRequest(List<String> principals) {
        this.principals = principals;
    }
    
    // Getters and Setters
    public String getPrincipal() {
        return principal;
    }
    
    public void setPrincipal(String principal) {
        this.principal = principal;
    }
    
    public List<String> getPrincipals() {
        return principals;
    }
    
    public void setPrincipals(List<String> principals) {
        this.principals = principals;
    }
    
    public String getKeytabPath() {
        return keytabPath;
    }
    
    public void setKeytabPath(String keytabPath) {
        this.keytabPath = keytabPath;
    }
    
    public Integer getKvno() {
        return kvno;
    }
    
    public void setKvno(Integer kvno) {
        this.kvno = kvno;
    }
    
    public boolean isAppend() {
        return append;
    }
    
    public void setAppend(boolean append) {
        this.append = append;
    }
    
    public String getEncryptionType() {
        return encryptionType;
    }
    
    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeytabRequest that = (KeytabRequest) o;
        return append == that.append &&
               Objects.equals(principal, that.principal) &&
               Objects.equals(principals, that.principals) &&
               Objects.equals(keytabPath, that.keytabPath) &&
               Objects.equals(kvno, that.kvno) &&
               Objects.equals(encryptionType, that.encryptionType);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(principal, principals, keytabPath, kvno, append, encryptionType);
    }
    
    @Override
    public String toString() {
        return "KeytabRequest{" +
               "principal='" + principal + '\'' +
               ", principals=" + principals +
               ", keytabPath='" + keytabPath + '\'' +
               ", kvno=" + kvno +
               ", append=" + append +
               ", encryptionType='" + encryptionType + '\'' +
               '}';
    }
}

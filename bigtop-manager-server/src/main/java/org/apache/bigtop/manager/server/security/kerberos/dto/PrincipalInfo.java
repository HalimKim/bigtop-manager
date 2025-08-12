package org.apache.bigtop.manager.server.security.kerberos.dto;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * Information DTO representing a Kerberos principal and its properties.
 */
public class PrincipalInfo {
    
    private String principal;
    private String realm;
    private boolean enabled;
    private LocalDateTime lastModified;
    private LocalDateTime passwordExpiration;
    private LocalDateTime lastPasswordChange;
    private Integer kvno; // Key Version Number
    private Map<String, String> attributes;
    private String policy;
    private Long maxLife;
    private Long maxRenewableLife;
    private Integer failedAttempts;
    private LocalDateTime lockoutEnd;
    
    public PrincipalInfo() {}
    
    public PrincipalInfo(String principal, String realm) {
        this.principal = principal;
        this.realm = realm;
        this.enabled = true;
    }
    
    // Getters and Setters
    public String getPrincipal() {
        return principal;
    }
    
    public void setPrincipal(String principal) {
        this.principal = principal;
    }
    
    public String getRealm() {
        return realm;
    }
    
    public void setRealm(String realm) {
        this.realm = realm;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public LocalDateTime getLastModified() {
        return lastModified;
    }
    
    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }
    
    public LocalDateTime getPasswordExpiration() {
        return passwordExpiration;
    }
    
    public void setPasswordExpiration(LocalDateTime passwordExpiration) {
        this.passwordExpiration = passwordExpiration;
    }
    
    public LocalDateTime getLastPasswordChange() {
        return lastPasswordChange;
    }
    
    public void setLastPasswordChange(LocalDateTime lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }
    
    public Integer getKvno() {
        return kvno;
    }
    
    public void setKvno(Integer kvno) {
        this.kvno = kvno;
    }
    
    public Map<String, String> getAttributes() {
        return attributes;
    }
    
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
    
    public String getPolicy() {
        return policy;
    }
    
    public void setPolicy(String policy) {
        this.policy = policy;
    }
    
    public Long getMaxLife() {
        return maxLife;
    }
    
    public void setMaxLife(Long maxLife) {
        this.maxLife = maxLife;
    }
    
    public Long getMaxRenewableLife() {
        return maxRenewableLife;
    }
    
    public void setMaxRenewableLife(Long maxRenewableLife) {
        this.maxRenewableLife = maxRenewableLife;
    }
    
    public Integer getFailedAttempts() {
        return failedAttempts;
    }
    
    public void setFailedAttempts(Integer failedAttempts) {
        this.failedAttempts = failedAttempts;
    }
    
    public LocalDateTime getLockoutEnd() {
        return lockoutEnd;
    }
    
    public void setLockoutEnd(LocalDateTime lockoutEnd) {
        this.lockoutEnd = lockoutEnd;
    }
    
    /**
     * Returns the full principal name (principal@realm).
     */
    public String getFullPrincipal() {
        if (realm != null && !principal.contains("@")) {
            return principal + "@" + realm;
        }
        return principal;
    }
    
    /**
     * Checks if the principal is currently locked out.
     */
    public boolean isLockedOut() {
        return lockoutEnd != null && lockoutEnd.isAfter(LocalDateTime.now());
    }
    
    /**
     * Checks if the password has expired.
     */
    public boolean isPasswordExpired() {
        return passwordExpiration != null && passwordExpiration.isBefore(LocalDateTime.now());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrincipalInfo that = (PrincipalInfo) o;
        return enabled == that.enabled &&
               Objects.equals(principal, that.principal) &&
               Objects.equals(realm, that.realm) &&
               Objects.equals(lastModified, that.lastModified) &&
               Objects.equals(passwordExpiration, that.passwordExpiration) &&
               Objects.equals(lastPasswordChange, that.lastPasswordChange) &&
               Objects.equals(kvno, that.kvno) &&
               Objects.equals(attributes, that.attributes) &&
               Objects.equals(policy, that.policy) &&
               Objects.equals(maxLife, that.maxLife) &&
               Objects.equals(maxRenewableLife, that.maxRenewableLife) &&
               Objects.equals(failedAttempts, that.failedAttempts) &&
               Objects.equals(lockoutEnd, that.lockoutEnd);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(principal, realm, enabled, lastModified, passwordExpiration, 
                           lastPasswordChange, kvno, attributes, policy, maxLife, 
                           maxRenewableLife, failedAttempts, lockoutEnd);
    }
    
    @Override
    public String toString() {
        return "PrincipalInfo{" +
               "principal='" + principal + '\'' +
               ", realm='" + realm + '\'' +
               ", enabled=" + enabled +
               ", lastModified=" + lastModified +
               ", passwordExpiration=" + passwordExpiration +
               ", kvno=" + kvno +
               ", policy='" + policy + '\'' +
               ", isLockedOut=" + isLockedOut() +
               ", isPasswordExpired=" + isPasswordExpired() +
               '}';
    }
}

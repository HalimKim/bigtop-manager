package org.apache.bigtop.manager.server.security.kerberos.dto;

import java.util.Map;
import java.util.Objects;

/**
 * Request DTO for principal operations like creation, modification, or deletion.
 */
public class PrincipalRequest {
    
    private String principal;
    private String password;
    private boolean randomKey;
    private Map<String, String> attributes;
    private String policy;
    private Long maxLife;
    private Long maxRenewableLife;
    
    public PrincipalRequest() {}
    
    public PrincipalRequest(String principal) {
        this.principal = principal;
        this.randomKey = true; // Default to random key for service principals
    }
    
    // Getters and Setters
    public String getPrincipal() {
        return principal;
    }
    
    public void setPrincipal(String principal) {
        this.principal = principal;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isRandomKey() {
        return randomKey;
    }
    
    public void setRandomKey(boolean randomKey) {
        this.randomKey = randomKey;
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrincipalRequest that = (PrincipalRequest) o;
        return randomKey == that.randomKey &&
               Objects.equals(principal, that.principal) &&
               Objects.equals(password, that.password) &&
               Objects.equals(attributes, that.attributes) &&
               Objects.equals(policy, that.policy) &&
               Objects.equals(maxLife, that.maxLife) &&
               Objects.equals(maxRenewableLife, that.maxRenewableLife);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(principal, password, randomKey, attributes, policy, maxLife, maxRenewableLife);
    }
    
    @Override
    public String toString() {
        return "PrincipalRequest{" +
               "principal='" + principal + '\'' +
               ", randomKey=" + randomKey +
               ", policy='" + policy + '\'' +
               ", maxLife=" + maxLife +
               ", maxRenewableLife=" + maxRenewableLife +
               '}';
    }
}

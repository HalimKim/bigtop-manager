package org.apache.bigtop.manager.server.security.kerberos.service;

import java.util.List;
import org.apache.bigtop.manager.server.security.kerberos.dto.KdcOptions;
import org.apache.bigtop.manager.server.security.kerberos.dto.KerberosConfig;
import org.apache.bigtop.manager.server.security.kerberos.dto.KeytabInfo;
import org.apache.bigtop.manager.server.security.kerberos.dto.KeytabRequest;
import org.apache.bigtop.manager.server.security.kerberos.dto.PrincipalInfo;
import org.apache.bigtop.manager.server.security.kerberos.dto.PrincipalRequest;

/**
 * Service interface for Kerberos operations and management.
 * Provides high-level business logic for principal management, keytab operations,
 * and KDC interactions across different provider implementations.
 */
public interface KerberosService {
    
    /**
     * Tests connectivity to the configured KDC.
     * 
     * @param config Kerberos configuration containing KDC details
     * @param options Admin credentials for KDC access
     * @return true if connection is successful, false otherwise
     */
    boolean testKdcConnection(KerberosConfig config, KdcOptions options);
    
    /**
     * Creates or ensures a principal exists in the KDC.
     * This operation is idempotent - if the principal already exists, it won't be recreated.
     * 
     * @param request Principal creation request containing details
     * @param options Admin credentials for KDC access
     * @return Information about the created/existing principal
     */
    PrincipalInfo createPrincipal(PrincipalRequest request, KdcOptions options);
    
    /**
     * Retrieves information about a specific principal.
     * 
     * @param principal Principal name to look up
     * @param options Admin credentials for KDC access
     * @return Principal information if found, null if not found
     */
    PrincipalInfo getPrincipalInfo(String principal, KdcOptions options);
    
    /**
     * Lists all principals in the KDC or filters by pattern.
     * 
     * @param pattern Optional pattern to filter principals (null for all)
     * @param options Admin credentials for KDC access
     * @return List of principals matching the pattern
     */
    List<PrincipalInfo> listPrincipals(String pattern, KdcOptions options);
    
    /**
     * Generates a keytab for one or more principals.
     * 
     * @param request Keytab generation request
     * @param options Admin credentials for KDC access
     * @return Keytab file contents as byte array
     */
    byte[] generateKeytab(KeytabRequest request, KdcOptions options);
    
    /**
     * Retrieves information about a keytab file.
     * 
     * @param keytabData Keytab file contents
     * @return Information about the keytab and its entries
     */
    KeytabInfo analyzeKeytab(byte[] keytabData);
    
    /**
     * Disables a principal by requiring password change.
     * 
     * @param principal Principal name to disable
     * @param options Admin credentials for KDC access
     * @return Updated principal information
     */
    PrincipalInfo disablePrincipal(String principal, KdcOptions options);
    
    /**
     * Permanently deletes a principal from the KDC.
     * 
     * @param principal Principal name to delete
     * @param options Admin credentials for KDC access
     * @return true if deletion was successful
     */
    boolean deletePrincipal(String principal, KdcOptions options);
    
    /**
     * Updates principal attributes or policy.
     * 
     * @param principal Principal name to update
     * @param request Update request with new attributes
     * @param options Admin credentials for KDC access
     * @return Updated principal information
     */
    PrincipalInfo updatePrincipal(String principal, PrincipalRequest request, KdcOptions options);
    
    /**
     * Changes the password for a principal.
     * 
     * @param principal Principal name
     * @param newPassword New password
     * @param options Admin credentials for KDC access
     * @return Updated principal information
     */
    PrincipalInfo changePassword(String principal, String newPassword, KdcOptions options);
    
    /**
     * Validates the current Kerberos configuration.
     * 
     * @param config Configuration to validate
     * @return List of validation errors (empty if valid)
     */
    List<String> validateConfiguration(KerberosConfig config);
    
    /**
     * Gets the current Kerberos configuration.
     * 
     * @return Current configuration
     */
    KerberosConfig getCurrentConfiguration();
    
    /**
     * Updates the Kerberos configuration.
     * 
     * @param config New configuration
     * @return true if update was successful
     */
    boolean updateConfiguration(KerberosConfig config);
}

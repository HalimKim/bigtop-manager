package org.apache.bigtop.manager.server.security.kerberos.provider.impl;

import org.apache.bigtop.manager.server.security.kerberos.dto.KdcOptions;
import org.apache.bigtop.manager.server.security.kerberos.provider.KdcProvider;
import org.springframework.stereotype.Component;

@Component
public class ActiveDirectoryProvider implements KdcProvider {

    @Override
    public void testConnection() {
        // TODO: Implement Active Directory connection test
        throw new UnsupportedOperationException("Active Directory provider not yet implemented");
    }

    @Override
    public void ensurePrincipal(String principal, KdcOptions opts) {
        // TODO: Implement Active Directory principal creation
        throw new UnsupportedOperationException("Active Directory provider not yet implemented");
    }

    @Override
    public byte[] issueKeytab(String principal, KdcOptions opts) {
        // TODO: Implement Active Directory keytab generation
        throw new UnsupportedOperationException("Active Directory provider not yet implemented");
    }

    @Override
    public void disableOrDelete(String principal, boolean delete) {
        // TODO: Implement Active Directory principal disable/delete
        throw new UnsupportedOperationException("Active Directory provider not yet implemented");
    }
}

package org.apache.bigtop.manager.server.security.kerberos.provider.impl;

import org.apache.bigtop.manager.server.security.kerberos.dto.KdcOptions;
import org.apache.bigtop.manager.server.security.kerberos.provider.KdcProvider;
import org.springframework.stereotype.Component;

@Component
public class FreeIPAProvider implements KdcProvider {

    @Override
    public void testConnection() {
        // TODO: Implement FreeIPA connection test
        throw new UnsupportedOperationException("FreeIPA provider not yet implemented");
    }

    @Override
    public void ensurePrincipal(String principal, KdcOptions opts) {
        // TODO: Implement FreeIPA principal creation
        throw new UnsupportedOperationException("FreeIPA provider not yet implemented");
    }

    @Override
    public byte[] issueKeytab(String principal, KdcOptions opts) {
        // TODO: Implement FreeIPA keytab generation
        throw new UnsupportedOperationException("FreeIPA provider not yet implemented");
    }

    @Override
    public void disableOrDelete(String principal, boolean delete) {
        // TODO: Implement FreeIPA principal disable/delete
        throw new UnsupportedOperationException("FreeIPA provider not yet implemented");
    }
}

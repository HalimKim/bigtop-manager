package org.apache.bigtop.manager.server.security.kerberos.provider.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.apache.bigtop.manager.common.shell.ShellExecutor;
import org.apache.bigtop.manager.server.security.kerberos.dto.KdcOptions;
import org.apache.bigtop.manager.server.security.kerberos.provider.KdcProvider;
import org.springframework.stereotype.Component;

@Component
public class MitKdcProvider implements KdcProvider {

    @Override public void ensurePrincipal(String p, KdcOptions o) throws RuntimeException {
        try {
            ShellExecutor.execCommand(List.of("kadmin", "-p", o.admin(), "-w", o.password(),
                    "-q", "addprinc -randkey " + p));
        } catch (IOException e) {
            throw new RuntimeException("Failed to add principal", e);
        }
    }
    @Override public byte[] issueKeytab(String p, KdcOptions o) throws RuntimeException {
        try {
            Path tmp = Files.createTempFile("bm-", ".keytab");
            ShellExecutor.execCommand(List.of("kadmin", "-p", o.admin(), "-w", o.password(),
                    "-q", "ktadd -k " + tmp + " " + p));
            byte[] b = Files.readAllBytes(tmp);
            Files.deleteIfExists(tmp);
            return b;
        } catch (IOException e) {
            throw new RuntimeException("Failed to create or read keytab file", e);
        }
    }
    @Override public void testConnection() throws RuntimeException {
        // Test connection by listing principals - this verifies KDC connectivity and authentication
        // Note: This method doesn't take KdcOptions, so it assumes default/configured credentials
        // In a real implementation, you'd need to pass admin credentials somehow
        try {
            ShellExecutor.execCommand(List.of("kadmin", "-q", "listprincs"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to test connection to kdc", e);
        }
    }
    @Override public void disableOrDelete(String p, boolean del) throws RuntimeException {
        // Note: This method doesn't take KdcOptions, so it assumes default/configured credentials
        // In a real implementation, you'd need to pass admin credentials somehow
        try {
            if (del) {
                // Delete the principal
                ShellExecutor.execCommand(List.of("kadmin", "-q", "delprinc -force " + p));
            } else {
                // Disable the principal by requiring password change
                ShellExecutor.execCommand(List.of("kadmin", "-q", "modprinc +needchange " + p));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to disable or delete principal", e);
        }

    }
}

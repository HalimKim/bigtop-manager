/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.bigtop.manager.server.security.kerberos.controller;

import org.apache.bigtop.manager.server.security.kerberos.dto.KdcOptions;
import org.apache.bigtop.manager.server.security.kerberos.dto.KerberosConfig;
import org.apache.bigtop.manager.server.security.kerberos.dto.KeytabInfo;
import org.apache.bigtop.manager.server.security.kerberos.dto.KeytabRequest;
import org.apache.bigtop.manager.server.security.kerberos.dto.PrincipalInfo;
import org.apache.bigtop.manager.server.security.kerberos.dto.PrincipalRequest;
import org.apache.bigtop.manager.server.security.kerberos.service.KerberosService;
import org.apache.bigtop.manager.server.utils.ResponseEntity;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "Cluster Kerberos Management Controller", description = "API endpoints for cluster-specific Kerberos operations and management")
@RestController
@RequestMapping("/clusters/{clusterId}/kerberos")
public class KerberosController {

    @Resource
    private KerberosService kerberosService;

    // ==================== Configuration Management ====================

    @Operation(
        summary = "Get current Kerberos configuration",
        description = "Retrieves the current Kerberos configuration settings for a specific cluster",
        responses = {
            @ApiResponse(responseCode = "200", description = "Configuration retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Cluster not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping("/config")
    public ResponseEntity<KerberosConfig> getConfiguration(
            @Parameter(description = "Cluster ID", required = true) @PathVariable Long clusterId) {
        log.info("Getting Kerberos configuration for cluster: {}", clusterId);
        return ResponseEntity.success(kerberosService.getCurrentConfiguration());
    }

    @Operation(
        summary = "Update Kerberos configuration",
        description = "Updates the Kerberos configuration with new settings for a specific cluster",
        responses = {
            @ApiResponse(responseCode = "200", description = "Configuration updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid configuration provided"),
            @ApiResponse(responseCode = "404", description = "Cluster not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @PostMapping("/config")
    public ResponseEntity<Boolean> updateConfiguration(
            @Parameter(description = "Cluster ID", required = true) @PathVariable Long clusterId,
            @RequestBody KerberosConfig config) {
        log.info("Updating Kerberos configuration for cluster: {}, realm: {}", clusterId, config.getRealm());
        return ResponseEntity.success(kerberosService.updateConfiguration(config));
    }

    @Operation(
        summary = "Validate Kerberos configuration",
        description = "Validates the provided Kerberos configuration and returns any validation errors for a specific cluster",
        responses = {
            @ApiResponse(responseCode = "200", description = "Validation completed"),
            @ApiResponse(responseCode = "400", description = "Invalid configuration format"),
            @ApiResponse(responseCode = "404", description = "Cluster not found")
        }
    )
    @PostMapping("/config/validate")
    public ResponseEntity<List<String>> validateConfiguration(
            @Parameter(description = "Cluster ID", required = true) @PathVariable Long clusterId,
            @RequestBody KerberosConfig config) {
        log.info("Validating Kerberos configuration for cluster: {}, realm: {}", clusterId, config.getRealm());
        return ResponseEntity.success(kerberosService.validateConfiguration(config));
    }

    @Operation(
        summary = "Test KDC connection",
        description = "Tests connectivity to the KDC using the provided configuration and admin credentials for a specific cluster",
        responses = {
            @ApiResponse(responseCode = "200", description = "Connection test completed"),
            @ApiResponse(responseCode = "400", description = "Invalid connection parameters"),
            @ApiResponse(responseCode = "404", description = "Cluster not found"),
            @ApiResponse(responseCode = "500", description = "Connection test failed")
        }
    )
    @PostMapping("/config/test-connection")
    public ResponseEntity<Boolean> testKdcConnection(
            @Parameter(description = "Cluster ID", required = true) @PathVariable Long clusterId,
            @RequestBody KerberosConfig config,
            @Parameter(description = "KDC admin credentials and connection options")
            @RequestParam Map<String, String> kdcOptions) {
        log.info("Testing KDC connection for cluster: {}, realm: {}", clusterId, config.getRealm());
        
        KdcOptions options = KdcOptions.of(
            kdcOptions.get("admin"),
            kdcOptions.get("password"),
            kdcOptions.get("realm"),
            kdcOptions.get("kdcHost"),
            kdcOptions.get("kdcPort") != null ? Integer.valueOf(kdcOptions.get("kdcPort")) : null
        );
        
        return ResponseEntity.success(kerberosService.testKdcConnection(config, options));
    }

    // ==================== Principal Management ====================

    @Operation(
        summary = "List principals",
        description = "Lists all principals in the KDC for a specific cluster, optionally filtered by pattern",
        responses = {
            @ApiResponse(responseCode = "200", description = "Principals listed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid filter pattern"),
            @ApiResponse(responseCode = "404", description = "Cluster not found"),
            @ApiResponse(responseCode = "500", description = "Failed to list principals")
        }
    )
    @GetMapping("/principals")
    public ResponseEntity<List<PrincipalInfo>> listPrincipals(
            @Parameter(description = "Cluster ID", required = true) @PathVariable Long clusterId,
            @Parameter(description = "Optional pattern to filter principals (null for all)")
            @RequestParam(required = false) String pattern,
            @Parameter(description = "KDC admin credentials", required = true)
            @RequestParam Map<String, String> kdcOptions) {
        log.info("Listing principals for cluster: {} with pattern: {}", clusterId, pattern);
        
        KdcOptions options = createKdcOptions(kdcOptions);
        return ResponseEntity.success(kerberosService.listPrincipals(pattern, options));
    }

    @Operation(
        summary = "Create principal",
        description = "Creates a new principal in the KDC for a specific cluster",
        responses = {
            @ApiResponse(responseCode = "200", description = "Principal created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid principal request"),
            @ApiResponse(responseCode = "404", description = "Cluster not found"),
            @ApiResponse(responseCode = "409", description = "Principal already exists"),
            @ApiResponse(responseCode = "500", description = "Failed to create principal")
        }
    )
    @PostMapping("/principals")
    public ResponseEntity<PrincipalInfo> createPrincipal(
            @Parameter(description = "Cluster ID", required = true) @PathVariable Long clusterId,
            @RequestBody PrincipalRequest request,
            @Parameter(description = "KDC admin credentials", required = true)
            @RequestParam Map<String, String> kdcOptions) {
        log.info("Creating principal: {} for cluster: {}", request.getPrincipal(), clusterId);
        
        KdcOptions options = createKdcOptions(kdcOptions);
        return ResponseEntity.success(kerberosService.createPrincipal(request, options));
    }

    @Operation(
        summary = "Get principal information",
        description = "Retrieves detailed information about a specific principal in a cluster",
        responses = {
            @ApiResponse(responseCode = "200", description = "Principal information retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Principal or cluster not found"),
            @ApiResponse(responseCode = "500", description = "Failed to get principal information")
        }
    )
    @GetMapping("/principals/{principalName}")
    public ResponseEntity<PrincipalInfo> getPrincipalInfo(
            @Parameter(description = "Cluster ID", required = true) @PathVariable Long clusterId,
            @Parameter(description = "Principal name to look up", required = true)
            @PathVariable String principalName,
            @Parameter(description = "KDC admin credentials", required = true)
            @RequestParam Map<String, String> kdcOptions) {
        log.info("Getting principal info for: {} in cluster: {}", principalName, clusterId);
        
        KdcOptions options = createKdcOptions(kdcOptions);
        return ResponseEntity.success(kerberosService.getPrincipalInfo(principalName, options));
    }

    @Operation(
        summary = "Update principal",
        description = "Updates attributes or policy of an existing principal in a specific cluster",
        responses = {
            @ApiResponse(responseCode = "200", description = "Principal updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid update request"),
            @ApiResponse(responseCode = "404", description = "Principal or cluster not found"),
            @ApiResponse(responseCode = "500", description = "Failed to update principal")
        }
    )
    @PutMapping("/principals/{principalName}")
    public ResponseEntity<PrincipalInfo> updatePrincipal(
            @Parameter(description = "Cluster ID", required = true) @PathVariable Long clusterId,
            @Parameter(description = "Principal name to update", required = true)
            @PathVariable String principalName,
            @RequestBody PrincipalRequest request,
            @Parameter(description = "KDC admin credentials", required = true)
            @RequestParam Map<String, String> kdcOptions) {
        log.info("Updating principal: {} in cluster: {}", principalName, clusterId);
        
        KdcOptions options = createKdcOptions(kdcOptions);
        return ResponseEntity.success(kerberosService.updatePrincipal(principalName, request, options));
    }

    @Operation(
        summary = "Delete principal",
        description = "Permanently deletes a principal from the KDC in a specific cluster",
        responses = {
            @ApiResponse(responseCode = "200", description = "Principal deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Principal or cluster not found"),
            @ApiResponse(responseCode = "500", description = "Failed to delete principal")
        }
    )
    @DeleteMapping("/principals/{principalName}")
    public ResponseEntity<Boolean> deletePrincipal(
            @Parameter(description = "Cluster ID", required = true) @PathVariable Long clusterId,
            @Parameter(description = "Principal name to delete", required = true)
            @PathVariable String principalName,
            @Parameter(description = "KDC admin credentials", required = true)
            @RequestParam Map<String, String> kdcOptions) {
        log.info("Deleting principal: {} in cluster: {}", principalName, clusterId);
        
        KdcOptions options = createKdcOptions(kdcOptions);
        return ResponseEntity.success(kerberosService.deletePrincipal(principalName, options));
    }

    @Operation(
        summary = "Disable principal",
        description = "Disables a principal by requiring password change in a specific cluster",
        responses = {
            @ApiResponse(responseCode = "200", description = "Principal disabled successfully"),
            @ApiResponse(responseCode = "404", description = "Principal or cluster not found"),
            @ApiResponse(responseCode = "500", description = "Failed to disable principal")
        }
    )
    @PostMapping("/principals/{principalName}/disable")
    public ResponseEntity<PrincipalInfo> disablePrincipal(
            @Parameter(description = "Cluster ID", required = true) @PathVariable Long clusterId,
            @Parameter(description = "Principal name to disable", required = true)
            @PathVariable String principalName,
            @Parameter(description = "KDC admin credentials", required = true)
            @RequestParam Map<String, String> kdcOptions) {
        log.info("Disabling principal: {} in cluster: {}", principalName, clusterId);
        
        KdcOptions options = createKdcOptions(kdcOptions);
        return ResponseEntity.success(kerberosService.disablePrincipal(principalName, options));
    }

    @Operation(
        summary = "Change principal password",
        description = "Changes the password for a principal in a specific cluster",
        responses = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid password"),
            @ApiResponse(responseCode = "404", description = "Principal or cluster not found"),
            @ApiResponse(responseCode = "500", description = "Failed to change password")
        }
    )
    @PostMapping("/principals/{principalName}/password")
    public ResponseEntity<PrincipalInfo> changePassword(
            @Parameter(description = "Cluster ID", required = true) @PathVariable Long clusterId,
            @Parameter(description = "Principal name", required = true)
            @PathVariable String principalName,
            @Parameter(description = "New password", required = true)
            @RequestParam String newPassword,
            @Parameter(description = "KDC admin credentials", required = true)
            @RequestParam Map<String, String> kdcOptions) {
        log.info("Changing password for principal: {} in cluster: {}", principalName, clusterId);
        
        KdcOptions options = createKdcOptions(kdcOptions);
        return ResponseEntity.success(kerberosService.changePassword(principalName, newPassword, options));
    }

    // ==================== Keytab Operations ====================

    @Operation(
        summary = "Generate keytab",
        description = "Generates a keytab file for one or more principals in a specific cluster",
        responses = {
            @ApiResponse(responseCode = "200", description = "Keytab generated successfully", 
                        content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)),
            @ApiResponse(responseCode = "400", description = "Invalid keytab request"),
            @ApiResponse(responseCode = "404", description = "Cluster not found"),
            @ApiResponse(responseCode = "500", description = "Failed to generate keytab")
        }
    )
    @PostMapping(value = "/keytabs/generate", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generateKeytab(
            @Parameter(description = "Cluster ID", required = true) @PathVariable Long clusterId,
            @RequestBody KeytabRequest request,
            @Parameter(description = "KDC admin credentials", required = true)
            @RequestParam Map<String, String> kdcOptions) {
        log.info("Generating keytab for principals: {} in cluster: {}", request.getPrincipals(), clusterId);
        
        KdcOptions options = createKdcOptions(kdcOptions);
        byte[] keytabData = kerberosService.generateKeytab(request, options);
        return ResponseEntity.success(keytabData);
    }

    @Operation(
        summary = "Analyze keytab",
        description = "Analyzes a keytab file and returns information about its contents for a specific cluster",
        responses = {
            @ApiResponse(responseCode = "200", description = "Keytab analyzed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid keytab file"),
            @ApiResponse(responseCode = "404", description = "Cluster not found"),
            @ApiResponse(responseCode = "500", description = "Failed to analyze keytab")
        }
    )
    @PostMapping(value = "/keytabs/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<KeytabInfo> analyzeKeytab(
            @Parameter(description = "Cluster ID", required = true) @PathVariable Long clusterId,
            @Parameter(description = "Keytab file to analyze", required = true)
            @RequestParam("file") MultipartFile keytabFile) {
        log.info("Analyzing keytab file: {} for cluster: {}", keytabFile.getOriginalFilename(), clusterId);
        
        try {
            byte[] keytabData = keytabFile.getBytes();
            KeytabInfo info = kerberosService.analyzeKeytab(keytabData);
            return ResponseEntity.success(info);
        } catch (Exception e) {
            log.error("Failed to analyze keytab file", e);
            throw new RuntimeException("Failed to analyze keytab file", e);
        }
    }

    // ==================== Helper Methods ====================

    /**
     * Creates KdcOptions from request parameters.
     * 
     * @param kdcOptionsMap Map containing KDC connection parameters
     * @return KdcOptions object
     */
    private KdcOptions createKdcOptions(Map<String, String> kdcOptionsMap) {
        return KdcOptions.of(
            kdcOptionsMap.get("admin"),
            kdcOptionsMap.get("password"),
            kdcOptionsMap.get("realm"),
            kdcOptionsMap.get("kdcHost"),
            kdcOptionsMap.get("kdcPort") != null ? Integer.valueOf(kdcOptionsMap.get("kdcPort")) : null
        );
    }
}

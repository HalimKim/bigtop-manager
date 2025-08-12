package org.apache.bigtop.manager.server.security.kerberos.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Information DTO representing a keytab file and its contents.
 */
public class KeytabInfo {
    
    private String filePath;
    private long fileSize;
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;
    private List<KeytabEntry> entries;
    private String checksum;
    
    public KeytabInfo() {}
    
    public KeytabInfo(String filePath) {
        this.filePath = filePath;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public long getFileSize() {
        return fileSize;
    }
    
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getLastModified() {
        return lastModified;
    }
    
    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }
    
    public List<KeytabEntry> getEntries() {
        return entries;
    }
    
    public void setEntries(List<KeytabEntry> entries) {
        this.entries = entries;
    }
    
    public String getChecksum() {
        return checksum;
    }
    
    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }
    
    /**
     * Gets the number of entries in the keytab.
     */
    public int getEntryCount() {
        return entries != null ? entries.size() : 0;
    }
    
    /**
     * Gets the list of unique principals in the keytab.
     */
    public List<String> getPrincipals() {
        if (entries == null) {
            return List.of();
        }
        return entries.stream()
                .map(KeytabEntry::getPrincipal)
                .distinct()
                .toList();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeytabInfo that = (KeytabInfo) o;
        return fileSize == that.fileSize &&
               Objects.equals(filePath, that.filePath) &&
               Objects.equals(createdAt, that.createdAt) &&
               Objects.equals(lastModified, that.lastModified) &&
               Objects.equals(entries, that.entries) &&
               Objects.equals(checksum, that.checksum);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(filePath, fileSize, createdAt, lastModified, entries, checksum);
    }
    
    @Override
    public String toString() {
        return "KeytabInfo{" +
               "filePath='" + filePath + '\'' +
               ", fileSize=" + fileSize +
               ", createdAt=" + createdAt +
               ", lastModified=" + lastModified +
               ", entryCount=" + getEntryCount() +
               ", principals=" + getPrincipals() +
               '}';
    }
    
    /**
     * Represents a single entry in a keytab file.
     */
    public static class KeytabEntry {
        private String principal;
        private Integer kvno;
        private String encryptionType;
        private LocalDateTime timestamp;
        
        public KeytabEntry() {}
        
        public KeytabEntry(String principal, Integer kvno, String encryptionType) {
            this.principal = principal;
            this.kvno = kvno;
            this.encryptionType = encryptionType;
            this.timestamp = LocalDateTime.now();
        }
        
        // Getters and Setters
        public String getPrincipal() {
            return principal;
        }
        
        public void setPrincipal(String principal) {
            this.principal = principal;
        }
        
        public Integer getKvno() {
            return kvno;
        }
        
        public void setKvno(Integer kvno) {
            this.kvno = kvno;
        }
        
        public String getEncryptionType() {
            return encryptionType;
        }
        
        public void setEncryptionType(String encryptionType) {
            this.encryptionType = encryptionType;
        }
        
        public LocalDateTime getTimestamp() {
            return timestamp;
        }
        
        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            KeytabEntry that = (KeytabEntry) o;
            return Objects.equals(principal, that.principal) &&
                   Objects.equals(kvno, that.kvno) &&
                   Objects.equals(encryptionType, that.encryptionType) &&
                   Objects.equals(timestamp, that.timestamp);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(principal, kvno, encryptionType, timestamp);
        }
        
        @Override
        public String toString() {
            return "KeytabEntry{" +
                   "principal='" + principal + '\'' +
                   ", kvno=" + kvno +
                   ", encryptionType='" + encryptionType + '\'' +
                   ", timestamp=" + timestamp +
                   '}';
        }
    }
}

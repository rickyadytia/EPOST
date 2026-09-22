package id.epost.pos.tenant;

import java.util.Objects;

/**
 * Runtime tenant boundary. Every repository/service must receive a TenantContext
 * instead of reading a global tenant id.
 */
public final class TenantContext {
    private final String tenantId;
    private final String tenantName;
    private final String outletId;

    public TenantContext(String tenantId, String tenantName, String outletId) {
        this.tenantId = Objects.requireNonNull(tenantId);
        this.tenantName = Objects.requireNonNull(tenantName);
        this.outletId = Objects.requireNonNull(outletId);
    }

    public String tenantId() { return tenantId; }
    public String tenantName() { return tenantName; }
    public String outletId() { return outletId; }
}

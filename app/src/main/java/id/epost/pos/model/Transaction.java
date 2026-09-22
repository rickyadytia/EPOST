package id.epost.pos.model;

public final class Transaction {
    public final String id, tenantId, outletId, paymentMethod, createdAt;
    public final long total;

    public Transaction(String id,String tenantId,String outletId,long total,String paymentMethod,String createdAt) {
        this.id=id; this.tenantId=tenantId; this.outletId=outletId; this.total=total;
        this.paymentMethod=paymentMethod; this.createdAt=createdAt;
    }
}

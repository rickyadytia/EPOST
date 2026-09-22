package id.epost.pos.model;

public final class Product {
    public final String id, tenantId, sku, name;
    public final long price;
    public final int stock;

    public Product(String id, String tenantId, String sku, String name, long price, int stock) {
        this.id=id; this.tenantId=tenantId; this.sku=sku; this.name=name; this.price=price; this.stock=stock;
    }
}

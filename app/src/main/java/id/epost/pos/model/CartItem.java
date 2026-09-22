package id.epost.pos.model;

public final class CartItem {
    public final Product product;
    public int qty;

    public CartItem(Product product, int qty) { this.product=product; this.qty=qty; }
    public long subtotal() { return product.price * qty; }
}

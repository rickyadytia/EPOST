package id.epost.pos.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import id.epost.pos.model.Product;
import id.epost.pos.model.Transaction;
import id.epost.pos.tenant.TenantContext;

public final class DemoRepository {
    private final List<Transaction> transactions = new ArrayList<>();

    public List<Product> products(TenantContext tenant) {
        return Arrays.asList(
            new Product("p1",tenant.tenantId(),"KOPI-01","Kopi Susu",18000,40),
            new Product("p2",tenant.tenantId(),"TEH-01","Es Teh",10000,55),
            new Product("p3",tenant.tenantId(),"NASI-01","Nasi Goreng",28000,25),
            new Product("p4",tenant.tenantId(),"MIE-01","Mie Goreng",25000,30),
            new Product("p5",tenant.tenantId(),"AIR-01","Air Mineral",7000,80),
            new Product("p6",tenant.tenantId(),"SNACK-01","Kentang Goreng",22000,20)
        );
    }

    public void save(Transaction transaction) {
        if (!transaction.tenantId.isEmpty()) transactions.add(0, transaction);
    }

    public List<Transaction> transactions(TenantContext tenant) {
        List<Transaction> scoped = new ArrayList<>();
        for (Transaction t : transactions) if (tenant.tenantId().equals(t.tenantId)) scoped.add(t);
        return scoped;
    }
}

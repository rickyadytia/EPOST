# EPOST Architecture

Vertical slice: login -> tenant/outlet -> catalog -> cart -> cash checkout -> transaction history.

Every business record is tenant-scoped through TenantContext. Authentication must resolve tenant membership server-side; a client tenant ID is never authorization.

DemoRepository is a temporary adapter. Next: offline database + sync queue, tenant-aware REST API, inventory, payment methods, receipt/thermal printer, barcode scanner, roles and administration.

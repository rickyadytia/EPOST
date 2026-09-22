# EPOST

Android Point of Sales foundation designed for multi-tenant operation.

## Current phase

- Native Android application shell (Java, minSdk 24, target/compile SDK 35)
- Explicit `TenantContext` boundary for tenant/outlet scoping
- GitHub Actions CI that builds a debug APK on push, pull request, or manual dispatch
- APK is published as the `epost-debug-apk` workflow artifact

## Architecture direction

Tenant isolation is mandatory at every data boundary. Domain repositories and future API requests must receive tenant context explicitly. Planned modules are authentication/tenant selection, outlet/session, product catalog, cart, checkout, payment, receipt, inventory, transaction history, offline local storage/sync, and admin configuration.

## Build

CI uses JDK 17 and Gradle 8.9:

```bash
gradle :app:assembleDebug
```

The generated APK is `app/build/outputs/apk/debug/app-debug.apk`.

## Next milestone

Build the POS vertical slice: tenant login -> outlet selection -> catalog -> cart -> cash checkout -> local transaction history, then introduce API/offline synchronization.

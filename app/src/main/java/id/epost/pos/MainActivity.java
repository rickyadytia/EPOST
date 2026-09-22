package id.epost.pos;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import id.epost.pos.tenant.TenantContext;

public class MainActivity extends Activity {
    private final TenantContext demoTenant =
            new TenantContext("demo", "Demo Store", "main-outlet");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int pad = dp(24);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_VERTICAL);
        root.setPadding(pad, pad, pad, pad);
        root.setBackgroundColor(Color.rgb(248, 250, 252));

        TextView brand = text("EPOST", 34, true);
        TextView subtitle = text("Point of Sales • Multi-tenant", 18, false);
        TextView tenant = text("Tenant: " + demoTenant.tenantName(), 16, false);
        TextView status = text("Foundation ready — catalog, cart, checkout and sync are next.", 15, false);

        root.addView(brand);
        root.addView(subtitle);
        root.addView(space());
        root.addView(tenant);
        root.addView(status);
        setContentView(root);
    }

    private TextView text(String value, float sp, boolean bold) {
        TextView view = new TextView(this);
        view.setText(value);
        view.setTextSize(sp);
        view.setTextColor(Color.rgb(15, 23, 42));
        if (bold) view.setTypeface(view.getTypeface(), android.graphics.Typeface.BOLD);
        view.setPadding(0, dp(6), 0, dp(6));
        return view;
    }

    private TextView space() {
        TextView view = new TextView(this);
        view.setLayoutParams(new ViewGroup.LayoutParams(1, dp(20)));
        return view;
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}

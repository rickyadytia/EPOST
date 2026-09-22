package id.epost.pos;

import android.app.*; import android.graphics.*; import android.os.Bundle; import android.text.InputType; import android.view.*; import android.widget.*;
import java.text.SimpleDateFormat; import java.util.*;
import id.epost.pos.data.DemoRepository; import id.epost.pos.model.*; import id.epost.pos.tenant.TenantContext; import id.epost.pos.util.Money;

public class MainActivity extends Activity {
 private final DemoRepository repo=new DemoRepository(); private final List<CartItem> cart=new ArrayList<>();
 private TenantContext tenant; private LinearLayout content; private TextView cartBadge;
 @Override public void onCreate(Bundle b){super.onCreate(b);showLogin();}
 private void shell(String page){
  LinearLayout root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setBackgroundColor(Color.rgb(248,250,252));
  LinearLayout top=row();top.setPadding(dp(18),dp(12),dp(18),dp(12));top.addView(label("EPOST",24,true),new LinearLayout.LayoutParams(0,-2,1));top.addView(label(page,15,true));root.addView(top);
  content=new LinearLayout(this);content.setOrientation(LinearLayout.VERTICAL);content.setPadding(dp(18),dp(8),dp(18),dp(40));ScrollView scroll=new ScrollView(this);scroll.addView(content);root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
  LinearLayout nav=row();nav.addView(nav("POS",v->showPOS()),weight());nav.addView(nav("Transaksi",v->showTransactions()),weight());nav.addView(nav("Outlet",v->showOutlet()),weight());root.addView(nav);setContentView(root);
 }
 private void showLogin(){
  LinearLayout x=new LinearLayout(this);x.setOrientation(LinearLayout.VERTICAL);x.setGravity(Gravity.CENTER_VERTICAL);x.setPadding(dp(28),dp(28),dp(28),dp(28));x.setBackgroundColor(Color.rgb(248,250,252));
  x.addView(label("EPOST",38,true));x.addView(label("Point of Sales\n",20,false));x.addView(label("Masuk ke workspace tenant",16,true));
  EditText code=input("Tenant code");code.setText("DEMO");EditText email=input("Email");email.setText("cashier@demo.id");EditText pass=input("Password");pass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);pass.setText("demo");
  x.addView(code);x.addView(email);x.addView(pass);Button enter=button("Masuk");enter.setOnClickListener(v->{String c=code.getText().toString().trim();if(c.isEmpty()){code.setError("Tenant wajib diisi");return;}tenant=new TenantContext(c.toLowerCase(Locale.ROOT),"Demo Store","OUTLET-01");showPOS();});x.addView(enter);setContentView(x);
 }
 private void showPOS(){shell("Kasir • "+tenant.tenantName());content.addView(input("Cari produk / SKU"));cartBadge=label(cartText(),15,true);content.addView(cartBadge);for(Product p:repo.products(tenant))addProduct(p);Button checkout=button("Checkout");checkout.setOnClickListener(v->checkout());content.addView(checkout);}
 private void addProduct(Product p){LinearLayout c=row();c.setPadding(dp(10),dp(10),dp(4),dp(10));c.addView(label(p.name+"\n"+p.sku+" • stok "+p.stock+"\n"+Money.idr(p.price),16,true),new LinearLayout.LayoutParams(0,-2,1));Button add=button("+");add.setOnClickListener(v->{addCart(p);cartBadge.setText(cartText());});c.addView(add);content.addView(c);}
 private void addCart(Product p){for(CartItem i:cart)if(i.product.id.equals(p.id)){i.qty++;return;}cart.add(new CartItem(p,1));}
 private long total(){long n=0;for(CartItem i:cart)n+=i.subtotal();return n;} private int qty(){int n=0;for(CartItem i:cart)n+=i.qty;return n;} private String cartText(){return "Keranjang: "+qty()+" item • "+Money.idr(total());}
 private void checkout(){if(cart.isEmpty()){toast("Keranjang masih kosong");return;}new AlertDialog.Builder(this).setTitle("Pembayaran").setMessage("Total "+Money.idr(total())+"\nMetode: Tunai").setNegativeButton("Batal",null).setPositiveButton("Bayar",(d,w)->complete()).show();}
 private void complete(){String now=new SimpleDateFormat("dd MMM yyyy HH:mm",new Locale("id","ID")).format(new Date());repo.save(new Transaction("TRX-"+System.currentTimeMillis(),tenant.tenantId(),tenant.outletId(),total(),"TUNAI",now));cart.clear();toast("Transaksi berhasil");showTransactions();}
 private void showTransactions(){shell("Riwayat Transaksi");List<Transaction> list=repo.transactions(tenant);content.addView(label("Outlet "+tenant.outletId()+" • "+list.size()+" transaksi\n",14,false));if(list.isEmpty())content.addView(label("Belum ada transaksi.",16,false));for(Transaction t:list)content.addView(label(t.id+"\n"+t.createdAt+" • "+t.paymentMethod+"\n"+Money.idr(t.total)+"\n",16,true));}
 private void showOutlet(){shell("Outlet");content.addView(label(tenant.tenantName(),24,true));content.addView(label("Tenant: "+tenant.tenantId()+"\nOutlet aktif: "+tenant.outletId()+"\nRole: CASHIER",16,false));Button out=button("Keluar");out.setOnClickListener(v->{cart.clear();tenant=null;showLogin();});content.addView(out);}
 private LinearLayout row(){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.HORIZONTAL);l.setGravity(Gravity.CENTER_VERTICAL);return l;} private LinearLayout.LayoutParams weight(){return new LinearLayout.LayoutParams(0,-2,1);}
 private Button nav(String s,View.OnClickListener l){Button b=button(s);b.setOnClickListener(l);return b;} private Button button(String s){Button b=new Button(this);b.setText(s);b.setAllCaps(false);return b;}
 private EditText input(String h){EditText e=new EditText(this);e.setHint(h);e.setSingleLine(true);return e;} private TextView label(String s,float z,boolean bold){TextView t=new TextView(this);t.setText(s);t.setTextSize(z);t.setTextColor(Color.rgb(15,23,42));if(bold)t.setTypeface(Typeface.DEFAULT,Typeface.BOLD);t.setPadding(0,dp(6),0,dp(6));return t;}
 private void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();} private int dp(int n){return Math.round(n*getResources().getDisplayMetrics().density);}
}
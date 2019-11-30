package com.si.adminperpus;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.os.*;
import android.graphics.*;
import android.app.ActionBar.LayoutParams;
import android.content.DialogInterface;

public class MainActivity extends Activity implements OnClickListener {
	
	AdminActivity adminActivity = new AdminActivity();
	TableLayout tableLayout;
	Button buttonTambahAdmin;
	ArrayList<Button>buttonEdit 	= new ArrayList<Button>();
	ArrayList<Button>buttonDelete 	= new ArrayList<Button>();
	JSONArray arrayAdmin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	// Jika SDK Android diatas API Ver.9
		if (android.os.Build.VERSION.SDK_INT > 9) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}
	// Mendapatkan data widget dari XML Activity melalui ID
		tableLayout 			= (TableLayout) findViewById(R.id.tableAdmin);
		buttonTambahAdmin 		= (Button) findViewById(R.id.buttonTambahAdmin);
		buttonTambahAdmin.setOnClickListener(this);
		
	//Menambah baris untuk tabel
		TableRow barisTabel = new TableRow(this);
		barisTabel.setBackgroundColor(Color.CYAN);
		
	// Menambahkan tampilan teks untuk judul pada tabel
		TextView viewHeaderID 	 	= new TextView(this);
		TextView viewHeaderNama 	= new TextView(this);
		TextView viewHeaderUsername = new TextView(this);
		TextView viewHeaderPassword = new TextView(this);
		TextView viewHeaderAction   = new TextView(this);
		
		viewHeaderID.setText	   ("ID");
		viewHeaderNama.setText 	   ("Nama");
		viewHeaderUsername.setText ("Username");
		viewHeaderPassword.setText ("Password");
		viewHeaderAction.setText   ("Action");
		
		viewHeaderID.setPadding			(5, 1, 5, 1);
		viewHeaderNama.setPadding		(5, 1, 5, 1);
		viewHeaderUsername.setPadding	(5, 1, 5, 1);
		viewHeaderPassword.setPadding	(5, 1, 5, 1);
		viewHeaderAction.setPadding	(5, 1, 5, 1);
		
	// Menampilkan tampilan TextView ke dalam tabel
		barisTabel.addView	(viewHeaderID);
		barisTabel.addView	(viewHeaderNama);
		barisTabel.addView	(viewHeaderUsername);
		barisTabel.addView	(viewHeaderPassword);
		barisTabel.addView	(viewHeaderAction);
		
	// Menyusun ukuran dari tabel
		tableLayout.addView(barisTabel, new
		TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		try {
			
	// Mengubah data dari BiodataActivity yang berupa String menjadi array
		arrayAdmin = new JSONArray(adminActivity.tampilAdmin());
		for (int i = 0; i < arrayAdmin.length(); i++) {
		JSONObject jsonChildNode = arrayAdmin.getJSONObject(i);
		
		String nama_admin 	 = jsonChildNode.optString	("nama_admin");
		String username 	 = jsonChildNode.optString	("username");
		String password 	 = jsonChildNode.optString	("password");
		String id_admin 	 = jsonChildNode.optString	("id_admin");
		
		System.out.println	("Nama : " 	   + nama_admin );
		System.out.println	("Username : " + username );
		System.out.println	("Password : " + password );
		System.out.println	("ID : " 	   + id_admin);
		barisTabel = new TableRow(this);
		
	// Memberi warna pada baris tabel
		if (i % 2 == 0) {
		barisTabel.setBackgroundColor(Color.LTGRAY);
		}
		
		TextView viewID = new TextView(this);
		viewID.setText(id_admin);
		viewID.setPadding(5, 1, 5, 1);
		barisTabel.addView(viewID);

		TextView viewNama = new TextView(this);
		viewNama.setText(nama_admin);
		viewNama.setPadding(5, 1, 5, 1);
		barisTabel.addView(viewNama);
		
		TextView viewUsername = new TextView(this);
		viewUsername.setText(username);
		viewUsername.setPadding(5, 1, 5, 1);
		barisTabel.addView(viewUsername);
		
		TextView viewPassword = new TextView(this);
		viewPassword.setText(password);
		viewPassword.setPadding(5, 1, 5, 1);
		barisTabel.addView(viewPassword);

	// Menambahkan button Edit
		buttonEdit.add(i, new Button(this));
		buttonEdit.get(i).setId(Integer.parseInt(id_admin));
		buttonEdit.get(i).setTag("Edit");
		buttonEdit.get(i).setText("Edit");
		buttonEdit.get(i).setOnClickListener(this);
		barisTabel.addView(buttonEdit.get(i));
		
	// Menambahkan tombol Delete
		buttonDelete.add(i, new Button(this));
		buttonDelete.get(i).setId(Integer.parseInt(id_admin));
		buttonDelete.get(i).setTag("Delete");
		buttonDelete.get(i).setText("Delete");
		buttonDelete.get(i).setOnClickListener(this);
		barisTabel.addView(buttonDelete.get(i));
		
		tableLayout.addView(barisTabel, new TableLayout.LayoutParams
				(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
	}
	catch (JSONException e) {
		e.printStackTrace();
	}
	}
public void onClick (View view) {
	if (view.getId() == R.id.buttonTambahAdmin) {
		tambahAdmin();
	}else{
	for (int i= 0; i < buttonEdit.size(); i++) {
	// Jika ingin mengedit data pada biodata
		if (view.getId() == buttonEdit.get(i).getId() &&
		view.getTag().toString().trim().equals("Edit")) {
		Toast.makeText(MainActivity.this, "Edit : " + buttonEdit.get(i).getId(),
		Toast.LENGTH_SHORT).show();
		int id_admin = buttonEdit.get(i).getId();
		getAdminByID(id_admin);
		}
	// Menghapus data di Tabel
		else if (view.getId() == buttonDelete.get(i).getId() &&
		view.getTag().toString().trim().equals("Delete")){
		Toast.makeText(MainActivity.this, "Delete : " +
		buttonDelete.get(i).getId(), Toast.LENGTH_SHORT).show();
		int id_admin = buttonDelete.get(i).getId();
		deleteAdmin(id_admin);
		}
		}
	}
}

public void deleteAdmin (int id_admin) {
	adminActivity.deleteAdmin(id_admin);
		finish();
		startActivity(getIntent());
	}
	// Mendapatkan Biodata melalui ID
public void getAdminByID (int id_admin) {
	String nama_adminEdit = null,
		   usernameEdit   = null,
		   passwordEdit   = null;
	
		JSONArray arrayPersonal;
		try {
			arrayPersonal = new JSONArray(adminActivity.getAdminById(id_admin));
			for (int i = 0; i < arrayPersonal.length(); i++) {
				JSONObject jsonChildNode = arrayPersonal.getJSONObject(i);
				nama_adminEdit = jsonChildNode.optString("nama_admin");
				usernameEdit   = jsonChildNode.optString("username");
				passwordEdit   = jsonChildNode.optString("password");
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		LinearLayout layoutInput = new LinearLayout(this);
		layoutInput.setOrientation(LinearLayout.VERTICAL);
		
	// Membuat id tersembunyi pada AlertDialog
		final TextView viewId = new TextView(this);
		viewId.setText(String.valueOf(id_admin));
		viewId.setTextColor(Color.TRANSPARENT);
		layoutInput.addView(viewId);
		
		final EditText editNama = new EditText(this);
		editNama.setText(nama_adminEdit);
		layoutInput.addView(editNama);
		
		final EditText editUsername = new EditText(this);
		editUsername.setText(usernameEdit);
		layoutInput.addView(editUsername);
		
		final EditText editPassword = new EditText(this);
		editPassword.setText(passwordEdit);
		layoutInput.addView(editPassword);
		
	// Membuat AlertDialog untuk mengubah data di Biodata
		AlertDialog.Builder builderEditAdmin = new AlertDialog.Builder(this);
		
	//builderEditBiodata.setIcon(R.drawable.webse);
		builderEditAdmin.setTitle("Update Admin");
		builderEditAdmin.setView(layoutInput);
		builderEditAdmin.setPositiveButton("Update", new DialogInterface.OnClickListener()
		{
			
@Override
public void onClick(DialogInterface dialog, int which) {
	String nama_admin = editNama.getText().toString();
	String username = editUsername.getText().toString();
	String password = editPassword.getText().toString();
	
	System.out.println("Nama : " 	 + nama_admin +
					   "Username : " + username +
					   "Password : " + password);
	
	String laporan = adminActivity.updateAdmin(viewId.getText().toString(),
					editNama.getText().toString(),
					editUsername.getText().toString(),
					editPassword.getText().toString());
	Toast.makeText(MainActivity.this, laporan, Toast.LENGTH_SHORT).show();
	finish();
	startActivity(getIntent());
	}
	});
		
	// Jika tidak ingin mengubah data pada Biodata
		builderEditAdmin.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
		{
			
@Override
public void onClick(DialogInterface dialog, int which) {
	dialog.cancel();
	}
});
		builderEditAdmin.show();
}
public void tambahAdmin() {
	LinearLayout layoutInput = new LinearLayout(this);
	layoutInput.setOrientation(LinearLayout.VERTICAL);
	
	final EditText editNama = new EditText(this);
	editNama.setHint("Nama");
	layoutInput.addView(editNama);
	
	final EditText editUsername = new EditText(this);
	editUsername.setHint("Username");
	layoutInput.addView(editUsername);
	
	final EditText editPassword = new EditText(this);
	editPassword.setHint("Password");
	layoutInput.addView(editPassword);
	
// Membuat AlertDialog untuk menambahkan data pada Biodata
AlertDialog.Builder builderInsertAdmin= new AlertDialog.Builder(this);

//builderInsertBiodata.setIcon(R.drawable.webse);
	builderInsertAdmin.setTitle("Insert Kategori");
	builderInsertAdmin.setView(layoutInput);
	builderInsertAdmin.setPositiveButton("Insert", new
	DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {
	String nama_admin = editNama.getText().toString();
	String username = editUsername.getText().toString();
	String password = editPassword.getText().toString();
	
	System.out.println("Nama : " 	 + nama_admin +
					   "Username : " + username +
					   "Password : " + password);
	
	String laporan = adminActivity.insertAdmin(nama_admin,
											   username,
											   password);
	Toast.makeText(MainActivity.this, laporan, Toast.LENGTH_SHORT).show();
	finish();
	startActivity(getIntent());
	}
});
	builderInsertAdmin.setNegativeButton("Cancel", new
	DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {
	dialog.cancel();
	}
});
	builderInsertAdmin.show();
}
@Override
public boolean onCreateOptionsMenu(Menu menu) {
//Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
	}
}

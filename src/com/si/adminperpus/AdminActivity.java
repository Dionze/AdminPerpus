package com.si.adminperpus;

import com.si.adminperpus.KoneksiActivity;

public class AdminActivity extends KoneksiActivity {
// sourcecode untuk URL ->URL menggunakan IP address default eclipse
	String URL = "http://192.168.8.101/Perpustakaan/server.php";
	String url = "";
	String response = "";
//menampilkan kategori dari database
public String tampilAdmin() {
	try{
		url = URL + "?operasi_admin=view";
		System.out.println("URL Tampil Admin : " + url);
		response = call(url);
		}
	catch(Exception e) {
	}
	return response;
	}
//memasukan biodata baru ke dlama database
public String insertAdmin(String nama_admin,
						  String username,
						  String password) {
	try{
		url = URL + "?operasi_admin=insert&nama_admin=" + nama_admin +
										 "&username="	+ username +
										 "&password="	+ password;
		System.out.println("URL Insert Admin : " + url);
		response = call(url);
	}
	catch (Exception e){
	}
	return response;
	}
//melihat biodata berdasarkan ID
public String getAdminById (int id_admin) {
	try{
		url=URL + "?operasi_admin=get_admin_by_id&id_admin=" + id_admin;
		System.out.println("URL Insert Admin : " + url);
		response = call(url);
		}
	catch(Exception e) {
	}
	return response;
	}
//mengubah isi biodata
public String updateAdmin(String id_admin,
						  String nama_admin,
						  String username,
						  String password) {
	try{
		url=URL + "?operasi_admin=update&id_admin=" 	+ id_admin + 
									   "&nama_admin=" 	+ nama_admin +
									   "&username="		+ username +
									   "&password="		+ password;
		System.out.println("URL Update Admin : " + url);
		response = call(url);
		}
	catch(Exception e){
	}
	return response;
	}
//coding hapus
public String deleteAdmin (int id_admin) {
	try{
		url = URL + "?operasi_admin=delete&id_admin=" + id_admin;
		System.out.println("URL Hapus Admin : " + url);
		response = call(url);
		}
	catch(Exception e){
	}
	return response;
	}
}
/*
 * Created by Albert Kristaen (Kayzweller) on 29/06/20 21.27
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 29/06/20 21.27
 */

package com.xoxltn.pinjam_aja.registrasi;

import android.text.Editable;
import android.text.TextWatcher;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xoxltn.pinjam_aja.R;

import java.util.Objects;

public class InfoPribadiActivity extends AppCompatActivity {

    private DocumentReference mDocRef;
    String mUserID;

    private AutoCompleteTextView mPendidikan, mPernikahan, mKendaraan, mKota, mProvinsi;
    private TextInputLayout mAlamat, mNamaIbu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pribadi);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore mFire = FirebaseFirestore.getInstance();

        mUserID = mAuth.getUid();
        mDocRef = mFire.collection("USER").document(mUserID);

        mPendidikan = findViewById(R.id.pribadi_pendidikan);
        mPernikahan = findViewById(R.id.pribadi_pernikahan);
        mAlamat = findViewById(R.id.pribadi_alamat);
        mKota = findViewById(R.id.pribadi_kota);
        mProvinsi = findViewById(R.id.pribadi_provinsi);
        mNamaIbu = findViewById(R.id.pribadi_ibu);
        mKendaraan = findViewById(R.id.pribadi_kendaraan);

        getListPendidikan();
        getListPernikahan();
        getListProvinsi();
        getListKota();
        getListKendaraan();

        loadPreviousData();

    }

    //-------------------------------------------------------------------------------------------//

    private void getListPendidikan() {
        String[] items = new String[] {
                "Sekolah Dasar (SD)", "Sekolah Menengah Pertama (SMP)",
                "Sekolah Menengah Atas (SMA)", "D3/Akademi", "Sarjana", "Magister"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                InfoPribadiActivity.this,
                R.layout.dropdown_item,
                items
        );

        mPendidikan.setAdapter(adapter);
    }

    private void getListPernikahan() {
        String[] items = new String[] {
                "Belum Menikah", "Nikah - Punya Anak", "Nikah - Tanpa Anak", "Cerai"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                InfoPribadiActivity.this,
                R.layout.dropdown_item,
                items
        );

        mPernikahan.setAdapter(adapter);
    }

    private void getListProvinsi() {
        mProvinsi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                listProvinsi();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                listProvinsi();
            }
        });
    }
    
    private void listProvinsi() {
        String[] items = new String[] {
                "Aceh", "Sumatra Utara", "Sumatra Barat", "Riau", "Kepulauan Riau", "Jambi",
                "Bengkulu", "Sumatra Selatan", "Kepulauan Bangka Belitung", "Lampung",
                "Banten", "Jawa Barat", "Jakarta", "Jawa Tengah", "Yogyakarta", "Jawa Timur",
                "Bali", "Nusa Tenggara Barat", "Nusa Tenggara Timur", "Kalimantan Barat",
                "Kalimantan Selatan", "Kalimantan Tengah", "Kalimantan Timur",
                "Kalimantan Utara", "Gorontalo", "Sulawesi Barat", "Sulawesi Selatan",
                "Sulawesi Tengah", "Sulawesi Tenggara", "Sulawesi Utara", "Maluku",
                "Maluku Utara", "Papua Barat", "Papua"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                InfoPribadiActivity.this,
                R.layout.dropdown_item,
                items
        );

        mProvinsi.setAdapter(adapter);
    }

    private void getListKota() {
        mProvinsi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // NOTHING HERE.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String call_provinsi = mProvinsi.getText().toString();
                listKota(call_provinsi);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //NOTHING HERE.
            }
        });
    }

    private void listKota(String loadProvinsi) {
        switch (loadProvinsi) {

            case "Aceh": {
                String[] items = new String[]{
                        "Meulaboh", "Blangpidie", "Kota Jantho", "Calang", "Tapak Tuan",
                        "Singkil", "Karang Baru", "Takengon", "Kutacane", "Idi Rayeuk",
                        "Lhosukon", "Simpang Tiga Redelong", "Bireuen", "Blang Kejeren",
                        "Suka Makmue", "Sigli", "Meureudu", "Sinabang", "Banda Aceh",
                        "Langsa", "Lhokseumawe", "Sabang", "Subulussalam"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Sumatra Utara": {
                String[] items = new String[]{
                        "Kisaran", "Limapuluh", "Sidikalang", "Lubuk Pakam",
                        "Dolok Sanggul", "Kabanjahe", "Rantau Prapat", "Kota Pinang",
                        "Aek Kanopan", "Stabat", "Panyabungan", "Gido", "Lahomi",
                        "Teluk Dalam", "Lotu", "Sibuhuan", "Gunung Tua", "Salak",
                        "Pangururan", "Sei Rampah", "Raya", "Sipirok", "Pandan",
                        "Tarutung", "Balige", "Binjai", "Gunungsitoli", "Medan",
                        "Padangsidempuan", "Pematangsiantar", "Sibolga",
                        "Tanjungbalai", "Tebing Tinggi"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Sumatra Barat": {
                String[] items = new String[]{
                        "Lubuk Basung", "Pulau Punjung", "Tuapejat", "Sarilamak",
                        "Parit Malintang", "Lubuk Sikaping", "Simpang Ampek",
                        "Painan", "Muaro Sijunjung", "Arosuka", "Padang Aro",
                        "Batusangkar", "Bukittinggi", "Padang", "Padangpanjang",
                        "Pariaman", "Payakumbuh", "Sawahlunto", "Solok"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Riau": {
                String[] items = new String[]{
                        "Bengkalis", "Tembilahan", "Rengat", "Bangkinang", "Selatpanjang",
                        "Teluk Kuantan", "Pangkalan Kerinci", "Bagansiapiapi", "Pasir Pengairan",
                        "Siak Sri Indrapura", "Dumai", "Pekanbaru"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Kepulauan Riau": {
                String[] items = new String[]{
                        "Kijang", "Tanjung Balai Karimun", "Tarempa", "Daik", "Ranai",
                        "Batam", "Tanjungpinang"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Jambi": {
                String[] items = new String[]{
                        "Muara Bulian", "Muara Bungo", "Siulak", "Bangko", "Sengeti",
                        "Sarolangun", "Kuala Tungkal", "Muara Sabak", "Muara Tebo",
                        "Jambi", "Sungaipenuh"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Bengkulu": {
                String[] items = new String[]{
                        "Manna", "Karang Tinggi", "Arga Makmur", "Bintuhan", "Kepahiang",
                        "Muara Aman", "Mukomuko", "Curup", "Tais", "Bengkulu"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            // TODO : CEK ADA MARTAPURA
            case "Sumatra Selatan": {
                String[] items = new String[]{
                        "Pangkalan Balai", "Tebing Tinggi", "Lahat", "Muara Enim",
                        "Sekayu", "Muara Beliti Baru", "Rupit", "Indralaya",
                        "Kota Kayu Agung", "Baturaja", "Muaradua", "Martapura",
                        "Talang Ubi", "Lubuklinggau", "Pagar Alam", "Palembang",
                        "Prabumulih"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Kepulauan Bangka Belitung": {
                String[] items = new String[]{
                        "Sungai Liat", "Muntok", "Toboali", "Koba", "Tanjung Pandan",
                        "Manggar", "Pangkalpinang"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Lampung": {
                String[] items = new String[]{
                        "Liwa", "Kalianda", "Gunung Sugih", "Sukadana", "Kotabumi",
                        "Mesuji", "Gedong Tataan", "Krui", "Pringsewu", "Kota Agung",
                        "Menggala", "Tulang Bawang Tengah", "Blambangan Umpu",
                        "Bandar Lampung", "Metro"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Banten": {
                String[] items = new String[]{
                        "Rangkasbitung", "Padeglang", "Ciruas", "Tigaraksa", "Cilegon",
                        "Serang", "Tangerang", "Tangerang Selatan"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Jawa Barat": {
                String[] items = new String[]{
                        "Soerang", "Ngamprah", "Cikarang", "Cibinong", "Ciamis", "Cianjur",
                        "Sumber", "Tarogong Kidul", "Indramayu", "Karawang", "Kuningan",
                        "Majalengka", "Parigi", "Purwakarta", "Subang", "Palabuhanratu",
                        "Sumedang", "Singaparna", "Bandung", "Banjar", "Bekasi", "Bogor",
                        "Cimahi", "Cirebon", "Depok", "Sukabumi", "Tasikmalaya"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Jakarta": {
                String[] items = new String[]{
                        "Kembangan", "Menteng", "Kebayoran Baru", "Cakung", "Koja"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Jawa Tengah": {
                String[] items = new String[]{
                        "Banjarnegara", "Purwokerto", "Batang", "Blora",
                        "Boyolali", "Brebes", "Cilacap", "Demak", "Purwodadi",
                        "Jepara", "Karanganyar", "Kebumen", "Kendal", "Klaten",
                        "Kudus", "Mungkid", "Pati", "Kajen", "Pemalang", "Purbalingga",
                        "Purworejo", "Rembang", "Ungaran", "Sragen", "Sukoharjo",
                        "Siawi", "Temanggung", "Wonogiri", "Wonosobo", "Magelang",
                        "Pekalongan", "Salatiga", "Semarang", "Surakarta", "Tegal"

                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Yogyakarta": {
                String[] items = new String[]{
                        "Bantul", "Wonosari", "Wates", "Sleman", "Yogyakarta"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Jawa Timur": {
                String[] items = new String[]{
                        "Bangkalan", "Banyuwangi", "Kanigoro", "Bojonegoro", "Bondowoso",
                        "Gresik", "Jember", "Jombang", "Ngasem", "Lamongan", "Lumajang",
                        "Caruban", "Magetan", "Kepanjen", "Mojosari", "Nganjuk", "Ngawi",
                        "Pacitan", "Pamekasan", "Bangil", "Ponorogo", "Kraksaan", "Sampang",
                        "Sidoarjo", "Situbondo", "Sumenep", "Trenggalek", "Tuban",
                        "Tulungagung", "Batu", "Blitar", "Kediri", "Madiun", "Malang",
                        "Malang", "Mojokerto", "Pasuruan", "Probolinggo", "Surabaya"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Bali": {
                String[] items = new String[]{
                        "Mangupura", "Bangli", "Singaraja", "Gianyar", "Negara",
                        "Amlapura", "Semarapura", "Tabanan", "Denpasar"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Nusa Tenggara Barat": {
                String[] items = new String[]{
                        "Woha", "Dompu", "Gerung", "Praya", "Selong", "Tanjung",
                        "Sumbawa Besar", "Taliwang", "Bima", "Mataram"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Nusa Tenggara Timur": {
                String[] items = new String[]{
                        "Kalabari", "Atambua", "Ende", "Larantuka", "Oelamasi",
                        "Lewoleba", "Betun", "Ruteng", "Labuan Bajo", "Borong",
                        "Mbay", "Bajawa", "Baa", "Seba", "Maumere", "Waikabubak",
                        "Tambolaka", "Waibakul", "Waingapu", "Soe", "Kefamenanu",
                        "Kupang"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Kalimantan Barat": {
                String[] items = new String[]{
                        "Bengkayang", "Putusibau", "Sukadana", "Ketapang", "Sungai Raya",
                        "Ngabang", "Nanga Pinoh", "Mempawah", "Sambas", "Sanggau",
                        "Sekadau", "Sintang", "Pontianak", "Singkawang"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Kalimantan Selatan": {
                String[] items = new String[]{
                        "Paringin", "Martapura", "Marabahan", "Kandangan", "Barabai",
                        "Amuntai", "Kota Baru", "Tanjung", "Batu Licin", "Pelaihari",
                        "Rantau", "Banjarbaru", "Banjarmasin"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Kalimantan Tengah": {
                String[] items = new String[]{
                        "Buntok", "Tamiang Layang", "Muara Teweh", "Kuala Kurun",
                        "Kuala Kapuas", "Kasongan", "Pangkalan Bun", "Sampit",
                        "Sampit", "Nanga Bulik", "Puruk Cahu", "Pulang Pisau",
                        "Sukamara", "Kuala Pembuang", "Palangka Raya"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Kalimantan Timur": {
                String[] items = new String[]{
                        "Tanjung Redeb", "Malinau", "Tideng Pale", "Tarakan"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Gorontalo": {
                String[] items = new String[]{
                        "Tilamuta", "Suwawa", "Limboto", "Kwandang",
                        "Marisa", "Gorontalo"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Sulawesi Barat": {
                String[] items = new String[]{
                        "Banggae", "Mamasa", "Mamuju", "Tobadak", "Pasangkayu",
                        "Polewali"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Sulawesi Selatan": {
                String[] items = new String[]{
                        "Bantaeng", "Barru", "Watampone", "Bulukumba", "Enrekang",
                        "Sungguminasa", "Bontosunggu", "Benteng", "Belopa",
                        "Malili", "Masamba", "Turikale", "Pangkajene", "Pinrang",
                        "Watang Sidenreng", "Balangnipa", "Watansoppeng",
                        "Pattallassang", "Makale", "Rantepao", "Sengkang",
                        "Makassar", "Palopo", "Parepare"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Sulawesi Tenggara": {
                String[] items = new String[]{
                        "Rumbia", "Pasarwajo", "Batauga", "Labungkari", "Buranga",
                        "Kolaka", "Tirawuta", "Lasusua", "Unaaha", "Langara",
                        "Andolo", "Wanggudu", "Raha", "Sawerigadi", "Wangi-Wangi",
                        "Bau-Bau", "Kendari"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Sulawesi Tengah": {
                String[] items = new String[]{
                        "Luwuk", "Salakan", "Banggai", "Buol", "Banawa", "Bungku",
                        "Kolonodale", "Parigi", "Poso", "Sigi Biromaru", "Ampana",
                        "Tolitoli", "Palu"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Sulawesi Utara": {
                String[] items = new String[]{
                        "Lolak", "Molibagu", "Tutuyan", "Boroko", "Tahuna",
                        "Ondong Siau", "Melonguane", "Tondano", "Amurang",
                        "Ratahan", "Airmadidi", "Bitung", "Kotamobagu",
                        "Manado", "Tomohon"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Maluku": {
                String[] items = new String[]{
                        "Namlea", "Namrole", "Dobo", "Tiakur", "Masohi", "Langgur",
                        "Saumlaki", "Piru", "Bula", "Ambon", "Tual"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Maluku Utara": {
                String[] items = new String[]{
                        "Jailolo", "Weda", "Maba", "Labuha", "Tobelo", "Sanana",
                        "Daruba", "Bobong", "Ternate", "Tidore Kepulauan"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            case "Papua Barat": {
                String[] items = new String[]{
                        "Agats", "Biak", "Tanah Merah", "Tigi", "Kigamani",
                        "Sugapa", "Sentani", "Wamena", "Waris", "Serui",
                        "Tiom", "Burmeso", "Kobakma", "Kepi", "Merauke",
                        "Timika", "Nabire", "Kenyam", "Enarotali", "Oksibil",
                        "Ilaga", "Kotamulia", "Sarmi", "Sorendiweri",
                        "Karubaga", "Botawa", "Sumohai", "Elelim", "Jayapura"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

            // TODO : CEK FEF
            case "Papua": {
                String[] items = new String[]{
                        "Fakfak", "Kaimana", "Manokwari", "Ransiki",
                        "Kumurkek", "Anggi", "Waisai", "Aimas",
                        "Teminabuan", "Fef", "Bintuni", "Rasiei",
                        "Sorong"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        InfoPribadiActivity.this,
                        R.layout.dropdown_item,
                        items
                );
                mKota.setAdapter(adapter);
                break;
            }

        }
    }

    private void getListKendaraan() {
        String[] items = new String[] {
                "Tidak Memiliki Kendaraan",
                "Sepeda Motor",
                "Mobil"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                InfoPribadiActivity.this,
                R.layout.dropdown_item,
                items
        );

        mKendaraan.setAdapter(adapter);
    }

    //-------------------------------------------------------------------------------------------//

    private void loadPreviousData() {
        loadPendidikan();
        loadPernikahan();
        loadAlamat();
        loadKota();
        loadProvinsi();
        loadNamaIbu();
        loadKendaraan();
    }

    private void loadPendidikan() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String load = doc.getString("pribadi_pendidikan");
                if (mPendidikan.getText() != null) {
                    mPendidikan.setText(load);
                }
            }
        });
    }

    private void loadPernikahan() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String load = doc.getString("pribadi_pernikahan");
                if (mPernikahan.getText() != null) {
                    mPernikahan.setText(load);
                }
            }
        });
    }

    private void loadAlamat() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String load = doc.getString("pribadi_alamat");
                if (mAlamat.getEditText() != null) {
                    mAlamat.getEditText().setText(load);
                }
            }
        });
    }

    private void loadProvinsi() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String load = doc.getString("pribadi_provinsi");
                if (mProvinsi.getText() != null) {
                    mProvinsi.setText(load);
                }
            }
        });
    }

    private void loadKota() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String load = doc.getString("pribadi_kota");
                if (mKota.getText() != null) {
                    mKota.setText(load);
                }
            }
        });
    }

    private void loadNamaIbu() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String load = doc.getString("pribadi_namaibu");
                if (mNamaIbu.getEditText() != null) {
                    mNamaIbu.getEditText().setText(load);
                }
            }
        });
    }

    private void loadKendaraan() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String load = doc.getString("pribadi_kendaraan");
                if (mKendaraan.getText() != null) {
                    mKendaraan.setText(load);
                }
            }
        });
    }

    //-------------------------------------------------------------------------------------------//

    private boolean validatePendidikan() {
        String val = mPendidikan.getText().toString();

        if (val.isEmpty()) {
            mPendidikan.setError("Form tidak boleh kosong!");
            return false;
        } else {
            mPendidikan.setError(null);
            return true;
        }
    }

    private boolean validatePernikahan() {
        String val = mPernikahan.getText().toString();

        if (val.isEmpty()) {
            mPernikahan.setError("Form tidak boleh kosong!");
            return false;
        } else {
            mPernikahan.setError(null);
            return true;
        }
    }

    private boolean validateAlamat() {
        String val = Objects.requireNonNull(mAlamat.getEditText()).getText().toString();

        if (val.isEmpty()) {
            mAlamat.setError("Form tidak boleh kosong!");
            return false;
        } else {
            mAlamat.setError(null);
            return true;
        }
    }

    private boolean validateKota() {
        String val = mKota.getText().toString();

        if (val.isEmpty()) {
            mKota.setError("Form tidak boleh kosong!");
            return false;
        } else {
            mKota.setError(null);
            return true;
        }
    }

    private boolean validateProvinsi() {
        String val = mProvinsi.getText().toString();

        if (val.isEmpty()) {
            mProvinsi.setError("Form tidak boleh kosong!");
            return false;
        } else {
            mProvinsi.setError(null);
            return true;
        }
    }

    private boolean validateNamaIbu() {
        String val = Objects.requireNonNull(mNamaIbu.getEditText()).getText().toString();

        if (val.isEmpty()) {
            mNamaIbu.setError("Form tidak boleh kosong!");
            return false;
        } else {
            mNamaIbu.setError(null);
            return true;
        }
    }

    private boolean validateKendaraan() {
        String val = mKendaraan.getText().toString();

        if (val.isEmpty()) {
            mKendaraan.setError("Form tidak boleh kosong!");
            return false;
        } else {
            mKendaraan.setError(null);
            return true;
        }
    }


    public void OnClickButtonUploadKontak(View view) {

        if (!validatePendidikan() | !validatePernikahan() | !validateAlamat() | !validateKota()
                | !validateProvinsi() | !validateNamaIbu() | !validateKendaraan()) {
            return;
        }

        String pribadi_pendidikan = mPendidikan.getText().toString();
        String pribadi_pernikahan = mPernikahan.getText().toString();
        String pribadi_alamat = Objects.requireNonNull(mAlamat.getEditText())
                .getText().toString();

        String pribadi_kota = mKota.getText().toString();
        String pribadi_provinsi = mProvinsi.getText().toString();

        String pribadi_namaibu = Objects.requireNonNull(mNamaIbu.getEditText())
                .getText().toString();
        String pribadi_kendaraan = mKendaraan.getText().toString();

        mDocRef.update("pribadi_pendidikan", pribadi_pendidikan);
        mDocRef.update("pribadi_pernikahan", pribadi_pernikahan);
        mDocRef.update("pribadi_alamat", pribadi_alamat);
        mDocRef.update("pribadi_kota", pribadi_kota);
        mDocRef.update("pribadi_provinsi", pribadi_provinsi);
        mDocRef.update("pribadi_namaibu", pribadi_namaibu);
        mDocRef.update("pribadi_kendaraan", pribadi_kendaraan);

        mDocRef.update("info_pribadi", "done")
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(InfoPribadiActivity.this,
                            "DATA PRIBADI DIPERAHARUI!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                });

    }

    //-------------------------------------------------------------------------------------------//

    public void ButtonBackArrow(View view) {
        finish();
    }
}

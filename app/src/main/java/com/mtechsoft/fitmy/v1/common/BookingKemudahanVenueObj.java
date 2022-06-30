package com.mtechsoft.fitmy.v1.common;

public class BookingKemudahanVenueObj {
    private int pengurusan_kemudahan_sedia_ada_id; // "pengurusan_kemudahan_sedia_ada_id": 1,
    private int pengurusan_kemudahan_venue_id; // "pengurusan_kemudahan_venue_id": 1,
    private String refPengurusanVenue; // "refPengurusanVenue": null,
    private String kod_penjenisan; // "kod_penjenisan": null,
    private String nama_kemudahan; // "nama_kemudahan": null,
    private int sukan_rekreasi; // "sukan_rekreasi": 3,
    private SukanRekreasi refSukanRekreasi; // "refSukanRekreasi": { "id": 3, "desc": "BADMINTON", "aktif": 1, "created_by": null, "updated_by": null, "created": "2016-03-03 15:54:12", "updated": null },
    private int jenis_kemudahan; // "jenis_kemudahan": 3,
    private JenisKemudahan refJenisKemudahan; // "refJenisKemudahan": { "id": 3, "desc": "GELANGGANG SERBAGUNA", "aktif": 1, "created_by": null, "updated_by": 490, "created": "2016-03-03 15:52:57", "updated": "2019-02-25 15:54:18" },
    private String size; // "size": "",
    private String lokasi; // "lokasi": null,
    private String keluasan_padang; // "keluasan_padang": "",
    private String jumlah_kapasiti; // "jumlah_kapasiti": null,
    private String bilangan_kekerapan_penyenggaran; // "bilangan_kekerapan_penyenggaran": null,
    private String kekerapan_penggunaan; // "kekerapan_penggunaan": null,
    private String kekerapan_kerosakan_berlaku; // "kekerapan_kerosakan_berlaku": null,
    private String cost_pembaikian; // "cost_pembaikian": null,
    private String kadar_sewaan_sejam_siang; // "kadar_sewaan_sejam_siang": "1.00",
    private String kadar_sewaan_sehari_siang; // "kadar_sewaan_sehari_siang": "10.00",
    private String kadar_sewaan_seminggu_siang; // "kadar_sewaan_seminggu_siang": "70.00",
    private String kadar_sewaan_sebulan_siang; // "kadar_sewaan_sebulan_siang": "300.00",
    private String kadar_sewaan_sejam_malam; // "kadar_sewaan_sejam_malam": "1.00",
    private String kadar_sewaan_sehari_malam; // "kadar_sewaan_sehari_malam": "10.00",
    private String kadar_sewaan_seminggu_malam; // "kadar_sewaan_seminggu_malam": "70.00",
    private String kadar_sewaan_sebulan_malam; // "kadar_sewaan_sebulan_malam": "300.00",
    private String kadar_sewaan_sejam_siang_foreigner; // "kadar_sewaan_sejam_siang_foreigner": "30.00",
    private String kadar_sewaan_sejam_malam_foreigner; // "kadar_sewaan_sejam_malam_foreigner": "50.00",
    private String kadar_sewaan_sehari_foreigner; // "kadar_sewaan_sehari_foreigner": "300.00",
    private String diskaun_peratus; // "diskaun_peratus": null,
    private int unit_ada; // "unit_ada": 1,
    private String item_tambahan; // "item_tambahan": null,
    private String gambar_1; // "gambar_1": "",
    private String gambar_2; // "gambar_2": "",
    private String gambar_3; // "gambar_3": "",
    private String gambar_4; // "gambar_4": "",
    private String gambar_5; // "gambar_5": "",
    private String session_id; // "session_id": "",
    private int created_by; // "created_by": 1,
    private int updated_by; // "updated_by": 1,
    private String created; // "created": "2017-11-18 23:56:16",
    private String updated; // "updated": "2017-11-18 23:56:16"

    class SukanRekreasi {
        private int id; // "id": 3,
        private String desc; // "desc": "BADMINTON",
        private int aktif; // "aktif": 1,
        private String created_by; // "created_by": null,
        private String updated_by; // "updated_by": null,
        private String created; // "created": "2016-03-03 15:54:12",
        private String updated; // "updated": null
    }

    class JenisKemudahan {
        private int id; // "id": 3,
        private String desc; // "desc": "GELANGGANG SERBAGUNA",
        private int aktif; // "aktif": 1,
        private String created_by; // "created_by": null,
        private String updated_by; // "updated_by": 490,
        private String created; // "created": "2016-03-03 15:52:57",
        private String updated; // "updated": "2019-02-25 15:54:18"
    }
}

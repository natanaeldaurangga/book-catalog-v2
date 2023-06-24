package com.nael.catalog.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// dengan memberikan akses kepada library lombok untuk membuatkan setter dan getter untuk kelas Author
@Data
@NoArgsConstructor // ekan membuat satu constructor dengan parameter kosong
@AllArgsConstructor // dengan anotasi ini lombok akan membuatkan kita semua contructor yang
                    // ibutuhkan
@Entity // supaya kelas author dijadikan model untuk database, jadinya setiap instance
        // dan objek dari entity ini, akan merepresentasikan 1 baris/record dari sebuah
        // table
@Table(name = "author") // memberi nama table yang akan disambungkan ke Author atau nama table yan gakan
// @DynamicUpdate // cocok jika kolom pada tabel yang diupdate ada banyak
// @Where(clause = "deleted=false or deleted is null") // dengan menggunakan
// where clause maka nantinya hibernate akan
// melakukan filter
// terhadap record mana saja yang dapat diquery oleh hibernate
// kita tidak perlu menambahkan clause tambahan lagi setiap kali melakukan query
// terhadap author service impl
// jadi ketika kita melakukan select ototmatis dibelakangnya ditambahan "WHERE
// deleted = FALSE"
// @SQLDelete(sql = "UPDATE author SET deleted = true WHERE id = ?") // anotasi
// ini berguna untuku mendefine skrip delete
// yang nantinya dapat
// digunakan untuk mereplace ketika sebuah method build in delete dipanggil
public class Author extends AbstractBaseEntity {

    @Id // akan menjadi primary key
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_generator")
    @SequenceGenerator(name = "author_generator", sequenceName = "author_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(255)") // memberitahu orm kalo ini adalah kolom
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL) // author di sini adalah nama dari atribut di kelas
                                                               // Address
    private List<Address> addresses;

    // TODO: lanjut ngebuat relation many-to-many: Book-Author

}

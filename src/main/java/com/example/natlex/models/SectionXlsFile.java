package com.example.natlex.models;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class SectionXlsFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long id;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "filename")
    private String filename;

    @Column(name = "result")
    private String result;

    public SectionXlsFile(Long id, String filename, String result, String originalName) {
        this.id = id;
        this.filename = filename;
        this.result = result;
        this.originalName = originalName;
    }

    public SectionXlsFile() {
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

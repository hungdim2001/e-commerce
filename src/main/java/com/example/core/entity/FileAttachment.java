package com.example.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
@SuperBuilder
@Entity
@Data
@Table(name = "file_attachments")
@NoArgsConstructor
@AllArgsConstructor
public class FileAttachment extends AuditTable implements Serializable {

    @Column(name = "LINK")
    private String link;
}

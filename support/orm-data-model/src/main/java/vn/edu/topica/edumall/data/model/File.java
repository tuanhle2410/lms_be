package vn.edu.topica.edumall.data.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import vn.edu.topica.edumall.data.enumtype.FileStatusEnum;
import vn.edu.topica.edumall.data.enumtype.FileTypeEnum;
import vn.edu.topica.edumall.data.enumtype.UploadFileStatusEnum;

@Entity
@Builder(toBuilder = true)
@Table(name = "file")
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Where(clause = "deleted_at is null")
public class File extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "file_type")
    private FileTypeEnum fileType;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "url", length = 255)
    private String url;

    @Column(name = "status")
    private FileStatusEnum status;

    @Column(name = "object_key")
    private String objectKey;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @Column(name = "duration")
    private Long duration;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    @JsonIgnoreProperties(value = {"files", "parentFolder", "childFolders", "course", "warehouse"})
    private Folder folder;

    @OneToMany(mappedBy = "file")
    @JsonIgnore
    private List<Asset> assets;

    @Column(name = "created_by")
    private Long createdBy;

    @OneToOne(mappedBy = "file")
    @JsonIgnoreProperties(value = {"file"})
    private MediaToolResult mediaToolResult;

    @Column(name = "upload_status")
    private UploadFileStatusEnum uploadStatus;
}

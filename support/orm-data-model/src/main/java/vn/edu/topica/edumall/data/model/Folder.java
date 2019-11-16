package vn.edu.topica.edumall.data.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Builder(toBuilder = true)
@Table(name = "folder")
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Where(clause = "deleted_at is null")
public class Folder extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    @NotEmpty(message = "Name can be not null")
    private String name;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @OneToOne(mappedBy = "folder")
    private WareHouse warehouse;

    @OneToMany(mappedBy = "folder")
    @JsonIgnoreProperties("folder")
    @OrderBy("createdAt desc")
    private List<File> files;

    @ManyToOne
    @JoinColumn(name = "parent_folder_id")
//	@JsonManagedReference
    @JsonBackReference
    private Folder parentFolder;

    @OneToMany(mappedBy = "parentFolder")
//	@JsonBackReference
    @JsonManagedReference
    @OrderBy("createdAt desc")
    private List<Folder> childFolders;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "created_by")
    private Long createdBy;

}

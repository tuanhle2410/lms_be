package vn.edu.topica.edumall.data.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.topica.edumall.data.enumtype.CourseRunMarketingEnum;
import vn.edu.topica.edumall.data.enumtype.CourseStatusEnum;

@Entity
@Builder(toBuilder = true)
@Table(name = "course")
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Course extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "alias_name", length = 70)
    private String aliasName;

    @Column(name = "status")
    private CourseStatusEnum status;

    @Column(name = "published_at")
    private Date publishedAt;

    @Column(name = "lastest_version_updated_at")
    private Date lastestVersionUpdatedAt;

    @OneToMany(mappedBy = "course")
    private List<CourseVersion> courseVersions;

    @OneToOne
    @JoinColumn(name = "release_course_version_id")
    private CourseVersion courseVersion;

    @OneToMany(mappedBy = "course")
    private List<Folder> folders;

    @ManyToMany
    @JoinTable(name = "teacher_course", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private List<Teacher> teachers;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "price")
    private Double price;

    @Column(name = "kelly_id")
    private String kelleyId;

    @Column(name = "is_run_marketing")
    private CourseRunMarketingEnum isRunMarketing;
}

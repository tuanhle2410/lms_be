package vn.edu.topica.edumall.data.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import vn.edu.topica.edumall.data.enumtype.ApproveStatusEnum;
import vn.edu.topica.edumall.data.enumtype.CourseVersionStatusEnum;
import vn.edu.topica.edumall.data.enumtype.CourseVersionStatusOfficialEnum;
import vn.edu.topica.edumall.data.enumtype.StatusSendExternalServiceEnum;

@Entity
@Builder(toBuilder = true)
@Table(name = "course_version")
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Where(clause = "status_official = 1")
public class CourseVersion extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "thumbnail_img", length = 255)
    private String thumbnailImg;

    @Column(name = "background_img", length = 255)
    private String backgroundImg;

    @Column(name = "benefit", columnDefinition = "Text")
    private String benefit;

    @Column(name = "target", columnDefinition = "Text")
    private String target;

    @Column(name = "requirement", columnDefinition = "Text")
    private String requirement;

    @Column(name = "short_des", columnDefinition = "Text")
    private String shortDescription;

    @Column(name = "long_des", columnDefinition = "Text")
    private String longDescription;

    @Column(name = "status")
    private CourseVersionStatusEnum status;

    @Column(name = "step")
    private Long step;

    @Column(name = "course_code")
    private String courseCode;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "courseVersion")
    private List<Chapter> chapters;

    @ManyToMany
    @JoinTable(name = "sub_category_course_version", joinColumns = @JoinColumn(name = "course_version_id"), inverseJoinColumns = @JoinColumn(name = "subcategory_id"))
    private List<SubCategory> subCategories;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "status_approve")
    private ApproveStatusEnum statusApprove;

    @Column(name = "link_approve_result")
    private String linkApproveResult;

    @Column(name = "status_send_external_service")
    private StatusSendExternalServiceEnum statusSendExternalService;

    @Column(name = "status_official")
    private CourseVersionStatusOfficialEnum statusOfficial;
}

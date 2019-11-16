package vn.edu.topica.edumall.data.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder(toBuilder = true)
@Table(name = "sub_category")
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class SubCategory extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "alias_name", columnDefinition = "Text")
    private String aliasName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_category_id")
    @JsonIgnoreProperties("subCategories")
    private Category parentCategory;

    @ManyToMany
    @JoinTable(name = "sub_category_course_version", joinColumns = @JoinColumn(name = "subcategory_id"), inverseJoinColumns = @JoinColumn(name = "course_version_id"))
    private List<CourseVersion> courseVersions;

    public void addCourseVersionToSubCategory(CourseVersion courseVersion) {
        if (this.courseVersions == null) {
            this.courseVersions = new ArrayList<>();
        }

        List<SubCategory> subCategoryList = courseVersion.getSubCategories();
        if (subCategoryList != null) {
            subCategoryList.removeAll(subCategoryList);
        }
        this.courseVersions.add(courseVersion);
    }

    @Column(name = "created_by")
    private Long created_by;

}

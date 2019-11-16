package vn.edu.topica.edumall.data.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder(toBuilder = true)
@Table(name = "teacher")
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Teacher extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "academic_rank", length = 100)
    private String academicRank;

    @Column(name = "teacher_code", length = 10)
    private String teacherCode;

    @Column(name = "major", length = 100)
    private String major;

    @Column(name = "function", length = 100)
    private String function;

    @Column(name = "work_unit", length = 100)
    private String workUnit;

    @Column(name = "short_des", length = 100)
    private String shortDescription;

    @Column(name = "long_des", columnDefinition = "Text")
    private String longDescription;

    @OneToOne(mappedBy = "teacher")
    private WareHouse warehouse;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "teacher_course", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;

    public void addCourseToTeacher(Course course) {
        if (this.courses == null) {
            this.courses = new ArrayList<>();
        }
        this.courses.add(course);
    }

}

package vn.edu.topica.edumall.api.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.topica.edumall.data.model.CourseVersion;

import java.util.List;

public interface CourseVersionRepository extends JpaRepository<CourseVersion, Long> {
    List<CourseVersion> findAllByStatus(long status);

    @Query(value = "select * from course_version where id = :id and created_by = :userId", nativeQuery = true)
    CourseVersion findByIdAndCreatedBy(@Param("id") Long id, @Param("userId") Long userId);

    @Query(value = "select cv.* from course_version cv \n" +
            "join (\n" +
            "select cv.course_id, max(cv.created_at) created_at from course_version cv\n" +
            "left join teacher_course tc on tc.course_id = cv.course_id\n" +
            "left join teacher t on t.id = tc.teacher_id\n" +
            "left join user u on t.user_id = u.id\n" +
            "where u.id = :userId and cv.status = :status and cv.status_official = 1\n" +
            "group by cv.course_id) a \n" +
            "on a.course_id = cv.course_id and a.created_at = cv.created_at\n" +
            "order by cv.created_at desc\n" +
            "limit :start, :perPage", nativeQuery = true)
    List<CourseVersion> findAllByStatusAndUserIdGroupByCodeOrderByCreatedAtDesc(@Param("userId") Long userId, @Param("status") Integer status, @Param("start") int start, @Param("perPage") int perPage);

    @Query(value = "select cv.* from course_version cv \n" +
            "join (\n" +
            "select cv.course_id, max(cv.created_at) created_at from course_version cv\n" +
            "left join teacher_course tc on tc.course_id = cv.course_id\n" +
            "left join teacher t on t.id = tc.teacher_id\n" +
            "left join user u on t.user_id = u.id\n" +
            "where u.id = :userId and cv.status_official = 1\n" +
            "group by cv.course_id) a \n" +
            "on a.course_id = cv.course_id and a.created_at = cv.created_at\n" +
            "order by cv.created_at desc\n" +
            "limit :start, :perPage", nativeQuery = true)
    List<CourseVersion> findAllByUserIdGroupByCodeOrderByCreatedAtDesc(@Param("userId") Long userId, @Param("start") int start, @Param("perPage") int perPage);

    @Query(value = "select count(1) from \n" +
            "(\n" +
            "select cv.course_id from course_version cv\n" +
            "left join teacher_course tc on tc.course_id = cv.course_id\n" +
            "left join teacher t on t.id = tc.teacher_id\n" +
            "left join user u on t.user_id = u.id\n" +
            "where u.id = :userId and cv.status_official = 1\n" +
            "group by cv.course_id) c", nativeQuery = true)
    Integer countByCourseCodeAndUserId(@Param("userId") Long userId);

    @Query(value = "select count(1) from \n" +
            "(\n" +
            "select cv.course_id from course_version cv\n" +
            "left join teacher_course tc on tc.course_id = cv.course_id\n" +
            "left join teacher t on t.id = tc.teacher_id\n" +
            "left join user u on t.user_id = u.id\n" +
            "where u.id = :userId and status = :status and cv.status_official = 1\n" +
            "group by cv.course_id) c", nativeQuery = true)
    Integer countByCourseCodeAndUserIdAndStatus(@Param("userId") Long userId, @Param("status") int status);

    @Query(value = "select ab.status, sum(ab.count) as count from (select status, count(*) as count from course_version cv \n" +
            "join (\n" +
            "select cv.course_id, max(cv.created_at) created_at from course_version cv\n" +
            "left join teacher_course tc on tc.course_id = cv.course_id\n" +
            "left join teacher t on t.id = tc.teacher_id\n" +
            "left join user u on t.user_id = u.id\n" +
            "where u.id = :userId and cv.status_official = 1\n" +
            "group by cv.course_id) a \n" +
            "on a.course_id = cv.course_id and a.created_at = cv.created_at\n" +
            "group by status union all " +
            "select 0 as status, 0 as count union all\n" +
            "select 1 as status, 0 as count union all\n" +
            "select 2 as status, 0 as count union all\n" +
            "select 3 as status, 0 as count union all\n" +
            "select 4 as status, 0 as count union all\n" +
            "select \"null\" as status, count(*) as count from course_version cv \n" +
            "join (select cv.course_id, max(cv.created_at) created_at from course_version cv \n" +
            "left join teacher_course tc on tc.course_id = cv.course_id \n" +
            "left join teacher t on t.id = tc.teacher_id \n" +
            "left join user u on t.user_id = u.id \n" +
            "where u.id = :userId and cv.status_official = 1\n" +
            "group by cv.course_id) a\n" +
            "on a.course_id = cv.course_id and a.created_at = cv.created_at) ab\n" +
            "group by status;", nativeQuery = true)
    List<CountByStatusAndUserIdRepository> countByStatusAndUserId(@Param("userId") Long userId);

    @Query("select courseVersion.id,courseVersion.name, sum(file.duration) as duration, " +
            "sum(case when file.duration is null then 0 else 1 end) as fileNumber " +
            "from CourseVersion courseVersion " +
            "left join courseVersion.chapters chapter " +
            "left join chapter.lectures lecture " +
            "left join lecture.assets asset " +
            "left join asset.file file " +
            "where courseVersion.id = :id and file.id is not null and courseVersion.statusOfficial = 1 " +
            "group by courseVersion.id,courseVersion.name")
    DurationAndNumberVideoRepository getDurationAndNumberVideo(@Param("id") Long id);

    List<CourseVersion> findByCourseCodeOrderByCreatedAtAsc(String courseCode);

    @Query(value = "select * from course_version where id = :courseVersionId", nativeQuery = true)
    CourseVersion getCourseVersionById(@Param("courseVersionId") Long courseVersionId);
}


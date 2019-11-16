package vn.edu.topica.edumall.data.model;

import lombok.*;
import vn.edu.topica.edumall.data.enumtype.RevenueApprovalStatusEnum;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRevenue extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    private int month;

    @NotNull
    private int year;

    @NotBlank
    private String email;

    @Column(name="is_valid")
    private int isValid = 1;

    @NotBlank
    private String url;

    @NotNull
    @Column(name="approval_status")
    private RevenueApprovalStatusEnum approvalStatus = RevenueApprovalStatusEnum.PENDING;
}

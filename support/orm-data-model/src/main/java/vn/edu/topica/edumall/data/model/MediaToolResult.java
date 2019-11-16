package vn.edu.topica.edumall.data.model;

import lombok.*;
import vn.edu.topica.edumall.data.enumtype.ApproveStatusEnum;

import javax.persistence.*;

@Entity
@Builder(toBuilder = true)
@Table(name = "media_tool_result")
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class MediaToolResult extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "frame_width")
    private ApproveStatusEnum videoFrameWidth;

    @Column(name = "frame_height")
    private ApproveStatusEnum videoFrameHeight;

    @Column(name = "bit_rate")
    private ApproveStatusEnum videoBitRate;

    @Column(name = "frame_rate")
    private ApproveStatusEnum videoFrameRate;

    @Column(name = "code_name")
    private ApproveStatusEnum videoCodeName;

    @Column(name = "status")
    private ApproveStatusEnum status;

    @Column(name = "format")
    private ApproveStatusEnum videoFormat;

    @Column(name = "audio_bit_rate")
    private ApproveStatusEnum audioBitRate;

    @Column(name = "audio_channel")
    private ApproveStatusEnum audioChannel;

    @Column(name = "detail_result", columnDefinition = "LONGTEXT")
    private String detailResult;

    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;
}

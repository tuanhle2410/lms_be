package vn.edu.topica.edumall.api.lms.utility;

import vn.edu.topica.edumall.data.enumtype.ApproveStatusEnum;

public class AnalyticStandard {
    private static final Long videoFrameWidthStandard = 1920L;
    private static final Long videoFrameHeightStandard = 1080L;
    private static final Long videoBitRateStandard = 3000000L;//bit per second
    private static final Long videoFrameRateStandard = 24L; //frame per second
    private static final String videoCodeNameStandard = "H264";
    private static final String videoFormatStandard = "mp4";
    private static final Long audioBitRateStandard = 128000L; //bit per second
    private static final Long audioChannelStandard = 2L;

    public static ApproveStatusEnum checkVideoFrameWidthStandard(Long videoFrameWidth) {
        if (videoFrameWidth >= videoFrameWidthStandard) {
            return ApproveStatusEnum.PASS;
        }
        return ApproveStatusEnum.FAILED;
    }

    public static ApproveStatusEnum checkVideoFrameHeightStandard(Long videoFrameHeight) {
        if (videoFrameHeight >= videoFrameHeightStandard) {
            return ApproveStatusEnum.PASS;
        }
        return ApproveStatusEnum.FAILED;
    }

    public static ApproveStatusEnum checkVideoBitRateStandard(Long videoBitRate) {
        if (videoBitRate >= videoBitRateStandard) {
            return ApproveStatusEnum.PASS;
        }
        return ApproveStatusEnum.FAILED;
    }

    public static ApproveStatusEnum checkVideoFrameRateStandard(Long videoFrameRate) {
        if (videoFrameRate >= videoFrameRateStandard) {
            return ApproveStatusEnum.PASS;
        }
        return ApproveStatusEnum.FAILED;
    }

    public static ApproveStatusEnum checkVideoCodeNameStandard(String videoCodecName) {
        if (videoCodecName.equalsIgnoreCase(videoCodeNameStandard)) {
            return ApproveStatusEnum.PASS;
        }
        return ApproveStatusEnum.FAILED;
    }

    public static ApproveStatusEnum checkVideoFormatStandard(String videoFormat) {
        if (videoFormat.equalsIgnoreCase(videoFormatStandard)) {
            return ApproveStatusEnum.PASS;
        }
        return ApproveStatusEnum.FAILED;
    }

    public static ApproveStatusEnum checkAudioBitRateStandard(Long audioBitRate) {
        if (audioBitRate >= audioBitRateStandard) {
            return ApproveStatusEnum.PASS;
        }
        return ApproveStatusEnum.FAILED;
    }

    public static ApproveStatusEnum checkAudioChannelStandard(Long audioChannel) {
        if (audioChannel >= audioChannelStandard) {
            return ApproveStatusEnum.PASS;
        }
        return ApproveStatusEnum.FAILED;
    }
}
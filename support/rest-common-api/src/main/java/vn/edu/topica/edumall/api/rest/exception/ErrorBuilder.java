package vn.edu.topica.edumall.api.rest.exception;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@Component
public class ErrorBuilder {

	public Map<String, Object> putErrorTrace(HttpStatus status, Throwable throwable) {
		Map<String, Object> result = Maps.newHashMap();
		Object code = null;
		if (throwable instanceof CodeException) {
			code = ((ApiCodeException) throwable).getUserFriendlyExceptionCode();
		}
		if (code == null) {
			code = status.value();
		}
		String traceID = MDC.get("X-B3-TraceId");
		result.put("trace_id", traceID);
		result.put("code", code);
		return result;
	}

	public void putErrorMessage(Map<String, Object> errors, Throwable throwable) {
		String msg = null;
		if (throwable instanceof CodeException) {
			msg = ((CodeException) throwable).getUserFriendlyMessage();
		}
		if (msg == null) {
			msg = "Có lỗi xảy ra!!!Nếu bạn cần hỗ trợ, vui lòng gọi điện vào hotline: 1800.6816 (7h00 - 22h00 các ngày trong tuần).";
		}
		errors.put("message", msg);
	}

	public void putDevMsg(Map<String, Object> errors, WebRequest request, HttpStatus status, Throwable throwable) {
		String devMsgKey = "developer_message";
		if (throwable != null) {
			errors.put(devMsgKey, ExceptionUtils.getRootCauseMessage(throwable));
			log.error("Error: ", throwable);
		} else {
			Object msg = request.getAttribute("javax.servlet.error.message", 0);
			if (msg instanceof String && Strings.isNullOrEmpty((String) msg)) {
				Object path = request.getAttribute("javax.servlet.error.request_uri", 0);
				errors.put(devMsgKey, status.getReasonPhrase() + " " + path);
			} else {
				errors.put(devMsgKey, status.getReasonPhrase() + " " + msg);
			}
			log.error("Error: ", errors.get(devMsgKey));
		}
	}
}

package vn.edu.topica.edumall.api.lms.utility;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class NormalizeStringUtility {
    private Map<String, String> regexMap = Stream.of(new String[][]{
            {"[\"äåāąàáảãạăắằẳẵặâầấẩẫậÀÁẢÃẠĂẮẰẲẴẶÂẦẤẨẪẬ\"]", "a"},
            {"[\"çćĉċč\"]", "c"},
            {"[\"ðďđĐ\"]", "d"},
            {"[\"ëēĕėęěèéẻẽẹêềếểễệÈÉẺẼẸÊỀẾỂỄỆ\"]", "e"},
            {"[\"ĝğġģ\"]", "g"},
            {"[\"ĥħ\"]", "h"},
            {"[\"îïīĭįıìíỉĩịÌÍỈĨỊ\"]", "i"},
            {"[\"ĵ\"]", "j"},
            {"[\"ñńņňŉŊŋ\"]", "n"},
            {"[\"öøōŏőòóỏõọôồốổỗộơờớởỡợÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢ\"]", "o"},
            {"[\"ŕŗř\"]", "r"},
            {"[\"śŝşšſ\"]", "s"},
            {"[\"ţťŧ\"]", "t"},
            {"[\"ûüūŭůűųùúủũụưừứủữựÙÚỦŨỤƯỪỨỬỮỰ\"]", "u"},
            {"[\"ỳýỷỹỵỲÝỶỸỴ\"]", "y"},
            {"[\" \"]", "-"}
    }).collect(Collectors.toMap(p -> p[0], p -> p[1]));

    public String normalize(String str) {
        if (str == null) {
            return null;
        }
        for (String key : regexMap.keySet()) {
            str = str.replaceAll(key, regexMap.get(key));
        }
        str = str.replaceAll("[^A-Za-z0-9 -]", "");
        return str.replaceAll("[-]+", "-");
    }

    public static boolean strIsNull(String str) {
        if (str == null || str.isEmpty() || str.equalsIgnoreCase("null")) {
            return true;
        }
        return false;
    }

}

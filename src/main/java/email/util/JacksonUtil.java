package email.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Handles serializing data using Jackson's object mapper
 * @author jjwyse
 */
public class JacksonUtil {
    /**
     * Serializes the given object into its JSON string representation
     * @param o The object to serialize
     * @return The object specified serialized as JSON
     */
    public static String toString(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

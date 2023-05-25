package gb.library.admin.utils;

import gb.library.common.entities.IdBasedEntity;

import java.util.Objects;

public class CheckUniqueResponseStatusHelper {
    public static final String OK = "OK";
    public static final String DUPLICATED = "Duplicated";
    public static final String ERROR = "Checking Error";


    public static String getCheckStatus(IdBasedEntity obj, Integer id){
        if (obj != null) {
            if (!Objects.equals(obj.getId(), id)) {
                return DUPLICATED;
            }
        }
        return OK;
    }
}

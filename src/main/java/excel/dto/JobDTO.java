package excel.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author 陆昆
 **/
@Data
public class JobDTO implements java.io.Serializable{

    public JobDTO() {
    }

    public JobDTO(String jobId, String groupId) {
        this.jobId = jobId;
        this.groupId = groupId;
    }

    @Excel(name = "id")
    private String jobId;

    @Excel(name = "client_group_id")
    private String groupId;
}

package excel;

import java.util.ArrayList;
import java.util.List;

import excel.dto.JobDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 任务相关
 *
 * @author 陆昆
 **/
public class JobUtils {
    public static String geneDbQuerString(String path) {
        List<JobDTO> jobDTOS = ExcelUtil.parseExcel(path, JobDTO.class);
        String result = StringUtils.EMPTY;
        List<String> temps = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(jobDTOS)) {
            for (JobDTO jobDTO : jobDTOS) {
                StringBuilder sb = new StringBuilder();
                sb.append("'");
                sb.append(jobDTO.getJobId());
                sb.append("|");
                sb.append(jobDTO.getGroupId());
                sb.append("'");
                temps.add(sb.toString());
            }
            result = StringUtils.join(",", temps);
        }
        return result;
    }

    public static void main(String[] args) {
        String path = "/Users/lukun/Downloads/hz.xlsx";
        System.out.println(geneDbQuerString(path));
    }
}

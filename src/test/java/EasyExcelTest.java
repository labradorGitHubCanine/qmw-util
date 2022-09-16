import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.qmw.util.EasyExcelUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EasyExcelTest {

    public static void main(String[] args) {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            DemoData demoData = new DemoData()
                    .setName("奥术大师多撒多")
                    .setSum(new BigDecimal("222.1356"));
            list.add(demoData);
        }

        EasyExcelUtil.save(list, DemoData.class, "C:\\Users\\12334\\Desktop\\aa.xlsx");
    }

    @Data
    @Accessors(chain = true)
//    @HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 27)
    public static class DemoData {

        @ExcelProperty(value = {"名称", "acx"}, index = 0)
        private String name;

        @NumberFormat("0.00")
        @ExcelProperty(value = {"金额"}, index = 1)
        private BigDecimal sum;

    }

}

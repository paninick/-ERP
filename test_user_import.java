import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.domain.entity.SysUser;

public class test_user_import {
    public static void main(String[] args) {
        try {
            // 测试读取第一个用户管理文件
            System.out.println("=== 测试读取 user_1775309546654.xlsx ===");
            InputStream is1 = new FileInputStream("C:\\Users\\91306\\Downloads\\user_1775309546654.xlsx");
            ExcelUtil<SysUser> util1 = new ExcelUtil<SysUser>(SysUser.class);
            List<SysUser> userList1 = util1.importExcel(is1);
            System.out.println("成功读取 " + userList1.size() + " 条用户数据");
            
            // 打印前5条数据的关键信息
            System.out.println("\n前5条用户数据预览：");
            for (int i = 0; i < Math.min(5, userList1.size()); i++) {
                SysUser user = userList1.get(i);
                System.out.println("用户序号: " + user.getUserId() + 
                                 ", 登录名称: " + user.getUserName() + 
                                 ", 用户名称: " + user.getNickName() + 
                                 ", 部门名称: " + (user.getDept() != null ? user.getDept().getDeptName() : "无部门"));
            }
            is1.close();
            
            System.out.println("\n" + "=".repeat(50) + "\n");
            
            // 测试读取第二个用户管理文件
            System.out.println("=== 测试读取 user_1775309613502.xlsx ===");
            InputStream is2 = new FileInputStream("C:\\Users\\91306\\Downloads\\user_1775309613502.xlsx");
            ExcelUtil<SysUser> util2 = new ExcelUtil<SysUser>(SysUser.class);
            List<SysUser> userList2 = util2.importExcel(is2);
            System.out.println("成功读取 " + userList2.size() + " 条用户数据");
            
            // 打印前5条数据的关键信息
            System.out.println("\n前5条用户数据预览：");
            for (int i = 0; i < Math.min(5, userList2.size()); i++) {
                SysUser user = userList2.get(i);
                System.out.println("用户序号: " + user.getUserId() + 
                                 ", 登录名称: " + user.getUserName() + 
                                 ", 用户名称: " + user.getNickName() + 
                                 ", 部门名称: " + (user.getDept() != null ? user.getDept().getDeptName() : "无部门"));
            }
            is2.close();
            
            System.out.println("\n" + "=".repeat(50) + "\n");
            
            // 检查两个文件的数据是否相同
            System.out.println("=== 检查两个文件的数据是否相同 ===");
            if (userList1.size() == userList2.size()) {
                boolean isSame = true;
                for (int i = 0; i < userList1.size(); i++) {
                    SysUser user1 = userList1.get(i);
                    SysUser user2 = userList2.get(i);
                    if (!user1.getUserName().equals(user2.getUserName()) || 
                        !user1.getNickName().equals(user2.getNickName())) {
                        isSame = false;
                        break;
                    }
                }
                if (isSame) {
                    System.out.println("✅ 两个文件的数据完全相同");
                } else {
                    System.out.println("❌ 两个文件的数据不完全相同");
                }
            } else {
                System.out.println("❌ 两个文件的数据行数不同");
            }
            
        } catch (Exception e) {
            System.out.println("❌ 测试过程中出现错误：" + e.getMessage());
            e.printStackTrace();
        }
    }
}

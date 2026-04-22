package com.ruoyi.common.utils.ip;

import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.config.RuoYiConfig;

/**
 * 获取地址类（离线 ip2region，PIPL 合规，不调公网）
 *
 * 依赖：org.lionsoul:ip2region 2.7.0
 * 数据库：部署时需将 ip2region.xdb 放到 ${ruoyi.profile}/ip2region.xdb
 *         或 classpath 根目录（ruoyi-common/src/main/resources/ip2region.xdb）
 * 下载地址：https://github.com/lionsoul2014/ip2region/tree/master/data
 *
 * @author ruoyi (改写：离线版)
 */
public class AddressUtils
{
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static final String UNKNOWN = "XX XX";

    private static volatile Searcher searcher;

    private static Searcher getSearcher() {
        if (searcher == null) {
            synchronized (AddressUtils.class) {
                if (searcher == null) {
                    try {
                        // 优先从 uploadPath 同级目录找 xdb，没有则尝试 classpath
                        String xdbPath = RuoYiConfig.getProfile() + "/ip2region.xdb";
                        searcher = Searcher.newWithFileOnly(xdbPath);
                    } catch (Exception e1) {
                        try {
                            java.io.InputStream is = AddressUtils.class.getResourceAsStream("/ip2region.xdb");
                            if (is == null) {
                                log.warn("ip2region.xdb 未找到，地理定位不可用。请下载后放到 {}",
                                        RuoYiConfig.getProfile() + "/ip2region.xdb");
                                return null;
                            }
                            byte[] data = is.readAllBytes();
                            searcher = Searcher.newWithBuffer(data);
                        } catch (Exception e2) {
                            log.warn("ip2region Searcher 初始化失败：{}", e2.getMessage());
                            return null;
                        }
                    }
                }
            }
        }
        return searcher;
    }

    public static String getRealAddressByIP(String ip)
    {
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        if (!RuoYiConfig.isAddressEnabled()) {
            return UNKNOWN;
        }
        try {
            Searcher s = getSearcher();
            if (s == null) {
                return UNKNOWN;
            }
            // 格式："国家|区域|省份|城市|ISP"，例如 "中国|0|浙江省|杭州市|阿里云"
            String region = s.search(ip);
            if (region == null || region.isEmpty()) {
                return UNKNOWN;
            }
            String[] parts = region.split("\\|");
            String province = parts.length > 2 ? parts[2].replace("省", "").replace("市", "") : "";
            String city    = parts.length > 3 ? parts[3].replace("市", "") : "";
            if ("0".equals(province) || province.isEmpty()) return parts[0];
            if ("0".equals(city)    || city.isEmpty())    return province;
            return province + " " + city;
        } catch (Exception e) {
            log.error("ip2region 查询失败 ip={}: {}", ip, e.getMessage());
            return UNKNOWN;
        }
    }
}
